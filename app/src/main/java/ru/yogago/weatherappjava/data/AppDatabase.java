package ru.yogago.weatherappjava.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CityModelSql.class /*, AnotherEntityType.class, AThirdEntityType.class */}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}