package com.bmapps.bmnews.dagger;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.interaction.RxMultiStringValues;
import com.bmapps.bmnews.utils.CollectionUtils;
import com.bmapps.bmnews.utils.ImageUtils;
import com.bmapps.bmnews.utils.RxDialogBox;
import com.bmapps.bmnews.utils.StringUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ApplicationModule.class)
public class UIModules {

    private NewsApplication application;

    public UIModules(NewsApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public StringUtils providesStringUtils(NewsApplication application) {
        return new StringUtils();
    }

    @Provides
    public RxMultiStringValues getRxStringBusForTwoStrings(NewsApplication application) {
        return new RxMultiStringValues();
    }

    @Provides
    @Singleton
    public CollectionUtils provideCollectionUtils(NewsApplication application) {
        return new CollectionUtils(application);
    }

    @Provides
    @Singleton
    public ImageUtils provideImageUtils(NewsApplication application) {
        return new ImageUtils(application);
    }

    @Provides
    @Singleton
    public RxDialogBox provideRxDialog(NewsApplication application) {
        return new RxDialogBox();
    }
}
