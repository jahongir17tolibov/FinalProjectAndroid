package com.jt17.finalprojectandroid.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jt17.finalprojectandroid.data.entity.MyEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MainDao {

    @Query("SELECT * FROM my_task_entity ORDER BY id ASC")
    Flowable<List<MyEntity>> getAllData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(MyEntity data);

    @Query("DELETE FROM my_task_entity WHERE id = :entityId")
    void deleteData(int entityId);

    @Query("DELETE FROM my_task_entity")
    void clearAllData();

}
