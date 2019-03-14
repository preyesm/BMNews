package com.bmapps.bmnews.network.retrofitApis;

import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.response.SourceList;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.bmapps.bmnews.dagger.APIModule.API_VERSION_2;

public interface NewsFeedAPIs {

    @GET(API_VERSION_2 + "/top-headlines")
    Observable<Response<FeedListResponse>> getNewsFeeds(@Query("country") String countryCode,
                                                        @Query("category") String category,
                                                        @Query("source") String source,
                                                        @Query("page") int pageStart);

    @GET(API_VERSION_2 + "/sources")
    Observable<Response<SourceList>> getAllNewsSources();
}
