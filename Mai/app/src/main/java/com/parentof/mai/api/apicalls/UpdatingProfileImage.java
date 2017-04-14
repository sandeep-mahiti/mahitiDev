package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahiti on 6/4/17.
 */
public class UpdatingProfileImage {
    private static final String TAG = "UpdatingProfileImage ";

    public static void apiToUpdateProfile(final Context context, final String profileImage, final String userId, final String childId) {
        String url = Feeds.URL + Feeds.PARENT + "/" + userId + Feeds.CHILD + "/" + childId + Feeds.PROFILE_PIC;
        //dev-arcapi.askm.ai/v0/parent/97/child/58dca780087701c93cc5a573/profilePic
        Logger.logD(TAG, " url : " + url);
        MyProgress.show(context, "", "");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profilePic", profileImage);
            Logger.logD(TAG, " ----Params ----- : " + jsonObject.toString());
        } catch (JSONException e) {
            Logger.logE(TAG, "json exe :", e);
        }
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        Logger.logD(TAG, " URL response : " + data);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyProgress.CancelDialog();
                error.printStackTrace();
                ToastUtils.displayToast(Constants.SERVER_ERROR, context);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeaderPart = new HashMap<>();
                mHeaderPart.put("Content-Type", "application/json");
                return mHeaderPart;
            }
        };
        Volley.newRequestQueue(context).add(getReq);
    }

}
