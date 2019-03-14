package com.bmapps.bmnews.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.bmapps.bmnews.R;
import com.bmapps.bmnews.databinding.DialogGenericTextOkCancelBinding;

import androidx.databinding.DataBindingUtil;

import static android.view.View.GONE;

public class RxDialogBox {

    public void dialogWithNoAction(String title, String text, Activity activity) {
        titleMessageTwoOptions(title,text,null,activity).show();
    }

    private AlertDialog titleMessageTwoOptions(String heading, String message,
                                               View.OnClickListener onOkClickListener,
                                               final Activity context) {
        return titleMessageTwoOptions(heading, message, null, null, onOkClickListener, context);
    }

    private AlertDialog titleMessageTwoOptions(String heading, String message,
                                               String positiveBtnText, String negativeBtnText,
                                               View.OnClickListener onOkClickListener,
                                               final Activity context) {
        final AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DialogGenericTextOkCancelBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_generic_text_ok_cancel, null, false);
        builder.setView(binding.getRoot());
        dialog = builder.create();

        binding.titleText.setText(heading);
        binding.dialogText.setText(message);

        if (onOkClickListener == null) {
            binding.negativeButton.setVisibility(GONE);
            binding.verticalSeperator.setVisibility(GONE);
            binding.positiveButton.setText("Ok");
        }

        if (positiveBtnText != null && !TextUtils.isEmpty(positiveBtnText)) {
            binding.positiveButton.setText(positiveBtnText);
        }

        if (negativeBtnText != null && !TextUtils.isEmpty(negativeBtnText)) {
            binding.negativeButton.setText(negativeBtnText);
        }

        binding.negativeButton.setOnClickListener(v -> dialog.dismiss());
        binding.positiveButton.setOnClickListener(v -> {
            if (onOkClickListener != null) {
                onOkClickListener.onClick(v);
                dialog.dismiss();
            } else {
                dialog.dismiss();
            }
        });
        return dialog;
    }

}
