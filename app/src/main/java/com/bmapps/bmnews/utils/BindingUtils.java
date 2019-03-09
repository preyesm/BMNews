package com.bmapps.bmnews.utils;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import com.bmapps.bmnews.NewsApplication;
import com.bmapps.bmnews.R;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;

import static android.graphics.Typeface.NORMAL;

public class BindingUtils {
    @BindingAdapter({"app:textString", "app:isShowMore"})
    public static void setTextString(TextView textView, String originalString, boolean canSetLogicForSeeMore) {
        boolean showSeeMore = false;
        if (!org.apache.commons.lang3.StringUtils.isEmpty(originalString)) {
            // reduce the character limit at the start of display and perform linkify and see more logic
            if (canSetLogicForSeeMore && originalString.length() > 100) {
                showSeeMore = true;
                originalString = originalString.substring(0, 100);
            }

            textView.setText(originalString);
            Linkify.addLinks(textView, Linkify.WEB_URLS);
            SpannableStringBuilder ssb = new SpannableStringBuilder(textView.getText());
            staticRemoveUnderlinesAndColorifyUrls(textView, ssb, true);
            if (showSeeMore) {
                String seeMore = "See more...";
                ssb.append(" ");
                ssb.append(seeMore);
                ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(textView.getContext(), R.color.blue)), originalString.length(), originalString.length() + seeMore.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new CustomTypefaceSpan("", Typeface.create(ResourcesCompat.getFont(textView.getContext(), R.font.rubik_medium), NORMAL)), originalString.length(), originalString.length() + seeMore.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(ssb);
            }
        }
    }

    private static void staticRemoveUnderlinesAndColorifyUrls(TextView textView, Spannable s, boolean addClickableSpan) {
        URLSpan spans[] = textView.getUrls();
        for (URLSpan span : spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            if (start > -1 && end > -1) {
                s.setSpan(new URLSpan(span.getURL()) {
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setColor(ContextCompat.getColor(textView.getContext(), R.color.blue));
                    }

                    @Override
                    public void onClick(View widget) {
                        if (addClickableSpan) {
                            NewsApplication.getInstance().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(span.getURL())));
                        }

                    }
                }, start, end, 0);
            }

        }

        textView.setText(s);
    }


}
