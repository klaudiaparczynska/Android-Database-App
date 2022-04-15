package com.example.app2_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.app2_database.Element;

import java.util.List;

@Dao
public interface ElementDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Element element);

    @Query("DELETE FROM phones_table")
    void deleteAll();

    @Query("SELECT * FROM phones_table ORDER BY producent ASC")
    LiveData<List<Element>> getAlphabetizedElements();

    @Delete
    void deleteElement(Element element);

    @Update
    void update(Element element);

}
