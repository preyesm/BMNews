package com.bmapps.bmnews.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.R;
import com.bmapps.bmnews.ViewDetails.FeedViewDetails;
import com.bmapps.bmnews.databinding.FeedListViewBinding;
import com.bmapps.bmnews.interaction.RxMultiStringValues;
import com.bmapps.bmnews.network.response.ResponseObserver;
import com.bmapps.bmnews.presenter.FeedsPresenter;
import com.bmapps.bmnews.ui.fragmentInterface.FeedDetailsInterface;
import com.bmapps.bmnews.utils.CollectionUtils;
import com.bmapps.bmnews.utils.ImageUtils;
import com.bmapps.bmnews.utils.ProgressItem;
import com.bmapps.bmnews.utils.RxDialogBox;
import com.bmapps.bmnews.utils.StringUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.IHolder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bmapps.bmnews.utils.FinalValues.LIST_VIEW;
import static com.bmapps.bmnews.utils.FinalValues.MULTI_STRING_BUS_FOR;

public class FeedListFragment extends BaseFragment implements FeedDetailsInterface,
        FlexibleAdapter.EndlessScrollListener {

    @Inject
    FeedsPresenter presenter;

    @Inject
    CollectionUtils collectionUtils;

    @Inject
    ImageUtils imageUtils;

    @Inject
    RxMultiStringValues multiStringValues;

    @Inject
    StringUtils stringUtils;

    @Inject
    RxDialogBox rxDialogBox;

    FeedListViewBinding binding;

    BaseFragment childFragment;

    FlexibleAdapter flexibleAdapter;

    private boolean swipedDown;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.feed_list_view, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((NewsApplication) activity.getApplication()).getApplicationComponent().inject(this);

        init();

        presenter.setView(this);

        presenter.setIncomingFrom(LIST_VIEW);
    }


    @Override
    public void init() {

        setUpSubscribers();

        showFullShimmer();

        binding.swipeRefreshLayout.setEnabled(true);
        binding.swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            swipedDown = true;
            showFeedsShimmer();
            presenter.refreshPage();
        });

        flexibleAdapter = collectionUtils.setUpRecyclerView(binding.feedList, null, false);

        flexibleAdapter.mItemClickListener = (view, position) -> {
            IFlexible item = flexibleAdapter.getItem(position);
            if (item instanceof IHolder && ((IHolder) item).getModel() instanceof FeedViewDetails) {
//                openFeedDetails(((FeedViewDetails) ((IHolder) item).getModel()), false, null);
            }
            return false;
        };

    }

    @Override
    public IFlexible getIFlexibleFromPosition(int position) {
        return flexibleAdapter.getItem(position);
    }

    @Override
    public void setUpSubscribers() {
        multiStringValues.getObservable().subscribe(new ResponseObserver<Map<String, String>>(this) {
            @Override
            public void onNext(Map<String, String> stringStringMap) {
                super.onNext(stringStringMap);
                String busFor = stringStringMap.get(MULTI_STRING_BUS_FOR);
                if (!stringUtils.isEmpty(busFor)) {
                    switch (busFor) {

                    }
                }
            }
        });
    }

    @Override
    public void shareFeed(String url) {
        String message = "Hi, checkout this post on Radius " + url;
        collectionUtils.shareMessage("Feed", message, activity, true);
    }

    @Override
    public void displayFeedDataByPresenter(List<IFlexible> iFlexibles, boolean isAddedToList) {
        if (isAddedToList) {
            flexibleAdapter.onLoadMoreComplete(iFlexibles);
        } else {
            if (swipedDown) {
                swipedDown = false;
                binding.swipeRefreshLayout.setRefreshing(false);
            }
            flexibleAdapter.clear();
            collectionUtils.updateDataSet(flexibleAdapter, iFlexibles);
            binding.feedList.smoothScrollToPosition(0);
            hideFeedsShimmer();
        }
    }

    @Override
    public boolean onBackPressed() {
        if (childFragment != null) {
            removeChildFragment();
            return true;
        }
        return super.onBackPressed();
    }


    @Override
    public void removeChildFragment() {
        if (childFragment != null) {
            childFragment = collectionUtils.getActivity(activity).removeChildFragment(childFragment, this);
            setTitleAndMenu();
        }
    }

    @Override
    public void setTitleAndMenu() {
        if (childFragment != null) {
            childFragment.setTitleAndMenu();
        } else {
            super.setTitleAndMenu();
        }

    }

    @Override
    public void showFullShimmer() {
        binding.feedlistShimmer.getRoot().setVisibility(VISIBLE);
        binding.feedList.setVisibility(GONE);
    }


    @Override
    public void onLoadMore(int lastPosition, int currentPage) {
        presenter.getNextSetOfFeeds();
    }

    @Override
    public void addEndlessScrollListener() {
        flexibleAdapter.setEndlessScrollListener(this, new ProgressItem());
    }

    @Override
    public void noMoreContentToLoad() {
        flexibleAdapter.setEndlessProgressItem(null);
        flexibleAdapter.onLoadMoreComplete(null);
    }

    @Override
    public void noMoreLoad(int newItemsSize) {

    }

//    @Override
//    public void openFeedDetails(FeedViewDetails feedViewDetails, boolean shouldOpenEditText) {
////        loadChildFragment(fragmentInstanceCreator.getFeedDetailsFragment(feedViewDetails, shouldOpenEditText, editCommentViewDetails));
//    }

    @Override
    public void objectNotFound() {
        super.objectNotFound("Not found", "Feed does not exist");
    }

    @Override
    public void goBack() {
        super.goBack();
    }

    @Override
    public void showFetchingLayoutWithMessage(String message) {
        super.showFetchingLayoutWithMessage(binding.fetchErrorMessage, message);
    }

    @Override
    public void showLoadingWithMessage(String message) {
        super.showLoadingWithMessage(binding.loader, message);
    }

    @Override
    public void hideLoader() {
        super.hideLoader(binding.loader);
    }

    @Override
    public void showNetworkErrorMessage() {
        super.showNetworkErrorMessage(binding.fetchErrorMessage);
    }

    @Override
    public void showToast(String message) {
        super.showToast(message);
    }

    @Override
    public void unauthorizedQuitApp() {
        super.unauthorizedQuitApp();
    }

    @Override
    public void showUserErrorDialog(String message) {
        super.showUserErrorDialog(message);
    }

    @Override
    public void openUrl(String url) {
        collectionUtils.goToUrl(activity, url);
    }

    @Override
    public void loadChildFragment(BaseFragment childFragment) {
        this.childFragment = collectionUtils.getActivity(activity).loadChildFragment(this, childFragment, R.id.containerFrame);
    }

    @Override
    public void updateItemAtPosition(IFlexible iFlexible, int position) {
        if (flexibleAdapter != null && iFlexible != null && position > -1) {
            flexibleAdapter.updateItem(position, iFlexible, null);
        }
    }

    @Override
    public void showEmptyState() {
        hideFeedsShimmer();
        binding.swipeRefreshLayout.setVisibility(GONE);
        binding.emptyState.getRoot().setVisibility(VISIBLE);
    }


    @Override
    public void hideEmptyState() {
        binding.swipeRefreshLayout.setVisibility(VISIBLE);
        binding.emptyState.getRoot().setVisibility(GONE);
    }
}
