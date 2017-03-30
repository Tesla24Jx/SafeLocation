package com.safelocation.HttpUtil;

/**
 * Created by Juliet on 2017/2/20.
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}