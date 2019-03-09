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

    static NewsApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .aPIModule(new APIModule())
                .repositoryModule(new RepositoryModule())
                .uIModules(new UIModules(this))
                .build();
    }


    public static synchronized NewsApplication getInstance() {
        return mInstance;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
