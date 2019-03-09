package com.bmapps.bmnews.viewDetails;

public class UrlSnippet {

    private String title, url, imageUrl;

    public UrlSnippet(String url,String title, String imageUrl) {
        this.url = url;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
