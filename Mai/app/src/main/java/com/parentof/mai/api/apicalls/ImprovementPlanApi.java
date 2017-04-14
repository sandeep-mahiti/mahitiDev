package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.ImprovementPlanCallBack;
import com.parentof.mai.model.ImprovementPlanModel.ImprovementPlanModel;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahiti on 28/2/17.
 */
public class ImprovementPlanApi {
    private static final String TAG = " ImprovementPlanApi ";

    public static void getImprovementDetails(final Context context, final String user_id, final String childId, final String dpId, final String skillId, final ImprovementPlanCallBack improvementPlanCallBack) {

        String url = Feeds.URL + Feeds.DP + "/" + user_id + "/" + childId + "/" + dpId + Feeds.SKILL + "/" + skillId + "/" + Feeds.CT;

     //   String url = "http://dev-arcapi.askm.ai/v0/dp/54/58afcd3d2caef4447298ab77/5885e60c4badf74668e9b37f/skill/58a40dbccdbf3ca607abaec2/ct";
        MyProgress.show(context, "", "");
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        try {
                            if("error".equalsIgnoreCase(response.getString("status"))){
                                MyProgress.CancelDialog();
                                if(response.has("message"))
                                    ToastUtils.displayToast(response.getString("message"), context);
                                else if(response.has("data"))
                                    ToastUtils.displayToast(response.getString("data"), context);
                                return;
                            }

                            Object item = response.get("data"); // `instanceof` tells us whether the object can be cast to a specific type
                            if (item instanceof JSONObject) {
                                // it's an array
                                //JSONArray urlArray = (JSONArray) item;
                                String data = gson.toJson(response);
                                MyProgress.CancelDialog();
                                ImprovementPlanModel improvementPlanModel = gson.fromJson(response.toString(), ImprovementPlanModel.class);
                                Logger.logD(Constants.PROJECT, "ImprovementPlanApi ---  " + response.toString());

                                improvementPlanCallBack.getImprovementPlanDetails(improvementPlanModel);
                                // do all kinds of JSONArray'ish things with urlArray
                            } else  if (item instanceof String){
                                // if you know it's either an array or an object, then it's an object
                                String strResp = (String) item; // do objecty stuff with urlObject
                                ToastUtils.displayToast( strResp, context);
                                MyProgress.CancelDialog();
                                Logger.logD(TAG,strResp );
                                return;
                            }

                        }catch(Exception e){
                            MyProgress.CancelDialog();
                            Logger.logE(TAG, " on ImprovementPlanApi class", e);
                            ToastUtils.displayToast( "Something Wrong, try after sometime", context);
                        }

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
