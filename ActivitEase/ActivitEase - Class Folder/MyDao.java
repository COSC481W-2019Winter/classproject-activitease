package com.example.activiteaseroomdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface MyDao {
    @Insert
    public void addInterest(Interest i);

    
}
