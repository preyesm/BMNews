package com.bmapps.bmnews;

import android.app.Application;

import com.bmapps.bmnews.dagger.APIModule;
import com.bmapps.bmnews.dagger.ApplicationComponent;
import com.bmapps.bmnews.dagger.ApplicationModule;
import com.bmapps.bmnews.dagger.DaggerApplicationComponent;
import com.bmapps.bmnews.dagger.RepositoryModule;
import com.bmapps.bmnews.dagger.UIModules;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class NewsApplication extends Application {

    ApplicationComponent applicationComponent;

    Realm realm;

    static NewsApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Realm.init(this);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().name("bmnews").deleteRealmIfMigrationNeeded().build());
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

    public static Realm getRealm() {
        return mInstance.getRealmAPI();
    }

    private synchronized Realm getRealmAPI() {
        if (realm == null) {
            initRealmAPI();
        }
        return realm;
    }

    private void initRealmAPI() {
        try {
            realm = Realm.getDefaultInstance();
            System.out.println("realm created");
        } catch (RealmMigrationNeededException ex) {
            ex.printStackTrace();
            Realm.deleteRealm(new RealmConfiguration.Builder().name("bmnews").deleteRealmIfMigrationNeeded().build());
            realm = Realm.getDefaultInstance();
        }
    }
}
