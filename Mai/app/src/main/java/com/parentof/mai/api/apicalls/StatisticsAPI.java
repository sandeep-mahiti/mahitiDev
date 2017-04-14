package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.StatisticsRespCallBack;
import com.parentof.mai.model.statisticsmodel.StatisticsRespModel;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandeep HR on 31/03/17.
 */

public class StatisticsAPI {
    private static final String TAG = " StatisticsAPI ";

    public static void getUserDetails(final Context context, final String user_id,final String childId,final String dpId, final StatisticsRespCallBack callBackResponse ) {
        // Logger.logD("my request URL", "my request---" + RbaConstants.MAIN_URL + RbaFeeds.SUBMIT_REQUEST_COMMENT);

       // MyProgress.show(context, "", "");
        String url = Feeds.URL +Feeds.DP+"/"+ user_id+"/"+childId+"/"+dpId+Feeds.STATACHIEVED;
        Logger.logD(TAG, " url " + url);
        final Map<String, String> params = new HashMap<>();
        JSONObject jsonObject=new JSONObject();
        try {
            /*jsonObject.put("app_id", app_id);
            jsonObject.put("token", token);
            jsonObject.put("user_id", user_id);*/
            Logger.logD(TAG , " ----Params ----- : " +jsonObject.toString());
        }catch (Exception e){
            Logger.logE(TAG, "json exe :" , e);
        }
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //MyProgress.CancelDialog();
                        Logger.logD(TAG," URL response : "+response.toString());
                        try {

                            Gson gson = new Gson();
                            String data = gson.toJson(response);
                            StatisticsRespModel statisticsRespModel=gson.fromJson(response.toString(),StatisticsRespModel.class);
                            callBackResponse.statisticsCallBack(statisticsRespModel, dpId);
                        } catch (Exception e) {
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
