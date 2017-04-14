package com.parentof.mai.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahiti on 19/01/17.
 */

public class PermissionClass {


    public static boolean checkPermission(Activity activity) {

        int externalread = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int externalwrite = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int gpsACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int gpsACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int READ_PHONE_STATE = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);

        int READ_CAL = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR);
        int WRITE_CAL = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR);
        if (externalread != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (externalwrite != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (READ_CAL != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (WRITE_CAL != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        if (READ_PHONE_STATE != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {

            return true;
        }

    }

    public static void requestPermission(Activity activity) {
        List<String> list = new ArrayList();
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE))
            list.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CALENDAR))
            list.add(Manifest.permission.READ_CALENDAR);
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_CALENDAR))
            list.add(Manifest.permission.WRITE_CALENDAR);
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE))
            list.add(Manifest.permission.READ_PHONE_STATE);

        //Toast.makeText(context,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

        String[] stockArr = new String[list.size()];
        stockArr = list.toArray(stockArr);
        if (stockArr.length != 0) {
            ActivityCompat.requestPermissions(activity, stockArr, 1);
        }

    }


}
