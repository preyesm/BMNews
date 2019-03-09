package com.bmapps.bmnews.presenter;

import com.bmapps.bmnews.ui.fragmentInterface.FeedDetailsInterface;

public class FeedsNullCheckPresenter extends BasePresenter<FeedDetailsInterface> {

    @Override
    public FeedDetailsInterface createDummyView() {
        return null;
    }
}
