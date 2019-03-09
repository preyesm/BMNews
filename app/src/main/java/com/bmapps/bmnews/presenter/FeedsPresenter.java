package com.bmapps.bmnews.presenter;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.ViewDetails.FeedViewDetails;
import com.bmapps.bmnews.interaction.RxMultiStringValues;
import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.response.NewsFeed;
import com.bmapps.bmnews.network.response.ResponseObserver;
import com.bmapps.bmnews.repository.NetworkRepository;
import com.bmapps.bmnews.ui.sectionItems.TextUrlFeedSectionItem;
import com.bmapps.bmnews.utils.CollectionUtils;
import com.bmapps.bmnews.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import eu.davidea.flexibleadapter.items.IFlexible;

import static com.bmapps.bmnews.utils.FinalValues.DETAIL_VIEW;
import static com.bmapps.bmnews.utils.FinalValues.LIST_VIEW;
import static com.bmapps.bmnews.utils.FinalValues.MINIMUM_FEED_COUNT;
import static com.bmapps.bmnews.utils.FinalValues.MULTI_STRING_BUS_FOR;
import static com.bmapps.bmnews.utils.FinalValues.MULTI_STRING_BUS_VALUE;
import static com.bmapps.bmnews.utils.FinalValues.OPEN_URL;


public class FeedsPresenter extends FeedsNullCheckPresenter implements FeedsPresenterInterface {
    @Inject
    NetworkRepository networkRepository;

    @Inject
    CollectionUtils collectionUtils;

    @Inject
    StringUtils stringUtils;

    @Inject
    RxMultiStringValues rxMultiStringValues;

    private FeedViewDetails feedViewDetails;

    private String detailsPageTitle = "";

    // default type of feeds is top and topics selected  is AllTopics
    private String countryCode = "in", category = "Entertainment", source = "cnet.com";

    private Integer groupId, currentLoadedCount = 0;

    private boolean firstTimeFetch = true, groupChanged = true;

    private int normalFeedsCount, editFeedItemPosition, totalFeedCount;

    @Inject
    public FeedsPresenter(NewsApplication application) {
        application.getApplicationComponent().inject(this);
    }

    @Override
    public void setIncomingFrom(String incomingFrom) {
        setupSubscribers();
        switch (incomingFrom) {
            case LIST_VIEW:
                countryCode = "top";
                groupId = null;
                checkTypeOfFeedsAndFetch();
                break;
            case DETAIL_VIEW:
                break;
        }
    }

    @Override
    public void refreshPage() {
        checkTypeOfFeedsAndFetch();
    }

    @Override
    public void changeGroupId(int groupId) {
        this.groupId = groupId;
        groupChanged = true;
        checkTypeOfFeedsAndFetch();
    }

    @Override
    public void changeTopic(String topicString) {
        this.category = topicString;
        checkTypeOfFeedsAndFetch();
    }

    @Override
    public void changeFeedType(String feedType) {
        this.countryCode = feedType.toLowerCase();
        checkTypeOfFeedsAndFetch();
    }

    @Override
    public void checkTypeOfFeedsAndFetch() {
        getView().showFeedsShimmer();
        getCompleteList();
    }

    /**
     * The below function is invoked for the first time or whenever group / topic / type of feeds changes.
     * getCompleteFeedIdList is responsible to fetch all the feed ids and
     * the repository stores the first MINIMUM_FEED_COUNT of feeds for immediate access.
     */

    @Override
    public void getCompleteList() {
        ResponseObserver<FeedListResponse> feedsResponseObserver = new ResponseObserver<FeedListResponse>(getView()) {
            @Override
            public void onNext(FeedListResponse newsFeedList) {
                if (newsFeedList != null && newsFeedList.getNewsFeeds() != null) {
                    FeedsPresenter.this.totalFeedCount = newsFeedList.getTotalResults();
                    super.onNext(newsFeedList);
                    currentLoadedCount = newsFeedList.getNewsFeeds().size() < MINIMUM_FEED_COUNT ? newsFeedList.getNewsFeeds().size() : MINIMUM_FEED_COUNT;

                    normalFeedsCount = 0;

                    if (collectionUtils.isEmpty(newsFeedList.getNewsFeeds())) {
                        decideEmptyStateBasedOnTab();
                    } else {
                        getView().hideEmptyState();
                        getView().displayFeedDataByPresenter(populateItemsForListView(newsFeedList), false);
                        if (currentLoadedCount <= MINIMUM_FEED_COUNT) {
                            getView().noMoreContentToLoad();
                        } else {
                            getView().addEndlessScrollListener();
                        }
                    }
                }
                getView().hideShimmer();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                getView().showUserErrorDialog("SOrry no feeds");
            }
        };

        networkRepository.getFeedList(countryCode, category, source, getView())
                .subscribe(feedsResponseObserver);
    }

    @Override
    public void decideEmptyStateBasedOnTab() {
        getView().showEmptyState();
    }

    @Override
    public Integer getGroupId() {
        return groupId;
    }

