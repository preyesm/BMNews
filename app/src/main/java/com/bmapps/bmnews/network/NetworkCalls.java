package com.bmapps.bmnews.network;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.network.response.ErrorResponse;
import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.retrofitApis.NewsFeedAPIs;
import com.bmapps.bmnews.ui.BaseView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NetworkCalls {

    @Inject
    NewsFeedAPIs newsFeedAPIs;

    @Inject
    ErrorResponse errorResponse;

    @Inject
    public NetworkCalls(NewsApplication application) {
        application.getApplicationComponent().inject(this);
    }

    public Observable<Response<FeedListResponse>> getAllNews(BaseView baseView) {
        return newsFeedAPIs.getNewsFeeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> errorResponse.showError(baseView, throwable));
    }
}
