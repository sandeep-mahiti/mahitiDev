package com.parentof.mai.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;

import com.parentof.mai.R;


public class MyProgress extends Dialog {
    static MyProgress dialog;

    public static MyProgress show(Context context, CharSequence title, CharSequence message) {
        return show(context, title, message, true);
    }

    public static MyProgress show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static MyProgress show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static MyProgress show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
        dialog = new MyProgress(context);
        dialog.setTitle(title);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        /* The next line will add the ProgressBar to the dialog. */
        dialog.addContentView(new ProgressBar(context), new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        dialog.show();

        return dialog;
    }

    public static boolean isShowingProgress() {
        if (dialog == null) {
            return false;
        }
        return dialog.isShowing();
    }

    public static void CancelDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (IllegalArgumentException e) {
            Logger.logE(Constants.PROJECT,"My progress--",e);
        } catch (Exception e) {
            Logger.logE(Constants.PROJECT, "My progress--", e);
        }
    }

    public MyProgress(Context context) {
        super(context, R.style.NewDialog);
    }
}
