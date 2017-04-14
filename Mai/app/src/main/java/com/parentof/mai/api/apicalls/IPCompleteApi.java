package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.IPCompRespCallback;
import com.parentof.mai.model.ImprovementPlanModel.ImprovementPlanModel;
import com.parentof.mai.model.ipcctrespmodel.IPCCTRespModel;
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
public class IPCompleteApi {
    private static final String TAG = " IPCompleteApi ";

    public static void callCompleteApi(final Context context, final String user_id, final String childId,  final String iId, String ipStatusStr, final IPCompRespCallback ipCompRespCallback) {

        String url = Feeds.URL + Feeds.DP + "/" + user_id + "/" + childId + Feeds.INTERVENTION +"/"+ iId + ipStatusStr;
     //   String url = "http://dev-arcapi.askm.ai/v0/dp/54/58afcd3d2caef4447298ab77/5885e60c4badf74668e9b37f/intervention/58a40dbccdbf3ca607abaec2/complete";
        MyProgress.show(context, "", "");
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Logger.logD(TAG, "IP CompleteApi --" + response.toString());
                        try {
                            Object item = response.get("data"); // `instanceof` tells us whether the object can be cast to a specific type
                            if (item instanceof JSONObject) {
                                // it's an array
                                //JSONArray urlArray = (JSONArray) item;
                                String data = gson.toJson(response);
                                MyProgress.CancelDialog();
                                ImprovementPlanModel interventaionModel = gson.fromJson(response.toString(), ImprovementPlanModel.class);
                                Logger.logD(TAG, "IP CompleteApi --" + response.toString());
                                ipCompRespCallback.ipCompResp(interventaionModel);//interventaionModel);


                                // do all kinds of JSONArray'ish things with urlArray
                            } else  if (item instanceof String){
                                // if you know it's either an array or an object, then it's an object
                                String strResp = (String) item; // do objecty stuff with urlObject
                                ToastUtils.displayToast( strResp, context);
                                ImprovementPlanModel interventaionModel =new ImprovementPlanModel();
                                Logger.logD(TAG, "IP CompleteApi --" + response.toString());
                                interventaionModel.setStatus(  response.get("status").toString());
                                ipCompRespCallback.ipCompResp(interventaionModel);
                                MyProgress.CancelDialog();
                                Logger.logD(TAG,strResp );
                            }

                        }catch(Exception e){
                            Logger.logE(TAG, " on ImprovementPlanApi class", e);
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
