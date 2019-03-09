package com.bmapps.bmnews.network.response;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;

public class FeedListResponse extends BaseResponse {

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private RealmList<NewsFeed> newsFeeds;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public RealmList<NewsFeed> getNewsFeeds() {
        return newsFeeds;
    }

    public void setNewsFeeds(RealmList<NewsFeed> newsFeeds) {
        this.newsFeeds = newsFeeds;
    }
}
