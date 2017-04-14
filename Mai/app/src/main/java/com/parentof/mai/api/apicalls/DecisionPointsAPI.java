package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;


import com.parentof.mai.activityinterfaces.DecisionPointsCallback;
import com.parentof.mai.model.decisionpointsmodel.DPRespModel;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ToastUtils;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandeep HR on 17/02/17.
 */

public class DecisionPointsAPI {

    private static final String TAG = " DecPntsAPI ";

    //http://dev-arcapi.askm.ai/v0/dp/41/58a548d9c0bcd9d11df8055b
    public static void getDPs(final Context context, final String user_id, final String childId, final DecisionPointsCallback callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        String url =  Feeds.URL + Feeds.DP+"/"+user_id+"/"+childId;
        //MyProgress.show(context,""," Loading, please wait! ");
        Logger.logD(TAG , " url : " +url);
        JSONObject jsonObject=new JSONObject();


        try {

  /*          jsonObject.put("childId", childDetails.get("childId"));
            jsonObject.put("likes", childDetails.get("likes"));
            jsonObject.put("dislikes", childDetails.get("dislikes"));
            jsonObject.put("hobbies", childDetails.get("hobbies"));
            jsonObject.put("skills", childDetails.get("skills\""));
            jsonObject.put("allergies", childDetails.get("allergies"));*/

            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());

        }catch (Exception e){
            Logger.logE(TAG, "json exe : " , e);
        }
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        Logger.logD(TAG, " URL response : " + response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase("error")){
                                ToastUtils.displayToast(response.getString("message"), context);
                            }else {
                                DPRespModel childrenRespModel = gson.fromJson(response.toString(), DPRespModel.class);
                                //MyProgress.CancelDialog();
                                Logger.logD(TAG, " URL response : " + response.toString());
                                callBackResponse.getAllDPsForChild(childrenRespModel);
                            }
                        }catch (Exception e){
                            Logger.logE(TAG, "getDPs ",e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //MyProgress.CancelDialog();
                error.printStackTrace();
                //ToastUtils.displayToast(Constants.SERVER_ERROR, context);
                // HTTPErrorCodes.showMessages(HTTPErrorCodes.checkRequestCode(error.networkResponse.statusCode), context, "My Request Module");
            }
        }) {
            /*@Override
            protected Map<String, String> getParams() {
                //JSONObject params= new JSONObject();
                params.put("app_id", app_id);
                params.put("token", token);
                params.put("user_id", user_id);
                params.put("email", email);
                return params;
            }*/

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
