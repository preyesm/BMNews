package com.bmapps.bmnews.ui.sectionItems;

import android.app.Activity;
import android.view.View;

import com.bmapps.bmnews.ViewDetails.FeedViewDetails;
import com.bmapps.bmnews.databinding.TextUrlFeedSectionItemBinding;
import com.bmapps.bmnews.interaction.RxMultiStringValues;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class TextUrlFeedSectionItem extends AbstractFlexibleItem<TextUrlFeedSectionItem.ViewHolder> {

    public TextUrlFeedSectionItem(Activity activity, FeedViewDetails feedViewDetails, RxMultiStringValues rxMultiStringValues) {

    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public ViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return null;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> flexibleAdapter, ViewHolder viewHolder, int i, List<Object> list) {

    }

    class ViewHolder extends FlexibleViewHolder {
        ViewHolder(TextUrlFeedSectionItemBinding binding, FlexibleAdapter adapter) {
            super(binding.getRoot(), adapter);
        }
    }

}
