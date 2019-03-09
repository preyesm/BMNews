package com.bmapps.bmnews.ui.sectionItems;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bmapps.bmnews.R;
import com.bmapps.bmnews.databinding.ItemNewsFeedBinding;
import com.bmapps.bmnews.interaction.RxMultiStringValues;
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

public class TextUrlFeedSectionItem extends AbstractFlexibleItem<TextUrlFeedSectionItem.ViewHolder> {

    @Inject
    ImageUtils imageUtils;

    @Inject
    StringUtils stringUtils;

    FeedViewDetails feedViewDetails;

    public TextUrlFeedSectionItem(Activity activity, FeedViewDetails feedViewDetails, RxMultiStringValues rxMultiStringValues) {
        this.feedViewDetails = feedViewDetails;
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

//        super.setAgentHeaderBinding(holder.mBinding.agentHeaderLayout, position);
//
//        super.setupClickListeners(holder.itemView, holder.mBinding.title, holder.mBinding.actionLayout, holder.mBinding.likesLocationLayout, position);

        // set url snippet in view
        if (feedViewDetails.urlSnippet != null) {
//            imageUtils.setImageUsingGlide(feedViewDetails.urlSnippet.getImage(), holder.mBinding.urlSnippet.image, R.drawable.ic_no_image_text);
//            holder.mBinding.urlSnippet.getRoot().setOnClickListener(v -> collectionUtils.populateRxMultiStringBusAndSendData(rxMultiStringValues, OPEN_URL, feedViewDetails.urlSnippet.getUrl()));
        }
//        ((GradientDrawable) holder.mBinding.urlSnippet.urlLayout.getBackground()).setColor(ContextCompat.getColor(holder.mBinding.urlSnippet.getRoot().getContext(), R.color.profile_grey));
//        ((GradientDrawable) holder.mBinding.urlSnippet.urlLayout.getBackground()).setStroke(1, ContextCompat.getColor(holder.mBinding.urlSnippet.getRoot().getContext(), R.color.referral_client_count));
    }

    class ViewHolder extends FlexibleViewHolder {
        ItemNewsFeedBinding mBinding;

        ViewHolder(ItemNewsFeedBinding binding, FlexibleAdapter adapter) {
            super(binding.getRoot(), adapter);
            mBinding = binding;
        }
    }

}
