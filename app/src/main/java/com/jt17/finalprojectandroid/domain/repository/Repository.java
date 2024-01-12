package com.jt17.finalprojectandroid.domain.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.jt17.finalprojectandroid.data.dao.MainDao;
import com.jt17.finalprojectandroid.data.entity.MyEntity;
import com.jt17.finalprojectandroid.data.mapper.MyMapper;
import com.jt17.finalprojectandroid.domain.model.ItemsModel;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Repository {
    private final MainDao dao;

    public Repository(MainDao dao) {
        this.dao = dao;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Flowable<List<ItemsModel>> getAllItems() {
        return dao.getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(entity -> entity.stream()
                        .map(MyMapper::toItemsModel)
                        .collect(Collectors.toList())
                );
    }

    public Completable insertData(String name, String sum) {
        return Completable.fromAction(() -> {
                    final MyEntity entity = new MyEntity(name, sum);
                    dao.insertData(entity);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteItem(int id) {
        return Completable.fromAction(() -> dao.deleteData(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable clearAllItems() {
        return Completable.fromAction(dao::clearAllData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
