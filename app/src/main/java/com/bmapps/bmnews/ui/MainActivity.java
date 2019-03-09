package com.bmapps.bmnews.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.bmapps.bmnews.R;
import com.bmapps.bmnews.databinding.ActivityMainBinding;
import com.bmapps.bmnews.ui.fragments.BaseFragment;
import com.bmapps.bmnews.ui.fragments.FeedListFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;
import static com.bmapps.bmnews.ui.MainActivity.FragmentLoadingAnim.FADE_EFFECT;

public class MainActivity extends AppCompatActivity {

    BaseFragment currentFragment;

    ActivityMainBinding binding;

    enum FragmentLoadingAnim {
        FADE_EFFECT("fade effect"),
        SLIDE_LEFT_RIGHT("slide left right");
        String type;

        FragmentLoadingAnim(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        loadFadeInFadeOutFragment(new FeedListFragment());
    }

    public void setActionAsBack() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void loadFadeInFadeOutFragment(BaseFragment baseFragment) {
        loadFragment(baseFragment, true, FADE_EFFECT);
    }


    public void loadFragment(BaseFragment fragment, boolean clearBackStack, FragmentLoadingAnim animationEffect) {

        ActionBar actionBar = getSupportActionBar();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (animationEffect) {
            case FADE_EFFECT:
                ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                break;
            case SLIDE_LEFT_RIGHT:
                ft.setCustomAnimations(R.anim.enter_slide_right, R.anim.exit_slide_left);
                break;
        }
        try {
            binding.toolbarTitleText.setText("");
            if (clearBackStack) {
                fm.popBackStack(0, POP_BACK_STACK_INCLUSIVE);
                ft.replace(R.id.fragmentContainer, fragment, null);
                System.gc();
            } else {
                if (actionBar != null) {
                    setActionAsBack();
                }
                ft.add(R.id.fragmentContainer, fragment, null)
                        .addToBackStack(fragment.getClass().getName());
                if (currentFragment != null) {
                    currentFragment.setHasOptionsMenu(false);
                }
            }

            if (!this.isFinishing() && !this.isDestroyed()) {
                loadCurrentFragment(ft, fragment);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (NoSuchMethodError error) {
            Toast.makeText(this, "Please update your Android OS", Toast.LENGTH_SHORT).show();
            this.finish();
            System.exit(0);
        }
    }

    private void loadCurrentFragment(FragmentTransaction ft, BaseFragment fragment) {
        ft.commitAllowingStateLoss();
        currentFragment = fragment;

    }

    public BaseFragment loadChildFragment(BaseFragment parentFragment, BaseFragment childFragment, int id) {
        parentFragment.setHasOptionsMenu(false);
        FragmentManager fm = parentFragment.getChildFragmentManager();
        try {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.enter_slide_right, R.anim.exit_slide_left);
            ft.add(id, childFragment, null);
            ft.addToBackStack(childFragment.getClass().getName());
            ft.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return childFragment;
    }

    public BaseFragment removeChildFragment(BaseFragment childFragment, BaseFragment parentFragment) {
        if (childFragment != null) {
            FragmentManager fm = parentFragment.getChildFragmentManager();
            fm.popBackStackImmediate();
        }
        return null;
    }

}
