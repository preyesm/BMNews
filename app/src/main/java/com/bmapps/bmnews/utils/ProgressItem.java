package com.bmapps.bmnews.utils;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bmapps.bmnews.R;
import com.bumptech.glide.Glide;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

public class ProgressItem extends AbstractFlexibleItem<ProgressItem.ProgressViewHolder> {

    @Override
    public boolean equals(Object o) {
        return this == o;//The default implementation
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_progress;
    }

    @Override
    public ProgressViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new ProgressViewHolder(LayoutInflater.from(view.getContext()).inflate(getLayoutRes(), (ViewGroup) view, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, ProgressViewHolder holder, int position, List payloads) {
        //nothing to bind
    }

    class ProgressViewHolder extends FlexibleViewHolder {

        ImageView progressBar;

        ProgressViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            progressBar = view.findViewById(R.id.progress_bar);
            Glide.with(progressBar.getContext())
                    .load(R.raw.loading)
                    .into(progressBar);
        }
    }

}