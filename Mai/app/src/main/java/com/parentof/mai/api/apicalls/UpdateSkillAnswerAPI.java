package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.UpdateSkillQAnsCallback;
import com.parentof.mai.model.UpdateSkillAnswerRespModel;

import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandeep HR on 22/02/17.
 */ //

public class UpdateSkillAnswerAPI {

    private static final String TAG = " updateSkilAnswerAPI ";
    public static void updateAnswer(final Context context, final String user_id, final String childId, final String dpId, final String skillId , final  String questionId,  final  String answer,  final UpdateSkillQAnsCallback respCallBack) {

        String url =  Feeds.URL + Feeds.DP+"/"+user_id+"/"+childId+"/"+dpId+ Feeds.SKILL+"/"+skillId +Feeds.QUESTION+"/"+questionId+"/"+answer;
        MyProgress.show(context, "", "");
        Logger.logD(TAG, " url : " + url);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        MyProgress.CancelDialog();
                        UpdateSkillAnswerRespModel updateSkillAnsResp=gson.fromJson(response.toString(),UpdateSkillAnswerRespModel.class);
                        Logger.logD(TAG, "UserGeneral getFirstname --" + response.toString());
                        respCallBack.skillAnsResp(updateSkillAnsResp );

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

}
