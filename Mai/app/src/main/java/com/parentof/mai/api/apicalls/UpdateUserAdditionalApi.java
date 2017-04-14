package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.UpdateUserAdditionalResponse;
import com.parentof.mai.model.updateuser.UpdateUserAdditionalBean;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahiti on 20/1/17.
 */
public class UpdateUserAdditionalApi {

    private static final String TAG = "UP userGeneral";

    public static void addUserAdditionalInfo(final Context context, final HashMap<String, String> userDetails, final String user_id, final UpdateUserAdditionalResponse updateUserAdditionalResponse) {
        String url = Feeds.URL + Feeds.PARENT +"/"+ user_id + Feeds.USER_ADDITIONAL;
      //  MyProgress.show(context, "", "");
        // http://dev-arcapi.askm.ai/v0/parent/9620479549/addionalInfo
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profession", userDetails.get("profession"));
            jsonObject.put("officeDays", userDetails.get("officeDays"));
            jsonObject.put("officeTiming", userDetails.get("officeTiming"));
            jsonObject.put("averageIncome", userDetails.get("averageIncome"));
            jsonObject.put("seperateChildRoom",userDetails.get("seperateChildRoom"));
            jsonObject.put("dob", userDetails.get("dob"));
            jsonObject.put("relation", userDetails.get("relation"));


            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());

        } catch (JSONException e) {
            Logger.logE(TAG, "json exe : ", e);
        } catch (Exception ex) {
            Logger.logE(TAG, "maptojson : ", ex);
        }
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        UpdateUserAdditionalBean updateUserAdditionalBean = gson.fromJson(response.toString(), UpdateUserAdditionalBean.class);
                       // MyProgress.CancelDialog();
                        Logger.logD(TAG, " URL response : " + data);
                        updateUserAdditionalResponse.updateUserAddResponse(updateUserAdditionalBean);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  MyProgress.CancelDialog();
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

    public static void addUserAdditionalInfoSave(final Context context, final HashMap<String, String> userDetails, final String user_id, final UpdateUserAdditionalResponse updateUserAdditionalResponse) {
        String url = Feeds.URL + Feeds.PARENT +"/"+ user_id + Feeds.USER_ADDITIONAL;
        // http://dev-arcapi.askm.ai/v0/parent/9620479549/addionalInfo
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profession", userDetails.get("profession"));
            jsonObject.put("officeDays", userDetails.get("officeDays"));
            jsonObject.put("officeTiming", userDetails.get("officeTiming"));
            jsonObject.put("averageIncome", userDetails.get("averageIncome"));
            jsonObject.put("seperateChildRoom",userDetails.get("seperateChildRoom"));
            jsonObject.put("dob", userDetails.get("dob"));
            jsonObject.put("relation", userDetails.get("relation"));


            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());

        } catch (JSONException e) {
            Logger.logE(TAG, "json exe : ", e);
        } catch (Exception ex) {
            Logger.logE(TAG, "maptojson : ", ex);
        }
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.PUT, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        UpdateUserAdditionalBean updateUserAdditionalBean = gson.fromJson(response.toString(), UpdateUserAdditionalBean.class);
                        Logger.logD(TAG, " URL response : " + data);
                        updateUserAdditionalResponse.updateUserAddResponse(updateUserAdditionalBean);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
