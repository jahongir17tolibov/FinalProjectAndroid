package com.jt17.finalprojectandroid.manager;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ActivityStopCounterManager {
    private static int mainActivityStopCounter = 0;
    private static int displayActivityStopCounter = 0;

    public static Single<Integer> countingOnStop() {
        return Single.fromCallable(() -> mainActivityStopCounter + displayActivityStopCounter)
                .subscribeOn(Schedulers.io());
    }

    public static void incrementMainStop() {
        mainActivityStopCounter++;
    }

    public static void incrementDisplayStop() {
        displayActivityStopCounter++;
    }

}
