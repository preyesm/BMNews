package com.bmapps.bmnews.network.response;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class SourceList extends RealmObject {
    @SerializedName("sources")
    private RealmList<CompleteNewsSourceInfo> completeNewsSourceInfos;

    public RealmList<CompleteNewsSourceInfo> getCompleteNewsSourceInfos() {
        return completeNewsSourceInfos;
    }

    public void setCompleteNewsSourceInfos(RealmList<CompleteNewsSourceInfo> completeNewsSourceInfos) {
        this.completeNewsSourceInfos = completeNewsSourceInfos;
    }
}
