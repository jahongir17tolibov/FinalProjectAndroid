package com.jt17.finalprojectandroid.domain.model;

import androidx.annotation.NonNull;

public class ItemsModel {
    private int id;
    private String name;
    private String sum;

    public ItemsModel(int id, String name, String sum) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return "{name: " + name + " id " + id + " sum " + sum + "}";
    }

}
