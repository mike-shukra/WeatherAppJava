package ru.yogago.weatherappjava.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(CityModelSql city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CityModelSql> cities);

    @Query("UPDATE cityModelSql SET temperature = :temperature, weatherText = :weatherText WHERE cityKey = :cityKey")
    void insert(String temperature, String weatherText, String cityKey);

    @Query("SELECT * FROM cityModelSql")
    List<CityModelSql> getAllCity();

//    @Query("INSERT OR REPLACE INTO cityModelSql ")
//    void insertOrReplaceOne(CityModelSql city);

    @Delete
    void delete(CityModelSql city);

    @Query("SELECT * FROM cityModelSql WHERE localizedName LIKE :localizedName")
    List<CityModelSql> getAllCityWithName(String localizedName);
}
