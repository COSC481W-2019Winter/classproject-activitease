package com.example.activiteaseroomdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface MyDao {
    @Insert
    public void addInterest(Interest i);

    @Query("select * from interests")
    public Interest getInterests();
}
