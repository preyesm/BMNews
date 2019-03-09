package com.bmapps.bmnews.presenter;


import android.util.Log;

public abstract class BasePresenter<T> {
    private T mView;
    private T mDummyView;

    public BasePresenter() {
        mDummyView = createDummyView();
    }

    public void setView(T view){
        this.mView = view;
    }

    public void onDetachView(){
        this.mView = null;
    }

    public abstract T createDummyView();

    public T getView(){
        if(mView == null){
            Log.e(BasePresenter.class.getSimpleName(), "getView: view is null, returning dummy view.");
            return mDummyView;
        } else {
            return mView;
        }
    }
}
