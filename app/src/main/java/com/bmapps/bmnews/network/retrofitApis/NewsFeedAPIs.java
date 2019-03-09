package com.bmapps.bmnews.network.retrofitApis;

import com.bmapps.bmnews.network.response.FeedListResponse;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

import static com.bmapps.bmnews.dagger.APIModule.API_VERSION_2;

public interface NewsFeedAPIs {

    @GET(API_VERSION_2 + "/")
    Observable<Response<FeedListResponse>> getNewsFeeds();
}
