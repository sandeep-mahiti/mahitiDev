package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.AddChildRespCallBack;
import com.parentof.mai.model.addchildmodel.AddChildRespModel;
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
 * Created by sandeep HR on 16/01/17.
 */

public class AddChildAPI {

    /* {
"firstName": "Test kid",
"nickName": "Kid",
"lastName": "test",
"dob": "02-08-1990",
"gender": "male",
"bloodGroup": "B+",
"school": "ZPH",
"class": "10"
}*/


    private static final String TAG = " AddChildAPI ";

    public static void addChild(final Context context, final HashMap<String, String> childDetails, final String user_id, final AddChildRespCallBack callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        MyProgress.show(context, "", "");
        String url =  Feeds.URL + Feeds.PARENT +"/"+user_id+Feeds.CHILD ;
        Logger.logD(TAG , " ---- url  ----- : " +url);
        JSONObject jsonObject=new JSONObject();


        try {
            if(childDetails!=null && !childDetails.isEmpty()) {
                jsonObject.put("firstName", childDetails.get("firstName"));
                jsonObject.put("nickName", childDetails.get("nickName"));
                jsonObject.put("lastName", childDetails.get("lastName"));
                jsonObject.put("dob", childDetails.get("dob"));
                jsonObject.put("gender", childDetails.get("gender"));
                jsonObject.put("bloodGroup", childDetails.get("bloodGroup"));
                jsonObject.put("school", childDetails.get("school"));
                jsonObject.put("class", childDetails.get("class"));
                jsonObject.put(Constants.CHILD_SECTION, childDetails.get(Constants.CHILD_SECTION));
                jsonObject.put(Constants.CHILD_ROLLNUMBER, childDetails.get(Constants.CHILD_ROLLNUMBER));
                Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());
            }

        }catch (JSONException e){
            Logger.logE(TAG, "json exe : " , e);
        }catch(Exception ex){
            Logger.logE(TAG, "maptojson : " , ex);
        }
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        Logger.logD(TAG, " URL response : " + response.toString());
                        AddChildRespModel addparentRespModel=gson.fromJson(response.toString(), AddChildRespModel.class);
                        MyProgress.CancelDialog();
                        Logger.logD(TAG, " URL response : " + response.toString());
                        callBackResponse.addChildResp(addparentRespModel);

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

    public static void addChildSave(final Context context, final HashMap<String, String> childDetails, final String user_id, final AddChildRespCallBack callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        String url =  Feeds.URL + Feeds.PARENT +"/"+user_id+Feeds.CHILD ;
        Logger.logD(TAG , " ---- url  ----- : " +url);
        JSONObject jsonObject=new JSONObject();


        try {
            if(childDetails!=null && !childDetails.isEmpty()) {
                jsonObject.put("firstName", childDetails.get("firstName"));
                jsonObject.put("nickName", childDetails.get("nickName"));
                jsonObject.put("lastName", childDetails.get("lastName"));
                jsonObject.put("dob", childDetails.get("dob"));
                jsonObject.put("gender", childDetails.get("gender"));
                jsonObject.put("bloodGroup", childDetails.get("bloodGroup"));
                jsonObject.put("school", childDetails.get("school"));
                jsonObject.put("class", childDetails.get("class"));
                jsonObject.put(Constants.CHILD_SECTION, childDetails.get(Constants.CHILD_SECTION));
                jsonObject.put(Constants.CHILD_ROLLNUMBER, childDetails.get(Constants.CHILD_ROLLNUMBER));
                Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());
            }

        }catch (JSONException e){
            Logger.logE(TAG, "json exe : " , e);
        }catch(Exception ex){
            Logger.logE(TAG, "maptojson : " , ex);
        }
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        Logger.logD(TAG, " URL response : " + response.toString());
                        AddChildRespModel addparentRespModel=gson.fromJson(response.toString(), AddChildRespModel.class);
                        Logger.logD(TAG, " URL response : " + response.toString());
                        callBackResponse.addChildResp(addparentRespModel);

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
