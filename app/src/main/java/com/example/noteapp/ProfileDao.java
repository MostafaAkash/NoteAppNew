package com.example.noteapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProfileDao {

    @Insert
    void insertOnePerson(Profile person);

    @Update
    void updatePerson(Profile person);

    @Delete
    void deleteOnePerson(Profile person);

    @Query("DELETE FROM profile_table")
    void deleteAllPersons();

    @Query("select * from profile_table order by priority_number desc")
    LiveData<List<Profile>> getAllPersons();
}
