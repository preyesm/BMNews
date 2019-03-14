package com.bmapps.bmnews.network;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.network.response.ErrorResponse;
import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.response.SourceList;
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

    public Observable<Response<FeedListResponse>> getAllNews(String countryCode, String category, String source, int startPage, BaseView baseView) {
        return newsFeedAPIs.getNewsFeeds(countryCode, category, source, startPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> errorResponse.showError(baseView, throwable));
    }

    public Observable<Response<SourceList>> getAllNewsSource(BaseView baseView) {
        return newsFeedAPIs.getAllNewsSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> errorResponse.showError(baseView, throwable));
    }
}
