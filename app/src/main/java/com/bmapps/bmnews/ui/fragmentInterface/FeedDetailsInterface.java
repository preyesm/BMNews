package com.bmapps.bmnews.ui.fragmentInterface;

import android.app.Activity;

import com.bmapps.bmnews.viewDetails.FeedViewDetails;
import com.bmapps.bmnews.ui.BaseView;
import com.bmapps.bmnews.ui.fragments.BaseFragment;

import java.util.List;

import eu.davidea.flexibleadapter.items.IFlexible;

public interface FeedDetailsInterface extends BaseView {
    void init();

    Activity getActivity();

    void displayFeedDataByPresenter(List<IFlexible> iFlexibles, boolean isAddedToList);

    void addEndlessScrollListener();

    void noMoreContentToLoad();

    default IFlexible getIFlexibleFromPosition(int position) {
        return null;
    }

    default void openFeedDetails(FeedViewDetails feedViewDetails) {

    }

    default void setUpSubscribers() {

    }

    default void showFullShimmer() {
    }

    default void hideShimmer() {
    }

    default FeedViewDetails getFeedViewDetails() {
        return new FeedViewDetails();
    }


    void openUrl(String url);

    void loadChildFragment(BaseFragment baseFragment);

    void updateItemAtPosition(IFlexible iFlexible, int position);

    void removeChildFragment();

    void shareFeed(String url);

    default void showEmptyState() {

    }

    default void hideEmptyState() {

    }
}
