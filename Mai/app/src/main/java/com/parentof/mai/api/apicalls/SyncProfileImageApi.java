package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.CustomMultipartRequest;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahiti on 5/4/17.
 */
public class SyncProfileImageApi {
    private static final String TAG = " SyncProfileImageApi ";

    public static void syncProfileImg(final Context context, final File profileImage, final String profileId, final String typeOfProfile, final String folder) {
        String url = Feeds.SEND_PROFILE_PIC_URL;
        Logger.logD(TAG, " url : " + url);
        MyProgress.show(context, "", "");
        Map<String, String> mHeaderPart = new HashMap<>();
        Logger.logD(Constants.PROJECT, "file-->" + profileImage);

        // application/json; charset=utf-8
        mHeaderPart.put("Content-Type", "form-data");
        // mHeaderPart.put("X-Authorization", "9cb0a1f07db7b5564e7aabd29d4dd50aeaaef4e0");

        //File part
        Map<String, File> mFilePartData = new HashMap<>();
        mFilePartData.put("file", profileImage);
        Logger.logD(Constants.PROJECT, "mFilePartData-->" + mFilePartData);


        //String part
        Map<String, String> mStringPart = new HashMap<>();
        mStringPart.put("user", profileId);
        mStringPart.put("type", typeOfProfile);
        mStringPart.put("folder", folder);
        Logger.logD(Constants.PROJECT, "mStringPart-->" + mStringPart);

        MyProgress.show(context, "", "");
        CustomMultipartRequest mCustomRequest = new CustomMultipartRequest(Request.Method.POST, context, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logger.logD("callServer", "syncProfileImg Response :- " + jsonObject);
                Logger.logD("callServer", "Response with string :- " + jsonObject.toString());
                MyProgress.CancelDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Logger.logD("callServer", " syncProfileImg Response :- " + volleyError);
                MyProgress.CancelDialog();

            }
        },
                mFilePartData, mStringPart, mHeaderPart);

        mCustomRequest.setRetryPolicy(new DefaultRetryPolicy(6000, 1, 1));
        Volley.newRequestQueue(context).add(mCustomRequest);


    }


}
