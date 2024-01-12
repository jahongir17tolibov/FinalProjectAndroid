package com.jt17.finalprojectandroid.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_task_entity")
public class MyEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String sum;

    public MyEntity(String name, String sum) {
        this.id = 0;
        this.name = name;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
