package com.parentof.mai.api.apicalls;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.AddChildHinfoRespCallBack;
import com.parentof.mai.model.addchildmodel.AddChildHIRespModel;
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

public class AddChildHealthInfoAPI {

    private static final String TAG = "ChHealInfAPI";

   /*{
   "childId":"587daba852da0e095a0445cd",
   "doctorName":"Mahiti",
   "doctorContactNumber":"9876543210",
   "HospitalName":"Sagar",
   "HospitalAddress":"BG road, Bangalore"
}*/

    public static void addChildMedicalInfo(final Context context, final HashMap<String, String> childDetails, final String user_id, final AddChildHinfoRespCallBack callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        String childId= childDetails.get("childId");
        String url =  Feeds.URL + Feeds.PARENT +"/"+user_id+Feeds.CHILD+"/"+childId+Feeds.CHILD_HEALTH_INFO ;
        Logger.logD(TAG , " url : " +url);
        MyProgress.show(context, "", "");
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("childId", childDetails.get("childId"));
            jsonObject.put("doctorName", childDetails.get("doctorName"));
            jsonObject.put("doctorContactNumber", childDetails.get("doctorContactNumber"));
            jsonObject.put("HospitalName", childDetails.get("HospitalName"));
            jsonObject.put("HospitalAddress", childDetails.get("HospitalAddress"));
            jsonObject.put("HospitalContactNumber", childDetails.get("HospitalContactNumber"));

            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());
        }catch (JSONException e){
            Logger.logE(TAG, "json exe : " , e);
        }catch(Exception ex){
            Logger.logE(TAG, "maptojson : " , ex);
        }
        Log.d(TAG,"object - "+jsonObject.toString());
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        AddChildHIRespModel addChildHIRespModel=gson.fromJson(response.toString(), AddChildHIRespModel.class);
                        MyProgress.CancelDialog();
                        Logger.logD(TAG, " URL response : " + response.toString());
                         callBackResponse.childHealthInfo(addChildHIRespModel);

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

    public static void addChildMedicalInfoSave(final Context context, final HashMap<String, String> childDetails, final String user_id, final AddChildHinfoRespCallBack callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        String childId= childDetails.get("childId");
        String url =  Feeds.URL + Feeds.PARENT +"/"+user_id+Feeds.CHILD+"/"+childId+Feeds.CHILD_HEALTH_INFO ;
        //String url =  Feeds.URL + Feeds.PARENT +"/"+user_id+Feeds.CHILD+Feeds.CHILD_HEALTH_INFO ;
        Logger.logD(TAG , " url : " +url);
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("childId", childDetails.get("childId"));
            jsonObject.put("doctorName", childDetails.get("doctorName"));
            jsonObject.put("doctorContactNumber", childDetails.get("doctorContactNumber"));
            jsonObject.put("HospitalName", childDetails.get("HospitalName"));
            jsonObject.put("HospitalAddress", childDetails.get("HospitalAddress"));
            jsonObject.put("HospitalContactNumber", childDetails.get("HospitalContactNumber"));

            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());
        }catch (JSONException e){
            Logger.logE(TAG, "json exe : " , e);
        }catch(Exception ex){
            Logger.logE(TAG, "maptojson : " , ex);
        }
        Log.d(TAG,"object - "+jsonObject.toString());
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        AddChildHIRespModel addChildHIRespModel=gson.fromJson(response.toString(), AddChildHIRespModel.class);
                        Logger.logD(TAG, " URL response : " + response.toString());
                        callBackResponse.childHealthInfo(addChildHIRespModel);

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
