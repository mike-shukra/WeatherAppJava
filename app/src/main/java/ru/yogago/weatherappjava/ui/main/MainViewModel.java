package ru.yogago.weatherappjava.ui.main;

import android.content.Context;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.yogago.weatherappjava.data.Model;

public class MainViewModel extends ViewModel {

    private final String LOG_TAG = "myLog";
    private Model model;
    private MutableLiveData<String> mText;
    private MutableLiveData<String> mToast;
    private MutableLiveData<String> mCityName;
    private MutableLiveData<Boolean> loadProgress;
    private MutableLiveData<ArrayList<String>> cities;

    public MainViewModel() {
        this.model = new Model();
        this.model.setMainViewModel(this);
        this.mText = new MutableLiveData<>();
        this.mToast = new MutableLiveData<>();
        this.mCityName = new MutableLiveData<>();
//        this.mText.setValue("Введите город:");
        this.loadProgress = new MutableLiveData<>();
        this.cities = new MutableLiveData<>();
    }

    void setContent(final String city){
        this.model.loadContent(city);
    }

    public MutableLiveData<String> getCityName() {
        return mCityName;
    }

    public MutableLiveData<Boolean> getLoadProgress() {
        return this.loadProgress;
    }

    public void setVisibilityloadProgress(boolean b) {
        this.loadProgress.setValue(b);
    }

    public void createDataBase(Context context) {
        model.createDataBase(context);
    }

    public void setContentToVM(ArrayList<String> arr) {
        this.cities.setValue(arr);
    }

    public MutableLiveData<ArrayList<String>> getCities() {
        return cities;
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getToast() {
        return mToast;
    }

    public void setToast(String s) {
        this.mToast.setValue(s);
    }
}
