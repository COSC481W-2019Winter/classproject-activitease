package com.example.activiteaseroomdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Interest.class}, version = 1)
public abstract class MyDB extends RoomDatabase {
    public abstract MyDao myDao();
}
