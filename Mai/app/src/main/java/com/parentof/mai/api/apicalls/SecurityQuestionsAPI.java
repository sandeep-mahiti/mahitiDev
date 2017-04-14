package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.SecurityQuestionsCallBack;
import com.parentof.mai.model.verifyuser.failmodel.anonymus.VerifyAPIAnonymusRespModel;
import com.parentof.mai.model.verifyuser.failmodel.locked.VerifyAPILockedRespModel;
import com.parentof.mai.model.verifyuser.succesModel.VerifyAPISuccessRespModel;
import com.parentof.mai.model.verifyuser.succesModel.nochild.VerifyAPINoChildRespModel;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ResponseConstants;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandeep HR on 05/01/17.
 */

public class SecurityQuestionsAPI  {

    private static final String TAG = " SecurityQstnsAPI ";

    public static void getQuestions(final Context context, final String user_id, final SecurityQuestionsCallBack callBackResponse ) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);

        MyProgress.show(context, "", "");
        String url =  Feeds.URL + Feeds.PARENT+ "/"+user_id +Feeds.VERIFY;
        Logger.logD(TAG, " url " + url);
        final Map<String, String> params = new HashMap<>();
        JSONObject jsonObject=new JSONObject();
        try {
            /*jsonObject.put("app_id", app_id);
            jsonObject.put("token", token);
            jsonObject.put("user_id", user_id);*/
        }catch (Exception e){
            Logger.logE(TAG, "json exe :" , e);
        }
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);

                        MyProgress.CancelDialog();
                        Logger.logD(TAG," URL response : "+response.toString());
                        try {
                            if (response.getString("status").equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)){
                                //existing user
                                JSONObject dataObj=new JSONObject(response.getString("data"));
                                if(dataObj.has("questions")){
                                    VerifyAPISuccessRespModel securityQuestionsModel = gson.fromJson(response.toString(),VerifyAPISuccessRespModel.class);
                                    callBackResponse.secQuestions(securityQuestionsModel);
                                }
                                else if(dataObj.has("haveChild") && !dataObj.getBoolean("haveChild"))
                                {
                                    VerifyAPINoChildRespModel verifyAPINoChildRespModel= gson.fromJson(response.toString(),VerifyAPINoChildRespModel.class);
                                    callBackResponse.existNoChild(verifyAPINoChildRespModel);
                                }

                            }else{
                                //newuser ,
                                JSONObject innerJson;
                                if(response.has("message")) {
                                     innerJson = new JSONObject(response.getString("message"));
                                    if (innerJson.has("anonymous") && innerJson.getBoolean("anonymous")) {
                                        VerifyAPIAnonymusRespModel anonymusRespModel = gson.fromJson(response.toString(), VerifyAPIAnonymusRespModel.class);
                                        callBackResponse.newUser(anonymusRespModel);

                                    }else if (innerJson.has("isLocked") && innerJson.getBoolean("isLocked")) {
                                            VerifyAPILockedRespModel lockedUserModel = gson.fromJson(response.toString(), VerifyAPILockedRespModel.class);
                                            // callBackResponse.newUser();
                                            callBackResponse.blockedUser(lockedUserModel);

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MyProgress.CancelDialog();
                error.printStackTrace();
                ToastUtils.displayToast(Constants.SERVER_ERROR, context);
                // HTTPErrorCodes.showMessages(HTTPErrorCodes.checkRequestCode(error.networkResponse.statusCode), context, "My Request Module");
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
