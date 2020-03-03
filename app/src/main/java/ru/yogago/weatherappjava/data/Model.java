package ru.yogago.weatherappjava.data;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.room.Room;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yogago.weatherappjava.data.cityconditions.CityCondition;
import ru.yogago.weatherappjava.data.citymodel.CityModel;
import ru.yogago.weatherappjava.ui.main.MainViewModel;

public class Model {

    private final String LOG_TAG = "myLog";
    private final String apiKey = ApiKey.apiKey;
    private final String language = "ru-ru";
    private String city;
    private MainViewModel mainViewModel;
    private AppDatabase db;
    private DeveloperAccuweatherCom developerAccuweatherCom;

    public Model() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dataservice.accuweather.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.developerAccuweatherCom = retrofit.create(DeveloperAccuweatherCom.class);
    }
    public void setMainViewModel(MainViewModel m){
        this.mainViewModel = m;
    }

    public void loadContent(String city) {
        loadCities(city);
    }

    public void loadCities(final String city){
        this.city = city;
        mainViewModel.setVisibilityloadProgress(true);
        developerAccuweatherCom.getCity(city, apiKey, language).enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                assert response.body() != null;
                saveContentToDB(db, convertCityForSqlObject(response.body()));
                for (CityModel cityModel: response.body()) {
                    loadConditions(cityModel.getKey());
                }
                mainViewModel.setToast("Загружено: ");
                mainViewModel.setVisibilityloadProgress(false);
            }
            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
                Log.d(LOG_TAG, "Ошибка загрузки: " + t);
                loadContentFromDB(db, city);
                mainViewModel.setToast("Ошибка загрузки или отключен интеренет!");
                mainViewModel.setVisibilityloadProgress(false);
            }
        });
    }

    public void loadConditions(final String cityCode){
        mainViewModel.setVisibilityloadProgress(true);
        this.developerAccuweatherCom.getCurrentConditions(cityCode, apiKey, language).enqueue(new Callback<List<CityCondition>>() {
            @Override
            public void onResponse(Call<List<CityCondition>> call, Response<List<CityCondition>> response) {
                assert response.body() != null;
                for (CityCondition cityCondition: response.body()) {
                    updateContentToDB(db, cityCondition.getTemperature().getMetric().getValue().toString(), cityCondition.getWeatherText(), cityCode);
                }
                mainViewModel.setVisibilityloadProgress(false);
            }
            @Override
            public void onFailure(Call<List<CityCondition>> call, Throwable t) {
                Log.d(LOG_TAG, "Error - onFailure: " + t);
                mainViewModel.setVisibilityloadProgress(false);
            }
        });
    }

    public void createDataBase(Context context){
       this.db = Room.databaseBuilder(context,
                AppDatabase.class, "mi-database")
               .build();
    }

    void loadContentFromDB(final AppDatabase db, final String city){
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public List<CityModelSql> call() throws Exception {
                return db.getCityDao().getAllCityWithName(city);
//                return db.getCityDao().getAllCity();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    new Consumer<Object>() {
                        @Override
                        public void accept(Object result) throws Exception{
                            ArrayList<String> cityInfo = new ArrayList<>();
                            for (CityModelSql city : (List<CityModelSql>) result) {
                                cityInfo.add("город: " + city.getLocalizedName()
                                        + "\nстрана: "
                                        + city.getCountryName()
                                        + "\nобласть: "
                                        + city.getAdministrativeArea()
                                        + "\nключ: " + city.getCityKey()
                                        + "\nтемпература: " + city.getTemperature()
                                        + "\nтекст: " + city.getWeatherText()
                                );
                            }
                            mainViewModel.setContentToVM(cityInfo);
                        }
                    },
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(final Throwable throwable) throws Exception {
                            Log.d(LOG_TAG, "onError loadContentFromDB: " + throwable);
                        }
                    });
    }
    void saveContentToDB(final AppDatabase db, final List<CityModelSql> cities){
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() {
                db.getCityDao().insertAll(cities);
                return true;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    new Consumer<Object>() {
                        @Override
                        public void accept(Object result) throws Exception {
                            loadContentFromDB(db, city);
                        }
                    },
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(final Throwable throwable) throws Exception {
                            Log.d(LOG_TAG, "onError saveContentToDB: " + throwable);
                        }
                    });
    }

    void updateContentToDB(final AppDatabase db, final String temperature, final String weatherText, final String cityCode){
        Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() {
                db.getCityDao().insert(temperature, weatherText, cityCode);
                return true;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    new Consumer<Object>() {
                        @Override
                        public void accept(Object result) throws Exception {
                            loadContentFromDB(db, city);
                        }
                    },
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(final Throwable throwable) throws Exception {
                            Log.d(LOG_TAG, "onError updateContentToDB:" + throwable);
                        }
                    });
    }

    private List<CityModelSql> convertCityForSqlObject(List<CityModel> cityModels){
        List<CityModelSql> cities = new ArrayList<>();
        for (CityModel cityModel : cityModels) {
            CityModelSql city = new CityModelSql();
            city.setCityKey(cityModel.getKey());
            city.setLocalizedName(cityModel.getLocalizedName());
            city.setCountryName(cityModel.getCountry().getLocalizedName());
            city.setAdministrativeArea(cityModel.getAdministrativeArea().getLocalizedName());
            cities.add(city);
        }
        return cities;
    }
    private List<CityModelSql> convertCityForSqlObjectCondition(List<CityCondition> cityConditions, String cityCode){
        List<CityModelSql> cities = new ArrayList<>();
        for (CityCondition cityCondition : cityConditions) {
            CityModelSql city = new CityModelSql();
            city.setCityKey(cityCode);
            city.setTemperature(cityCondition.getTemperature().getMetric().getValue().toString());
            city.setWeatherText(cityCondition.getWeatherText());
            cities.add(city);
        }
        return cities;
    }

}
