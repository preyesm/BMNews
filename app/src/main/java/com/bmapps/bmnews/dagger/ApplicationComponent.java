package com.bmapps.bmnews.dagger;

import com.bmapps.bmnews.network.NetworkCalls;
import com.bmapps.bmnews.network.response.ErrorResponse;
import com.bmapps.bmnews.presenter.FeedsPresenter;
import com.bmapps.bmnews.repository.NetworkRepository;
import com.bmapps.bmnews.ui.fragments.FeedListFragment;
import com.bmapps.bmnews.utils.CollectionUtils;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        APIModule.class,
        UIModules.class})
public interface ApplicationComponent {

    //Networking
    void inject(ErrorResponse errorResponse);

    void inject(NetworkCalls networkCalls);

    //repository
    void inject(NetworkRepository networkRepository);

    //presenters
    void inject(FeedsPresenter feedsPresenter);

    //ui
    void inject(FeedListFragment feedListFragment);

    //utils
    void inject(CollectionUtils collectionUtils);


}
