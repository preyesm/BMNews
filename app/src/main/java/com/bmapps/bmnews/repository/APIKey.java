package com.bmapps.bmnews.repository;

import io.realm.RealmObject;

public class APIKey extends RealmObject {

    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
