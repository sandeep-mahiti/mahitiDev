package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.AllActiveInterventionCallBack;
import com.parentof.mai.model.allActivatedInterventionModel.AllActiveInterventionModel;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahiti on 22/3/17.
 */
public class AllActiveApiCall {
    private static final String TAG = "ALLACtiveCall";

    public static void allActiveInterventionApi(Context context, final String userId, final String childId, final AllActiveInterventionCallBack allActiveInterventionCallBack) {
        String url = Feeds.URL + Feeds.DP + "/" + userId + "/" + childId + "/" + Feeds.ALL_ACTIVE_INTERVENTION;
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonRew = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                String data = gson.toJson(response);
                AllActiveInterventionModel allActInt = gson.fromJson(response.toString(), AllActiveInterventionModel.class);
                Logger.logD(TAG, " URL response : " + response.toString());
                allActiveInterventionCallBack.responseAllActiveIntervention(allActInt);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeaderPart = new HashMap<>();
                mHeaderPart.put("Content-Type", "application/json");
                return mHeaderPart;
            }
        };
        Volley.newRequestQueue(context).add(jsonRew);
    }
}
