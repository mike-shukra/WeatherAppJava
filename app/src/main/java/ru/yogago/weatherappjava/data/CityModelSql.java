package ru.yogago.weatherappjava.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CityModelSql {

    @PrimaryKey
    @NonNull
    private String cityKey;
    private String localizedName;
    private String countryName;
    private String AdministrativeArea;
    private String temperature;
    private String weatherText;

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getCityKey() {
        return cityKey;
    }

    public void setCityKey(String cityKey) {
        this.cityKey = cityKey;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public String getAdministrativeArea() {
        return AdministrativeArea;
    }

    public void setAdministrativeArea(String administrativeArea) {
        AdministrativeArea = administrativeArea;
    }
}
