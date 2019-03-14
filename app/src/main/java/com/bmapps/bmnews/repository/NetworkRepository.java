package com.bmapps.bmnews.repository;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.network.NetworkCalls;
import com.bmapps.bmnews.network.response.ErrorResponse;
import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.response.NewsFeed;
import com.bmapps.bmnews.network.response.SourceList;
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

    public Observable<FeedListResponse> getNextSetOfFeeds(String countryCode, String category, String source, int startPage, BaseView baseView) {
        return getFeedsFromNetwork(countryCode, category, source, startPage, baseView);
    }

    public Observable<FeedListResponse> getFeedList(String countryCode, String category, String source, BaseView baseView) {
        return Observable.mergeDelayError(getFeedsFromCache(countryCode, category, source), getFeedsFromNetwork(countryCode, category, source, 0, baseView));
    }

    private Observable<FeedListResponse> getFeedsFromCache(String countryCode, String category, String source) {
        FeedListResponse feedListResponse = NewsApplication.getRealm().where(FeedListResponse.class)
                .beginGroup().isNotEmpty("countryCode").equalTo("countryCode", countryCode).endGroup()
                .or()
                .beginGroup().isNotEmpty("source").equalTo("source", source).endGroup()
                .or()
                .beginGroup().isNotEmpty("category").equalTo("category", category).endGroup()
                .findFirst();
        if (feedListResponse != null) {
            return Observable.just(NewsApplication.getRealm().copyFromRealm(feedListResponse));
        }
        return Observable.error(new Throwable());
    }

    private Observable<FeedListResponse> getFeedsFromNetwork(String countryCode, String category, String source, int startPage, BaseView baseView) {
        return networkCalls.getAllNews(countryCode, category, source, startPage, baseView)
                .map(response -> {
                    if (response.isSuccessful()) {
                        FeedListResponse feedListResponse = Objects.requireNonNull(response.body());
                        NewsApplication.getRealm().executeTransaction(realm -> {
                            if (startPage == 0) {
                                realm.where(FeedListResponse.class).findAll().deleteAllFromRealm();
                                feedListResponse.setCountryCode(countryCode);
                                feedListResponse.setSource(source);
                                feedListResponse.setCategory(category);
                                realm.insertOrUpdate(feedListResponse);
                            }

                        });
                        return feedListResponse;
                    } else {
                        errorResponse.processApiError(response, baseView);
                        return null;
                    }
                });
    }

    public Observable<SourceList> getSourceList(BaseView baseView) {
        return Observable.mergeDelayError(getSourceListFromCache(), getAllSourceFromNetwork(baseView));
    }

    private Observable<SourceList> getSourceListFromCache() {
        SourceList sourceList = NewsApplication.getRealm().where(SourceList.class).findFirst();
        if (sourceList != null) {
            return Observable.just(NewsApplication.getRealm().copyFromRealm(sourceList));
        }
        return Observable.error(new Throwable());
    }


    private Observable<SourceList> getAllSourceFromNetwork(BaseView baseView) {
        return networkCalls.getAllNewsSource(baseView)
                .map(response -> {
                    if (response.isSuccessful()) {
                        SourceList sourceList = Objects.requireNonNull(response.body());
                        NewsApplication.getRealm().executeTransaction(realm -> {
                            realm.where(SourceList.class).findAll().deleteAllFromRealm();
                            realm.insertOrUpdate(sourceList);
                        });
                        return sourceList;
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
