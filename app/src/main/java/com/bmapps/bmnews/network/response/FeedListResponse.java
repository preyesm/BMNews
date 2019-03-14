package com.bmapps.bmnews.network.response;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class FeedListResponse extends RealmObject {

    private String countryCode, category, source;

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private RealmList<NewsFeed> newsFeeds;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

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
