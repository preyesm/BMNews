package com.bmapps.bmnews.presenter;

import com.bmapps.bmnews.ViewDetails.FeedViewDetails;
import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.response.NewsFeed;

import java.util.List;

import eu.davidea.flexibleadapter.items.IFlexible;

public interface FeedsPresenterInterface {
    void setIncomingFrom(String incomingFrom);

    default void refreshPage() {
    }


    default Integer getGroupId() {
        return 1;
    }

    void changeGroupId(int groupId);

    void changeFeedType(String feedType);

    default void changeTopic(String topicString) {
    }

    void getCompleteList();

    void fetchFeedDetails();

    void upsertFeedViewDetails(NewsFeed newsFeed, FeedViewDetails feedViewDetails, List<IFlexible> iFlexibleList);

    void resetIsDetailsPage();

    default void checkTypeOfFeedsAndFetch() {
    }

    IFlexible createSectionItemsBasedOnType(FeedViewDetails feedViewDetails);

    List<IFlexible> populateItemsForListView(FeedListResponse feedAndRelatedObjects);

    default void getNextSetOfFeeds() {
    }


    void setupSubscribers();

    String getDetailsPageTitle();

    void decideEmptyStateBasedOnTab();

}
