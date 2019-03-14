package com.bmapps.bmnews.presenter;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.interaction.RxMultiStringValues;
import com.bmapps.bmnews.network.response.CompleteNewsSourceInfo;
import com.bmapps.bmnews.network.response.FeedListResponse;
import com.bmapps.bmnews.network.response.NewsFeed;
import com.bmapps.bmnews.network.response.ResponseObserver;
import com.bmapps.bmnews.network.response.SourceList;
import com.bmapps.bmnews.repository.NetworkRepository;
import com.bmapps.bmnews.ui.sectionItems.TextUrlFeedSectionItem;
import com.bmapps.bmnews.utils.CollectionUtils;
import com.bmapps.bmnews.utils.StringUtils;
import com.bmapps.bmnews.viewDetails.FeedViewDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import eu.davidea.flexibleadapter.items.IFlexible;

import static com.bmapps.bmnews.utils.FinalValues.DETAIL_VIEW;
import static com.bmapps.bmnews.utils.FinalValues.LIST_VIEW;
import static com.bmapps.bmnews.utils.FinalValues.MINIMUM_FEED_COUNT;
import static com.bmapps.bmnews.utils.FinalValues.MULTI_STRING_BUS_FOR;
import static com.bmapps.bmnews.utils.FinalValues.MULTI_STRING_BUS_VALUE;
import static com.bmapps.bmnews.utils.FinalValues.OPEN_DETAIL_PAGE;


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
    private String countryCode, category = "Entertainment", source = null;

    private Integer groupId, currentLoadedCount = 0;

    private int pageCounter, totalFeedCount;

    private NewsApplication newsApplication;

    @Inject
    public FeedsPresenter(NewsApplication application) {
        this.newsApplication = application;
        application.getApplicationComponent().inject(this);
    }

    @Override
    public void setIncomingFrom(String incomingFrom) {
        setupSubscribers();
        switch (incomingFrom) {
            case LIST_VIEW:
                countryCode = "in";
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
    public void changeSource(String source) {
        this.source = source;
        countryCode = null;
        category = null;
        checkTypeOfFeedsAndFetch();
    }

    @Override
    public void changeCountry(String countryCode) {
        this.countryCode = countryCode.toLowerCase();
        source = null;
        checkTypeOfFeedsAndFetch();
    }

    @Override
    public void changeCategory(String category) {
        this.category = category;
        source = null;
        checkTypeOfFeedsAndFetch();
    }

    @Override
    public void checkTypeOfFeedsAndFetch() {
        getView().showFullShimmer();
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

                    pageCounter = 0;

                    if (collectionUtils.isEmpty(newsFeedList.getNewsFeeds())) {
                        decideEmptyStateBasedOnTab();
                    } else {
                        getView().hideEmptyState();
                        getView().displayFeedDataByPresenter(populateItemsForListView(newsFeedList), false);
                        if (totalFeedCount <= currentLoadedCount) {
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
                getView().hideShimmer();
                if (totalFeedCount == 0) {
                    getView().showEmptyState();
                }
            }
        };

        networkRepository.getFeedList(countryCode, category, source, getView())
                .subscribe(feedsResponseObserver);

        networkRepository.getSourceList(getView())
                .subscribe(new ResponseObserver<SourceList>(getView()) {
                    @Override
                    public void onNext(SourceList sourceList) {
                        super.onNext(sourceList);
                        List<String> stringList = new ArrayList<>();
                        for (CompleteNewsSourceInfo completeNewsSourceInfo : sourceList.getCompleteNewsSourceInfos()) {
                            stringList.add(completeNewsSourceInfo.getName());
                        }
                        Collections.sort(stringList);
                        getView().setSourceListAdapter(stringList);
                    }
                });
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
        int pendingFeeds = totalFeedCount - currentLoadedCount;
        int loadableFeeds = pendingFeeds > MINIMUM_FEED_COUNT ? MINIMUM_FEED_COUNT : pendingFeeds;

        if (loadableFeeds > 0) {

            networkRepository.getNextSetOfFeeds(countryCode, category, source, ++pageCounter, getView())
                    .subscribe(new ResponseObserver<FeedListResponse>(getView()) {
                        @Override
                        public void onNext(FeedListResponse feedListResponse) {
                            super.onNext(feedListResponse);
                            currentLoadedCount += loadableFeeds;
                            // whenever the count of feeds % MINIMUM_FEED_COUNT = 0, first condition would fail and hence we add one
                            // more condition to check
                            if (currentLoadedCount == totalFeedCount) {
                                getView().noMoreContentToLoad();
                            }
                            getView().displayFeedDataByPresenter(populateItemsForListView(feedListResponse), true);
                        }
                    });
        }
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
            return new TextUrlFeedSectionItem(newsApplication, feedViewDetails, rxMultiStringValues);
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
                            case OPEN_DETAIL_PAGE:
                                getView().openFeedDetails(valueInBus);
                                break;
                        }
                    }
                }
            }
        });
    }
}