package com.bmapps.bmnews.dagger;


import com.bmapps.bmnews.network.retrofitApis.NewsFeedAPIs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bmapps.bmnews.BuildConfig.BASE_URL;
import static java.util.concurrent.TimeUnit.MINUTES;

@Module
public class APIModule {

    public static final int RESPONSE_200 = 200;

    public static final int RESPONSE_500 = 500;

    public static final int RESPONSE_401 = 401;

    public static final int RESPONSE_404 = 404;

    public static final String API_VERSION_2 = "v2";



    @Provides
    @Singleton
    public Interceptor getInterceptor() {
        return (Interceptor.Chain chain) -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            try {
//                requestBuilder.addHeader("User-Agent", );
//                requestBuilder.addHeader("X-User-Agent", );
                requestBuilder.addHeader("X-Api-Key", "74ff1173a2ad4d03a4f2d5fd423d8cab");
            } catch (Exception e) {
                e.printStackTrace();
            }
            requestBuilder.method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    public OkHttpClient provideClient(Interceptor interceptor) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, MINUTES);
        httpClientBuilder.readTimeout(5, MINUTES);
        httpClientBuilder.writeTimeout(5, MINUTES);
        httpClientBuilder.addInterceptor(interceptor);
        return httpClientBuilder.addInterceptor(httpLoggingInterceptor).build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }

    @Provides
    @Singleton
    public NewsFeedAPIs provideNewsFeedApi(Retrofit retrofit) {
        return retrofit.create(NewsFeedAPIs.class);
    }
}