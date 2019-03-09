package com.bmapps.bmnews.utils;

import android.widget.ImageView;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.R;

import javax.inject.Inject;

public class ImageUtils {
    @Inject
    public ImageUtils(NewsApplication application) {

    }

    public void loadGif(ImageView imageView) {
        if (imageView != null && imageView.getContext() != null) {
//            Glide.with(imageView.getContext())
//                    .load(R.raw.radius_logo)
//                    .into(imageView);
        }
    }
}
