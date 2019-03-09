package com.bmapps.bmnews.ui.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bmapps.bmnews.databinding.FetchOrErrorLayoutBinding;
import com.bmapps.bmnews.databinding.LoaderLayerBinding;
import com.bmapps.bmnews.utils.CollectionUtils;
import com.bmapps.bmnews.utils.RxDialogBox;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public abstract class BaseFragment extends Fragment {


    @Inject
    RxDialogBox rxDialogBox;

    @Inject
    CollectionUtils collectionUtils;


    protected String titleToDisplay;

    protected Activity activity = null;
    public FragmentManager childFragmentManager;


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        this.activity = (Activity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        childFragmentManager = getChildFragmentManager();
    }

    public void setTitleAndMenu() {
        activity.invalidateOptionsMenu();
    }

    public void showLoadingWithMessage(LoaderLayerBinding binding, String message) {
        collectionUtils.showLoaderWithMessage(binding, message, activity);
    }

    public void goBack() {
        activity.onBackPressed();
    }

    public void showFetchingLayoutWithMessage(FetchOrErrorLayoutBinding binding, String message) {
        collectionUtils.showFetchingLayoutWithMessage(binding, message, activity);
    }

    public void hideLoader(LoaderLayerBinding binding) {
        collectionUtils.hideLoader(binding, activity);
    }

    public void showNetworkErrorMessage(FetchOrErrorLayoutBinding binding) {
        collectionUtils.showNetworkErrorMessage(binding, activity);
    }

    public void showUserErrorDialog(String message) {
        rxDialogBox.dialogWithNoAction("Oops!", message, activity);
    }

    public void unauthorizedQuitApp() {
     //   collectionUtils.getActivity(activity).loadFadeInFadeOutFragment(fragmentInstanceCreator.getWelcomeScreen());
    }

    public void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    public void objectNotFound(String title, String message) {
        activity.onBackPressed();
        rxDialogBox.dialogWithNoAction(title, message, activity);
    }


    /**
     * @return boolean
     * return true of the event is consumed by the fragment. Else send false.
     */
    public boolean onBackPressed() {
        return false;
    }
}