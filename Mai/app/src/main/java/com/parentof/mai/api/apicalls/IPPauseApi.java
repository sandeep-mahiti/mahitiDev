package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.IPPauseApiRespCallback;
import com.parentof.mai.model.playpauserespmodel.PlayPauseModel;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahiti on 3/3/17.
 */
public class IPPauseApi {
    private static final String TAG = " PauseApi";

    public static void callPauseApi(final Context context, final String user_id, final String childId, final String iId, final String playPause, final IPPauseApiRespCallback pausePlayResp) {

        String url = Feeds.URL + Feeds.DP + "/" + user_id + "/" + childId  + Feeds.INTERVENTION + "/" + iId + "/" + playPause;
        //  String url = "http://dev-arcapi.askm.ai/v0/dp/54/58afcd3d2caef4447298ab77/5885e60c4badf74668e9b37f/intervention/58a41163cdbf3ca607abaec4";
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
                        PlayPauseModel ppRespModel = gson.fromJson(response.toString(), PlayPauseModel.class);
                        Logger.logD(Constants.PROJECT, "callInterventionApi  --" + response);
                        pausePlayResp.pausePlayAPiResp(ppRespModel);

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

