package com.parentof.mai.views.activity.growthCharts;

import android.app.AlertDialog;

/**
 * Created by mahiti on 31/1/17.
 */
public interface UpdateResponse {
    void updateChildData(String age, String height, String mode, int id, AlertDialog alertDialog);
}
