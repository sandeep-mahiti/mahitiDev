package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.DayLoggedCallBack;
import com.parentof.mai.model.dayLoggedModel.DayLoggedModel;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahiti on 28/2/17.
 */
public class DayLoggedApi {

    private static final String TAG = " DayLoggedApi ";

    public static void callDayLoggedApi(final Context context, final String user_id, final String childId, final String dpId, final String iId, String day, boolean userAns, final DayLoggedCallBack dayLoggedCallBack) {

        String url = Feeds.URL + Feeds.DP + "/" + user_id + "/" + childId + "/" + dpId + Feeds.INTERVENTION +"/"+ iId + Feeds.DAY +"/"+ day + "/"+userAns;

      //  String url = "http://dev-arcapi.askm.ai/v0/dp/54/58afcd3d2caef4447298ab77/5885e60c4badf74668e9b37f/intervention/58a40dbccdbf3ca607abaec2/day/day1";
        MyProgress.show(context, "", "");
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        MyProgress.CancelDialog();
                        DayLoggedModel dayLoggedModel = gson.fromJson(response.toString(), DayLoggedModel.class);
                        Logger.logD(Constants.PROJECT, " callDayLoggedApi  --" + response.toString());
                        dayLoggedCallBack.dayLoggedCallBack(dayLoggedModel);

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
