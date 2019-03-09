package com.bmapps.bmnews.dagger;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.repository.NetworkRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApplicationModule.class)
public class RepositoryModule {

    public RepositoryModule() {
    }

    @Provides
    @Singleton
    NetworkRepository networkCalls(NewsApplication newsApplication) {
        return new NetworkRepository(newsApplication);
    }
}