    @Override
    public void getNextSetOfFeeds() {
        String feedIdsInComma = "";
        int pendingFeeds = totalFeedCount - currentLoadedCount;
        int loadableFeeds = pendingFeeds > MINIMUM_FEED_COUNT ? MINIMUM_FEED_COUNT : pendingFeeds;

//        if (pendingFeeds > 0) {
//            int feedStartPointer, feedEndPointer = (currentLoadedCount + loadableFeeds) - 1;// doing a -1 in loadable feeds to stop adding comma before the last feed item..
//
//            for (feedStartPointer = currentLoadedCount; feedStartPointer < feedEndPointer; feedStartPointer++) {
//                feedIdsInComma = feedIdsInComma.concat(String.valueOf(feedIds.get(feedStartPointer))).concat(",");
//            }
//
//            feedIdsInComma = feedIdsInComma.concat(String.valueOf(feedIds.get(feedStartPointer)));
//            //System.out.println("feeds in comma -->" + feedIdsInComma);
//
//            networkRepository.getNextSetOfFeeds(feedIdsInComma, getView())
//                    .subscribe(new ResponseObserver<FeedListResponse>(getView()) {
//                        @Override
//                        public void onNext(FeedListResponse feedListResponse) {
//                            super.onNext(feedListResponse);
//                            currentLoadedCount += loadableFeeds;
//                            // whenever the count of feeds % MINIMUM_FEED_COUNT = 0, first condition would fail and hence we add one
//                            // more condition to check
//                            if (currentLoadedCount == feedIds.size()) {
//                                getView().noMoreContentToLoad();
//                            }
//                            getView().displayFeedDataByPresenter(populateItemsForListView(new NewsAndRelatedObjects(feedListResponse)), true);
//                        }
//                    });
//        }
    }

    @Override
    public List<IFlexible> populateItemsForListView(FeedListResponse newsFeedList) {
        List<IFlexible> iFlexibleList = new ArrayList<>();
        if (newsFeedList != null) {
            if (newsFeedList.getNewsFeeds() != null && !collectionUtils.isEmpty(newsFeedList.getNewsFeeds())) {
                int feedsToLoad = newsFeedList.getNewsFeeds().size();
                for (int index = 0; index < feedsToLoad; index++) {

                    NewsFeed feedData = newsFeedList.getNewsFeeds().get(index);
                    if (feedData != null) {
                        upsertFeedViewDetails(feedData, new FeedViewDetails(), iFlexibleList);
                        normalFeedsCount++;
                    }
                }
            }
        }
        return iFlexibleList;
    }

    @Override
    public void fetchFeedDetails() {
//        if (skip == 0) {
//            getView().showLoadingWithMessage("");
//        }
//        networkRepository.getFeedDetailsById(feedViewDetails.feedId, getView(), skip, limit, ignoreCommentIds)
//                .subscribe(new ResponseObserver<FeedDetails>(getView()) {
//                    @Override
//                    public void onNext(FeedDetails feedDetails) {
//                        super.onNext(feedDetails);
//                        List<IFlexible> iFlexibles = new ArrayList<>();
//                        upsertFeedViewDetails(feedDetails, feedViewDetails, iFlexibles);
//                        feedViewDetails.isDetailsPage = true;
//                        getView().displayFeedDataByPresenter(iFlexibles, false);
//                        // handling pagination for comments
//                        if (skip == 0 && feedDetails.getTotalCommentsCount() > limit) {
//                            getView().addEndlessScrollListener();
//                        }
//                    }
//                });
    }

    @Override
    public void upsertFeedViewDetails(NewsFeed newsFeed, FeedViewDetails feedViewDetails, List<IFlexible> iFlexibleList) {
        if (newsFeed != null) {
            feedViewDetails = networkRepository.getFeedViewDetailsFromFeedData(newsFeed, feedViewDetails);

            IFlexible iFlexible = createSectionItemsBasedOnType(feedViewDetails);
            if (iFlexible != null) {
                iFlexibleList.add(iFlexible);
            }
        }
    }

    @Override
    public IFlexible createSectionItemsBasedOnType(FeedViewDetails feedViewDetails) {
        if (feedViewDetails != null && getView().getActivity() != null) {
            return new TextUrlFeedSectionItem(getView().getActivity(), feedViewDetails, rxMultiStringValues);
        }
        return null;
    }

    @Override
    public void setupSubscribers() {
        rxMultiStringValues.getObservable().subscribe(new ResponseObserver<Map<String, String>>(getView()) {
            @Override
            public void onNext(Map<String, String> stringStringMap) {
                super.onNext(stringStringMap);
                if (stringStringMap != null) {
                    String busFor = stringStringMap.get(MULTI_STRING_BUS_FOR);
                    String valueInBus = stringStringMap.get(MULTI_STRING_BUS_VALUE);
                    if (busFor != null) {
                        switch (busFor) {
                            case OPEN_URL:
                                getView().openUrl(valueInBus);
                                break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public void resetIsDetailsPage() {
//        if (feedViewDetails != null) {
//            feedViewDetails.isDetailsPage = !feedViewDetails.isDetailsPage;
//            if (!feedViewDetails.isDetailsPage && feedViewDetails.commentViewDetailsList.size() > 1 &&
//                    topCommentViewDetails != null) {
//                feedViewDetails.commentViewDetailsList.clear();
//                feedViewDetails.commentViewDetailsList.add(topCommentViewDetails);
//            }
//        }
    }

    @Override
    public String getDetailsPageTitle() {
        return this.detailsPageTitle;
    }
}