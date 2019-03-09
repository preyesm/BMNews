package com.bmapps.bmnews.repository;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.network.NetworkCalls;
import com.bmapps.bmnews.network.response.ErrorResponse;
import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.response.NewsFeed;
import com.bmapps.bmnews.ui.BaseView;
import com.bmapps.bmnews.utils.StringUtils;
import com.bmapps.bmnews.viewDetails.FeedViewDetails;
import com.bmapps.bmnews.viewDetails.UrlSnippet;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;

public class NetworkRepository {

    @Inject
    StringUtils stringUtils;

    @Inject
    NetworkCalls networkCalls;

    @Inject
    ErrorResponse errorResponse;

    @Inject
    public NetworkRepository(NewsApplication application) {
        application.getApplicationComponent().inject(this);
    }

    public Observable<FeedListResponse> getNextSetOfFeeds() {
        return null;
    }

    public Observable<FeedListResponse> getFeedList(String countryCode, String category, String source, BaseView baseView) {
        return Observable.mergeDelayError(getFeedsFromCache(countryCode, category, source), getFeedsFromNetwork(countryCode, category, source, baseView));
    }

    private Observable<FeedListResponse> getFeedsFromCache(String countryCode, String category, String source) {
        return Observable.error(new Throwable());
    }

    private Observable<FeedListResponse> getFeedsFromNetwork(String countryCode, String category, String source, BaseView baseView) {
        return networkCalls.getAllNews(countryCode, category, source, baseView)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Objects.requireNonNull(response.body());
                    } else {
                        errorResponse.processApiError(response, baseView);
                        return null;
                    }
                });
    }

    public FeedViewDetails getFeedViewDetailsFromFeedData(NewsFeed newsFeed, FeedViewDetails feedViewDetails) {
        if (newsFeed.getUrl() != null) {
            feedViewDetails.urlSnippet = new UrlSnippet(newsFeed.getUrl(), newsFeed.getDescription(), newsFeed.getUrlToImage());
        }
        feedViewDetails.author = newsFeed.getAuthor();
        feedViewDetails.publishedAt = newsFeed.getPublishedAt();
        feedViewDetails.source = newsFeed.getNewsSource().getName();
        feedViewDetails.title = newsFeed.getTitle();
        return feedViewDetails;
    }
}
