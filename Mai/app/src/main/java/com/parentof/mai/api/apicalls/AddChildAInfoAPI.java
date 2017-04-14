package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.AddChildAInfoRespCallBack;
import com.parentof.mai.model.addchildmodel.AddChildAIRespModel;
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
 * Created by sandeep HR on 17/01/17.
 */

public class AddChildAInfoAPI {

    private static final String TAG = " AddChildAInfoAPI ";

   /*{
        "childId": "587daba852da0e095a0445cd",
            "likes": "Ice creasms",
            "dislikes": "Non veg",
            "hobbies": "Tv",
            "skills": "Blocks buildings",
            "allergies": "peanuts"
    }*/

    public static void addChildAdditionalInfo(final Context context, final HashMap<String, String> childDetails, final String user_id, final AddChildAInfoRespCallBack callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        String childId= childDetails.get("childId");
        String url =  Feeds.URL + Feeds.PARENT +"/"+user_id+Feeds.CHILD+"/"+childId+Feeds.CHILD_AINFO ;
        MyProgress.show(context, "", "");
        Logger.logD(TAG , " url : " +url);
        JSONObject jsonObject=new JSONObject();


        try {

                jsonObject.put("childId", childDetails.get("childId"));
                jsonObject.put("likes", childDetails.get("likes"));
                jsonObject.put("dislikes", childDetails.get("dislikes"));
                jsonObject.put("hobbies", childDetails.get("hobbies"));
                jsonObject.put("skills", childDetails.get("skills"));
                jsonObject.put("allergies", childDetails.get("allergies"));
            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());


        }catch (JSONException e){
            Logger.logE(TAG, "json exe : " , e);
        }catch(Exception ex){
            Logger.logE(TAG, "maptojson : " , ex);
        }
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        AddChildAIRespModel addChildAIRespModel=gson.fromJson(response.toString(), AddChildAIRespModel.class);
                        MyProgress.CancelDialog();
                        Logger.logD(TAG, " URL response : " + response.toString());
                        callBackResponse.childAddInfoResp(addChildAIRespModel);

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

    public static void addChildAdditionalInfoSave(final Context context, final HashMap<String, String> childDetails, final String user_id, final AddChildAInfoRespCallBack callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        String childId= childDetails.get("childId");
        String url =  Feeds.URL + Feeds.PARENT +"/"+user_id+Feeds.CHILD+"/"+childId+Feeds.CHILD_AINFO ;
        Logger.logD(TAG , " url : " +url);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("childId", childDetails.get("childId"));
            jsonObject.put("likes", childDetails.get("likes"));
            jsonObject.put("dislikes", childDetails.get("dislikes"));
            jsonObject.put("hobbies", childDetails.get("hobbies"));
            jsonObject.put("skills", childDetails.get("skills"));
            jsonObject.put("allergies", childDetails.get("allergies"));
            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());
        }catch (JSONException e){
            Logger.logE(TAG, "json exe : " , e);
        }catch(Exception ex){
            Logger.logE(TAG, "maptojson : " , ex);
        }
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        AddChildAIRespModel addChildAIRespModel=gson.fromJson(response.toString(), AddChildAIRespModel.class);
                        Logger.logD(TAG, " URL response : " + response.toString());
                        callBackResponse.childAddInfoResp(addChildAIRespModel);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                ToastUtils.displayToast(Constants.SERVER_ERROR, context);
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

