package com.bmapps.bmnews.dagger;

import com.bmapps.bmnews.NewsApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private NewsApplication application;

    public ApplicationModule(NewsApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public NewsApplication newsApplication() {
        return application;
    }
}
