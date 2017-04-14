package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.UpdateUserGenaralResponse;

import com.parentof.mai.model.updateuser.UpdateUserGeneralBean;
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
 * Created by mahiti on 20/1/17.
 */
public class UpdateUserGeneralApi {
    private static final String TAG = "UP userGeneral";

    public static void addUserGeneralInfo(final Context context, final HashMap<String, String> userDetails, final String id,  final UpdateUserGenaralResponse userGenaralResponse) {
        String url = Feeds.BASEURL + Feeds.BACKEND + Feeds.USER_GENERAL + id;
        MyProgress.show(context, "", "");
       // http://auth.askm.ai/backend/user/9620479549
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", userDetails.get("firstname"));
            jsonObject.put("lastname", userDetails.get("lastname"));
            jsonObject.put("email", userDetails.get("email"));
            jsonObject.put("mobile", userDetails.get("mobile"));
            //jsonObject.put("dob", userDetails.get("dob"));
           // jsonObject.put("relation", userDetails.get("relation"));
            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());

        } catch (JSONException e) {
            Logger.logE(TAG, "json exe : ", e);
        } catch (Exception ex) {
            Logger.logE(TAG, "maptojson : ", ex);
        }
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MyProgress.CancelDialog();
                        Gson gson = new Gson();
                        try {
                            Object item = response.get("data");
                            if (item instanceof JSONObject) {
                                // it's an array
                                //JSONArray urlArray = (JSONArray) item;
                                String data = gson.toJson(response);
                                UpdateUserGeneralBean updateUserGeneralBean = gson.fromJson(response.toString(), UpdateUserGeneralBean.class);
                                Logger.logD(TAG, " URL response : " + response);
                                userGenaralResponse.updateUserGeneralResponse(updateUserGeneralBean);


                                // do all kinds of JSONArray'ish things with urlArray
                            } else if (item instanceof String) {
                                // if you know it's either an array or an object, then it's an object
                                MyProgress.CancelDialog();
                                String strResp = (String) item; // do objecty stuff with urlObject
                                ToastUtils.displayToast(strResp, context);

                                Logger.logD(TAG, strResp);
                                return;
                            }
                        }catch (Exception e){
                            Logger.logE(TAG, " Exception : " , e);
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

    public static void addUserGeneralInfoSave(final Context context, final HashMap<String, String> userDetails, final String id,  final UpdateUserGenaralResponse userGenaralResponse) {
        String url = Feeds.BASEURL + Feeds.BACKEND + Feeds.USER_GENERAL + id;
        // http://auth.askm.ai/backend/user/9620479549
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstname", userDetails.get("firstname"));
            jsonObject.put("lastname", userDetails.get("lastname"));
            jsonObject.put("email", userDetails.get("email"));
            jsonObject.put("mobile", userDetails.get("mobile"));
            //jsonObject.put("dob", userDetails.get("dob"));
            // jsonObject.put("relation", userDetails.get("relation"));
            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());

        } catch (JSONException e) {
            Logger.logE(TAG, "json exe : ", e);
        } catch (Exception ex) {
            Logger.logE(TAG, "maptojson : ", ex);
        }
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        UpdateUserGeneralBean updateUserGeneralBean = gson.fromJson(response.toString(), UpdateUserGeneralBean.class);
                        Logger.logD(TAG, " URL response : " + data);
                        userGenaralResponse.updateUserGeneralResponse(updateUserGeneralBean);

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
