package com.bmapps.bmnews.interaction;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxMultiStringValues implements Parcelable {

    private Subject<Map<String, String>> subject = PublishSubject.create();

    private Subject<String> searchSubject = PublishSubject.create();

    private Subject<Map<String, Object>> genericSubject = PublishSubject.create();

    public static final Parcelable.Creator<RxMultiStringValues> CREATOR
            = new Parcelable.Creator<RxMultiStringValues>() {
        public RxMultiStringValues createFromParcel(Parcel in) {
            return new RxMultiStringValues();
        }

        public RxMultiStringValues[] newArray(int size) {
            return new RxMultiStringValues[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public void sendData(Map<String, String> value) {
        subject.onNext(value);
    }

    public Observable<Map<String, String>> getObservable() {
        return subject;
    }

    public void sendSearchData(String value) {
        searchSubject.onNext(value);
    }

    public Observable<String> getSearchObservable() {
        return searchSubject;
    }

    public void sendGenericData(Map<String, Object> value) {
        genericSubject.onNext(value);
    }

    public Observable<Map<String, Object>> getGenericObservable() {
        return genericSubject;
    }
}