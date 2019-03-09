package com.bmapps.bmnews.dagger;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.network.NetworkCalls;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApplicationModule.class)
public class NetworkModule {

    @Provides
    @Singleton
    NetworkCalls networkCalls(NewsApplication newsApplication) {
        return new NetworkCalls(newsApplication);
    }
}
