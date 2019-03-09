package com.bmapps.bmnews.network.response;


import com.bmapps.bmnews.ui.BaseView;

import io.reactivex.observers.DisposableObserver;

public abstract class ResponseObserver<T> extends DisposableObserver<T> {

    BaseView baseView;

    public ResponseObserver(BaseView view) {
        this.baseView = view;
    }

    @Override
    public void onNext(T t) {
        if (baseView != null)
            baseView.hideLoader();
    }

    @Override
    public void onError(Throwable e) {
        if (baseView != null)
            baseView.hideLoader();
    }

    @Override
    public void onComplete() {
        if (baseView != null)
            baseView.hideLoader();
    }
}