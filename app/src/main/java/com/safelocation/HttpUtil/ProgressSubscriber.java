package com.safelocation.HttpUtil;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by Juliet on 2017/2/20.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context context;


    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        //this.context = context;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onCompleted() {
        //Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
        Log.d("###onCompleted","onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        //Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        Log.d("###onError",e.getMessage());

    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }
}