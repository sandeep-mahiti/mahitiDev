package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.MobValidOnlyCallback;
import com.parentof.mai.model.MobValidOnlyRespModel;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandeep HR on 08/02/17.
 */

public class MobValidOnlyAPI {

    private static final String TAG = " MobValidOnlyAPI ";

    public static void submitMobi(final Context context, final String mob, final MobValidOnlyCallback callBackResponse ) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        String url = Feeds.BASEURL + Feeds.BACKEND+Feeds.OTP_VERIFYONLY+"/"+mob+Feeds.APP_ID;   // TOLOGINGETOTP+Feeds.OTP_VALIDATE_CREATE_USER;

        Logger.logD(TAG , " url : " +url);
        MyProgress.show(context, "", "");
        final Map<String, String> params = new HashMap<>();
        JSONObject jsonObject=new JSONObject();
        try {

            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());
        }catch (Exception e){
            Logger.logE(TAG, "json exe :" , e);
        }
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        MobValidOnlyRespModel otpResponseModel=gson.fromJson(response.toString(),MobValidOnlyRespModel.class);
                        MyProgress.CancelDialog();
                        Logger.logD(TAG," URL response : "+data);
                        callBackResponse.getMobOTP(otpResponseModel);

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
