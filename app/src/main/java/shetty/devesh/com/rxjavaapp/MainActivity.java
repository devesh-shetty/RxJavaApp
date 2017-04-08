package shetty.devesh.com.rxjavaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.operators.maybe.MaybeObserveOn;
import io.reactivex.internal.operators.single.SingleObserveOn;

public class MainActivity extends AppCompatActivity {

  public static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Observable<Integer> observable = Observable.create(e -> {
      //use onNext() to emit each item in the stream
      e.onNext(0);
      e.onNext(1);
      e.onNext(2);
      e.onNext(3);
      e.onNext(4);

      //call onComplete when the all the items have been emitted sequentially
      e.onComplete();
    });



    Observer<Integer> observer = new Observer<Integer>() {
      @Override
      public void onSubscribe(Disposable d) {
        Log.e(TAG, "onSubscribe");
      }
      @Override
      public void onNext(Integer integer) {
        Log.e(TAG, "onNext: "+ integer);
      }
      @Override
      public void onError(Throwable e) {
        Log.e(TAG, "onError: " );
      }
      @Override
      public void onComplete() {
        Log.e(TAG, "onComplete");
      }
    };

    //create our subscription
    observable.subscribe(observer);


    //creates 5 items starting from integer 10
    Observable<Integer> observableRange = Observable.range(10, 5);
    observableRange.reduce(new BiFunction<Integer, Integer, Integer>() {
      @Override
      public Integer apply(Integer integer, Integer integer2) throws Exception {
        return integer + integer2;
      }
    }).subscribe(getSingleObserverOfTypeInteger());

  }

  private static MaybeObserver<Integer> getSingleObserverOfTypeInteger(){
    return new MaybeObserver<Integer>() {

      @Override
      public void onSubscribe(Disposable d) {
        Log.d(TAG, "MaybeObserver: onSubscribe");
      }
      @Override
      public void onSuccess(Integer integer) {
        Log.d(TAG, "MaybeObserver: onSuccess: "+integer);
      }
      @Override
      public void onError(Throwable e) {
        Log.d(TAG, "MaybeObserver: onError");
      }
      @Override
      public void onComplete() {
        Log.d(TAG, "MaybeObserver: onComplete");
      }
    };
  }

}
