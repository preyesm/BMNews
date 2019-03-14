package com.bmapps.bmnews.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import static com.bmapps.bmnews.NewsApplication.getInstance;

public class ImageUtils {
    @Inject
    public ImageUtils(NewsApplication application) {

    }

    public void loadGif(ImageView imageView) {
        if (imageView != null && imageView.getContext() != null) {
            Glide.with(imageView.getContext())
                    .load(R.raw.loading)
                    .into(imageView);
        }
    }

    public void setImageUsingGlide(String url, ImageView imageView) {
        System.out.println("url" + url);
//        final ProgressBar progressBar = new ProgressBar(getContext());

        if (imageView != null && imageView.getContext() != null) {

            Glide.with(getInstance())
                    .asBitmap()
                    .load(url)
                    .apply(new RequestOptions().
                            diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .dontTransform()
                            .placeholder(imageView.getDrawable())
                    )
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                            progressBar.setVisibility(GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//                            progressBar.setVisibility(GONE);
                            return false;

                        }
                    }).into(imageView);
        }
    }

    public void setNameAvatar(char name, final ImageView imageView, int color) {
        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .width(180)  // width in px
                .height(180) // height in px
                .fontSize(50)
                .bold()
                .endConfig()
                .rect();
        TextDrawable textDrawable = builder
                .build(String.valueOf(name), ContextCompat.getColor(imageView.getContext(), color));
        System.out.println("image url --> setting initials");
        imageView.setImageDrawable(textDrawable);
    }

}
