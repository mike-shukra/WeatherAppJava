package ru.yogago.weatherappjava.data;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.yogago.weatherappjava.data.cityconditions.CityCondition;
import ru.yogago.weatherappjava.data.citymodel.CityModel;

public interface DeveloperAccuweatherCom {

    // https://dataservice.accuweather.com/locations/v1/search?q=Санкт-Петербург&apikey=h5N75PJIBYi2hexVDwWOrytBZDii3mDc&language=ru-ru
    // http://dataservice.accuweather.com/currentconditions/v1/295212?apikey=h5N75PJIBYi2hexVDwWOrytBZDii3mDc&language=ru-ru

    @GET("/locations/v1/search")
    Call<List<CityModel>> getCity(@Query("q") String city, @Query("apikey") String apiKey, @Query("language") String language);

    @GET("/currentconditions/v1/{cityCode}")
    Call<List<CityCondition>> getCurrentConditions(@Path("cityCode") String cityCode, @Query("apikey") String apiKey, @Query("language") String language);

}
