package com.example.javarxlab0103;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable myObservable = Observable.create(source->{
            Log.d(TAG, "onCreate: Hello MAD");
            source.onNext(1);
            source.onNext(2);
            source.onNext(3);
            source.onNext(4);
            source.onNext(5);
        });
         /*`subscribe` is used to initiate the execution of an Observable stream,
         `onNext` is called by the Observable when it emits an item,
         `doOnNext` is used to perform additional actions on the emitted item
         `subscribeOn` spacify the thread for the observable
         `observeOn` specify the Scheduler on which an observer will observe this Observable
          */

        //Schedulers role: provide a way to specify the thread on which the Observable will emit its items
        // and the thread on which the Observer will consume those items.
        // Schdulars.io : IO-bound tasks and uses an unbounded pool of threads.

        myObservable.subscribeOn(Schedulers.io())
                .doOnNext(Integer-> System.out.println("Emiting item "+Integer+ " On:"+Thread.currentThread().getName()))
                .observeOn(Schedulers.newThread())
                .doOnNext(Integer->System.out.println("After Emmiting Item "+ Integer+" On:" + Thread.currentThread().getName()))
                .observeOn(Schedulers.computation())
                .doOnNext(Integer->System.out.println("After Computation Thread Emmiting Item "+ Integer+" On:" + Thread.currentThread().getName()))
                .subscribe(Integer -> System.out.println("Consuming item" + Integer+" On:"+Thread.currentThread().getName()));


        // The `doOnNext` operator can be used to perform additional actions on the emitted item before it is passed on to the Observer.
    }
}