package com.bmapps.bmnews.utils;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class InstantAutoCompleteView extends AppCompatAutoCompleteTextView {

    public InstantAutoCompleteView(Context context) {
        super(context);
        setKeyListener(null);
    }

    public InstantAutoCompleteView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        setKeyListener(null);
    }

    public InstantAutoCompleteView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        setKeyListener(null);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && getFilter() != null) {
            performFiltering("", 0);
        }
    }

}