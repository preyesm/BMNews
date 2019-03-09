package com.bmapps.bmnews;

import android.app.Application;

import com.bmapps.bmnews.dagger.APIModule;
import com.bmapps.bmnews.dagger.ApplicationComponent;
import com.bmapps.bmnews.dagger.ApplicationModule;
import com.bmapps.bmnews.dagger.DaggerApplicationComponent;
import com.bmapps.bmnews.dagger.RepositoryModule;
import com.bmapps.bmnews.dagger.UIModules;

public class NewsApplication extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .aPIModule(new APIModule())
                .repositoryModule(new RepositoryModule())
                .uIModules(new UIModules(this))
                .build();


    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
