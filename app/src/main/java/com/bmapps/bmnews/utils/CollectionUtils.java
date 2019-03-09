package com.bmapps.bmnews.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.view.animation.AnimationUtils;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.R;
import com.bmapps.bmnews.databinding.FetchOrErrorLayoutBinding;
import com.bmapps.bmnews.databinding.LoaderLayerBinding;
import com.bmapps.bmnews.interaction.RxMultiStringValues;
import com.bmapps.bmnews.ui.MainActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.bmapps.bmnews.utils.FinalValues.MULTI_STRING_BUS_FOR;
import static com.bmapps.bmnews.utils.FinalValues.MULTI_STRING_BUS_VALUE;


public class CollectionUtils {

    @Inject
    StringUtils stringUtils;

    @Inject
    ImageUtils imageUtils;

    @Inject
    RxDialogBox rxDialogBox;

    @Inject
    public CollectionUtils(NewsApplication application) {
        application.getApplicationComponent().inject(this);
    }

    public boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public void goToUrl(Activity activity, String url) {
        try {
            if (!stringUtils.isEmpty(url)) {
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MainActivity getActivity(Activity activity) {
        if (activity instanceof MainActivity)
            return (MainActivity) activity;
        return null;
    }

    public void showNetworkErrorMessage(FetchOrErrorLayoutBinding binding, Activity activity) {
        binding.fetchOrError.setText(activity.getResources().getString(R.string.errorInFetching));
        binding.fetchOrError.setBackgroundColor(Color.parseColor("#CA3543"));
//        binding.fetchOrError.setTextColor(ContextCompat.getColor(binding.getRoot().application, R.color.white));
        binding.getRoot().setVisibility(VISIBLE);
        new Handler().postDelayed(() -> {
            binding.getRoot().setVisibility(GONE);
        }, 1000);
    }

    public void showFetchingLayoutWithMessage(FetchOrErrorLayoutBinding binding, String text, Activity activity) {
        binding.getRoot().setVisibility(VISIBLE);
        binding.fetchOrError.setText(text);
        binding.fetchOrError.setBackgroundColor(ContextCompat.getColor(activity, R.color.black_transparent_60));
        binding.fetchOrError.setTextColor(ContextCompat.getColor(activity, R.color.white));
        new Handler().postDelayed(() -> {
            binding.getRoot().setVisibility(GONE);
        }, 500);
    }

    public void showLoaderWithMessage(LoaderLayerBinding binding, String message, Activity activity) {
        binding.getRoot().setVisibility(VISIBLE);
        binding.getRoot().startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_in));
        try {
            binding.loadingText.setVisibility(GONE);
            binding.pleaseWait.setVisibility(GONE);
            binding.company.setVisibility(VISIBLE);
            imageUtils.loadGif(binding.company);
        } catch (Exception e) {
            binding.loadingText.setText(message);
            binding.loadingText.setVisibility(VISIBLE);
            binding.pleaseWait.setVisibility(VISIBLE);
            binding.company.setVisibility(GONE);
        }
    }

    public void hideLoader(LoaderLayerBinding binding, Activity activity) {
        if (binding.getRoot().getVisibility() == VISIBLE) {
            binding.getRoot().startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_out));
            binding.getRoot().setVisibility(GONE);
        }

    }

    public void shareMessage(String subject, String message, Context context, boolean append) {
        if (!stringUtils.isEmpty(message)) {
            if (append)
                message = message + "\n\n shared via Radius \n https://goo.gl/4vOYNV";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            context.startActivity(Intent.createChooser(sharingIntent, "Share using.."));
        }
    }


    public void populateRxMultiStringBusAndSendData(RxMultiStringValues multiStringValues, String rxStringFor, String rxStringValue) {
        if (multiStringValues != null) {
            Map<String, String> map = new HashMap<>();
            map.put(MULTI_STRING_BUS_FOR, rxStringFor);
            map.put(MULTI_STRING_BUS_VALUE, rxStringValue);
            multiStringValues.sendData(map);
        }
    }

    public void populateRxMultiStringObjectBusAndSendData(RxMultiStringValues multiStringValues, String rxStringFor, Object rxStringValue) {
        if (multiStringValues != null) {
            Map<String, Object> map = new HashMap<>();
            map.put(MULTI_STRING_BUS_FOR, rxStringFor);
            map.put(MULTI_STRING_BUS_VALUE, rxStringValue);
            multiStringValues.sendGenericData(map);
        }
    }


    public FlexibleAdapter setUpRecyclerView(RecyclerView recyclerView, List<IFlexible> iFlexibles, boolean showHeader) {
        if (iFlexibles == null)
            iFlexibles = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false));
        final FlexibleAdapter adapter = new FlexibleAdapter<>(iFlexibles);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        if (showHeader) {
            adapter.setDisplayHeadersAtStartUp(true);
            adapter.setStickyHeaders(true);
        }

        return adapter;
    }

    public void updateDataSet(FlexibleAdapter adapter, List<IFlexible> flexibles) {
        try {
            if (flexibles != null)
                adapter.updateDataSet(flexibles);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
