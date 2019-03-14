package com.bmapps.bmnews.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bmapps.bmnews.R;
import com.bmapps.bmnews.databinding.FragmentDetailNewsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import static com.bmapps.bmnews.utils.FinalValues.OPEN_URL;

public class DetailNewsFragment extends BaseFragment {

    FragmentDetailNewsBinding binding;

    public static BaseFragment getInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(OPEN_URL, url);
        DetailNewsFragment detailNewsFragment = new DetailNewsFragment();
        detailNewsFragment.setArguments(bundle);
        return detailNewsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_news, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
