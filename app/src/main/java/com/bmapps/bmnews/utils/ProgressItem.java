package com.bmapps.bmnews.utils;


import android.animation.Animator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bmapps.bmnews.R;

import java.util.List;

import androidx.annotation.NonNull;
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

    public static class ProgressViewHolder extends FlexibleViewHolder {

        public ImageView progressBar;

        public ProgressViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            progressBar = view.findViewById(R.id.progress_bar);
//            Glide.with(progressBar.getContext())
//                    .load(R.raw.loading)
//                    .into(progressBar);
        }

        @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
//            AnimatorHelper.scaleAnimator(animators, itemView, 0f);
        }
    }

}