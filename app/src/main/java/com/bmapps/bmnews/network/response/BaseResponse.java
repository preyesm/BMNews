package com.bmapps.bmnews.network.response;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("message")
    public String message;

    @SerializedName("code")
    public String code;


}
