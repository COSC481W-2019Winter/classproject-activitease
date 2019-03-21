package com.example.activitease;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyDao {
    @Insert
    void addInterest(Interest i);

    @Query("select * from interests")
    List<Interest> getInterests();
}
