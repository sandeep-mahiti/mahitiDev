package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.AddparentRespCallBack;
import com.parentof.mai.model.addparentmodel.AddparentRespModel;
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

public class AddParentAPI {


    private static final String TAG = " AddParentAPI ";

    public static void addParent(final Context context, final String user_id,  final AddparentRespCallBack callBackResponse) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);
        MyProgress.show(context, "", "");
        String url =  Feeds.URL + Feeds.PARENT;
        Logger.logD(TAG , " url : " +url);
        JSONObject jsonObject=new JSONObject();
        try {

            jsonObject.put("userId", user_id);
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
                        AddparentRespModel addparentRespModel=gson.fromJson(response.toString(), AddparentRespModel.class);
                        MyProgress.CancelDialog();
                        Logger.logD(TAG, " URL response : " + data);
                        callBackResponse.addparentResp(addparentRespModel);

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
