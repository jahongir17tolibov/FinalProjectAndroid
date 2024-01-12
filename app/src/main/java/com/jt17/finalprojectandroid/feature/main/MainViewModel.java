package com.jt17.finalprojectandroid.feature.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jt17.finalprojectandroid.data.db.AppDatabase;
import com.jt17.finalprojectandroid.domain.repository.Repository;
import com.jt17.finalprojectandroid.manager.ActivityStopCounterManager;
import com.jt17.finalprojectandroid.manager.MyNotificationManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainViewModel extends AndroidViewModel {
    private final Repository repository;
    private final MyNotificationManager myNotificationManager;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.repository = new Repository(appDatabase.mainDao());
        this.myNotificationManager = new MyNotificationManager(application.getApplicationContext());
    }

    public Completable insertData(String name, String sum) {
        return repository.insertData(name, sum);
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public Completable clearAll() {
        return repository.clearAllItems();
    }

    public void onStopCountingAndShowNotification() {
        ActivityStopCounterManager.incrementMainStop();
        final Disposable disposable = ActivityStopCounterManager.countingOnStop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myNotificationManager::updateNotification);

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
