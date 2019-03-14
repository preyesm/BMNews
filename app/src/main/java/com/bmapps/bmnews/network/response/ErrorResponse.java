package com.bmapps.bmnews.network.response;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.ui.BaseView;
import com.bmapps.bmnews.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;

import static com.bmapps.bmnews.dagger.APIModule.RESPONSE_401;
import static com.bmapps.bmnews.dagger.APIModule.RESPONSE_404;
import static com.bmapps.bmnews.dagger.APIModule.RESPONSE_500;

public class ErrorResponse {

    @Inject
    Gson gson;

    @Inject
    StringUtils stringUtils;

    @Inject
    public ErrorResponse(NewsApplication application) {
        application.getApplicationComponent().inject(this);
    }

    public void showError(BaseView baseView, Throwable e) {
        baseView.hideLoader();
        String message = e.getMessage();
        if (message.contains("newsapi.org")) {
            message = "Please check your Internet Connection";
        } else {
            message = "Something went wrong!";
        }
        //System.out.println("stacktrace-->");
        e.printStackTrace();
        baseView.showFetchingLayoutWithMessage(message);
    }

    public void processApiError(Response response, BaseView baseView) {
        TypeAdapter<BaseResponse> adapter = gson.getAdapter(BaseResponse.class);
        try {
            baseView.hideLoader();
            if (response.errorBody() != null) {

                System.out.println("error response-->"+response.code());
                BaseResponse baseResponse = adapter.fromJson(response.errorBody().string());
                int code = response.code();
                String message = baseResponse.code;
                if (code >= 400 && code < 500) {
                    switch (code) {
                        case RESPONSE_401:
                            baseView.unauthorizedQuitApp();
                            break;
                        case RESPONSE_404:
                            baseView.objectNotFound();
                            break;
                    }
                    if (!stringUtils.isEmpty(message))
                        baseView.showUserErrorDialog(message);
                } else if (code == RESPONSE_500) {
                    baseView.showNetworkErrorMessage();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
