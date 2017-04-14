package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.EmailOTPRespCallBack;

import com.parentof.mai.model.emailotprespmodels.EmailOTPRespModel;
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
 * Created by mahiti on 4/1/17.
 */
public class EmailOtpAPi {

    private static final String TAG = " OTPSubmitAPI_Volley ";

    public static void submitOtp(final Context context, final String app_id, final String token, final String user_id, final String email, final EmailOTPRespCallBack callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        MyProgress.show(context, "", "");
        String url = Feeds.BASEURL +Feeds.BACKEND+ Feeds.OTP + Feeds.OTP_VALIDATE;
        Logger.logD(TAG , " url : " +url);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("app_id", app_id);
            jsonObject.put("token", token);
            jsonObject.put("mobile", user_id);
            jsonObject.put("email", email);
            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());
        }catch (JSONException e){
            Logger.logE(TAG, "json exe :" , e);
        }
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        EmailOTPRespModel emailOTPRespModel=gson.fromJson(response.toString(), EmailOTPRespModel.class);
                        MyProgress.CancelDialog();
                        Logger.logD(TAG, " URL response : " + data);
                        callBackResponse.emailOtpResponse(emailOTPRespModel);

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

}
