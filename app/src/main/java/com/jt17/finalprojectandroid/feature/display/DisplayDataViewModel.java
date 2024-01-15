package com.jt17.finalprojectandroid.feature.display;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.jt17.finalprojectandroid.data.db.AppDatabase;
import com.jt17.finalprojectandroid.domain.model.ItemsModel;
import com.jt17.finalprojectandroid.domain.repository.Repository;
import com.jt17.finalprojectandroid.manager.ActivityStopCounterManager;
import com.jt17.finalprojectandroid.manager.MyNotificationManager;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class DisplayDataViewModel extends AndroidViewModel {
    private final Repository repository;
    private final MyNotificationManager myNotificationManager;

    public DisplayDataViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.repository = new Repository(appDatabase.mainDao());
        this.myNotificationManager = new MyNotificationManager(application.getApplicationContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<List<ItemsModel>> getAllItemsData() {
        return LiveDataReactiveStreams.fromPublisher(repository.getAllItems());
    }

    public Completable deleteItem(int id) {
        return repository.deleteItem(id);
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void onStopCountingAndShowNotification() {
        ActivityStopCounterManager.incrementDisplayStop();
        final Disposable disposable = ActivityStopCounterManager.countingOnStop()
                .subscribe(myNotificationManager::updateNotification);

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
