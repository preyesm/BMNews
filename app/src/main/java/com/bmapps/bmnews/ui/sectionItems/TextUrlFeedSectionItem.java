package com.bmapps.bmnews.ui.sectionItems;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.R;
import com.bmapps.bmnews.databinding.ItemNewsFeedBinding;
import com.bmapps.bmnews.interaction.RxMultiStringValues;
import com.bmapps.bmnews.utils.CollectionUtils;
import com.bmapps.bmnews.utils.ImageUtils;
import com.bmapps.bmnews.utils.StringUtils;
import com.bmapps.bmnews.viewDetails.FeedViewDetails;

import java.util.List;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

import static com.bmapps.bmnews.utils.FinalValues.OPEN_DETAIL_PAGE;

public class TextUrlFeedSectionItem extends AbstractFlexibleItem<TextUrlFeedSectionItem.ViewHolder> {

    @Inject
    ImageUtils imageUtils;

    @Inject
    StringUtils stringUtils;

    @Inject
    CollectionUtils collectionUtils;

    private FeedViewDetails feedViewDetails;

    private RxMultiStringValues rxMultiStringValues;

    public TextUrlFeedSectionItem(NewsApplication newsApplication, FeedViewDetails feedViewDetails, RxMultiStringValues rxMultiStringValues) {

        newsApplication.getApplicationComponent().inject(this);
        this.feedViewDetails = feedViewDetails;
        this.rxMultiStringValues = rxMultiStringValues;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_news_feed;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ViewHolder(DataBindingUtil
                .inflate(LayoutInflater.from(view.getContext()),
                        getLayoutRes(),
                        (ViewGroup) view,
                        false),
                adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> flexibleAdapter, ViewHolder holder, int i, List<Object> list) {
        holder.mBinding.setFeed(feedViewDetails);
        holder.mBinding.setImageUtils(imageUtils);
        holder.mBinding.setStringUtils(stringUtils);
        holder.mBinding.executePendingBindings();

        imageUtils.setNameAvatar(feedViewDetails.source.charAt(0), holder.mBinding.newsHeaderLayout.imgNewsSource, R.color.colorPrimary);

        // set url snippet in view
        if (feedViewDetails.urlSnippet != null) {
            imageUtils.setImageUsingGlide(feedViewDetails.urlSnippet.getImageUrl(), holder.mBinding.urlSnippet.image);
            holder.mBinding.urlSnippet.getRoot().setOnClickListener(v -> collectionUtils.populateRxMultiStringBusAndSendData(rxMultiStringValues, OPEN_DETAIL_PAGE, feedViewDetails.urlSnippet.getUrl()));
        }
    }

    class ViewHolder extends FlexibleViewHolder {
        ItemNewsFeedBinding mBinding;

        ViewHolder(ItemNewsFeedBinding binding, FlexibleAdapter adapter) {
            super(binding.getRoot(), adapter);
            mBinding = binding;
        }
    }


}
