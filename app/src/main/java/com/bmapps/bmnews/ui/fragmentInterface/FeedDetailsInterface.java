package com.bmapps.bmnews.ui.fragmentInterface;

import android.app.Activity;

import com.bmapps.bmnews.ui.BaseView;
import com.bmapps.bmnews.ui.fragments.BaseFragment;
import com.bmapps.bmnews.viewDetails.FeedViewDetails;

import java.util.List;

import eu.davidea.flexibleadapter.items.IFlexible;

public interface FeedDetailsInterface extends BaseView {
    void init();

    Activity getActivity();

    void displayFeedDataByPresenter(List<IFlexible> iFlexibles, boolean isAddedToList);

    void addEndlessScrollListener();

    void noMoreContentToLoad();

    IFlexible getIFlexibleFromPosition(int position);

    void openFeedDetails(String url);

    void setUpSubscribers();

    void showFullShimmer();

    void hideShimmer();

    void showFeedsShimmer();

    void hideFeedsShimmer();

    FeedViewDetails getFeedViewDetails();

    void openUrl(String url);

    void loadChildFragment(BaseFragment baseFragment);

    void updateItemAtPosition(IFlexible iFlexible, int position);

    void removeChildFragment();

    void shareFeed(String url);

    void showEmptyState();

    void hideEmptyState();

    void setSourceListAdapter(List<String> strings);

    void preSelectAutoCompleteView(String key, String value);
}
