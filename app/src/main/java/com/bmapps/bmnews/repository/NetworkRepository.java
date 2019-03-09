package com.bmapps.bmnews.repository;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.ViewDetails.FeedViewDetails;
import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.response.NewsFeed;
import com.bmapps.bmnews.ui.BaseView;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NetworkRepository {

    @Inject
    public NetworkRepository(NewsApplication application) {

    }

    public Observable<FeedListResponse> getNextSetOfFeeds() {
        return null;
    }

    public Observable<FeedListResponse> getFeedList(String countryCode, String category, String source, BaseView baseView) {
        return Observable.error(new Throwable());
    }

    public FeedViewDetails getFeedViewDetailsFromFeedData(NewsFeed newsFeed, FeedViewDetails feedViewDetails) {
        return feedViewDetails;
    }
}
