package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.parentof.mai.activityinterfaces.SaveReminderCallBack;
import com.parentof.mai.model.reminders.GetReminderResponse;
import com.parentof.mai.model.reminders.ReminderSavedResponse;
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
 * Created by mahiti on 10/4/17.
 */
public class SendRemindersToServer {


    private static final String TAG = " SendRemindersToServer ";

    public static void sendRemindersAPI(final Context context, JsonArray jsonReminderString, final String user_id, final String childId, final SaveReminderCallBack saveReminderCallBack) {
        MyProgress.show(context, "", "");
        // http://dev-arcapi.askm.ai/v0/reminder/113/child/58e728f0975a24c8479a55e4
        String url = Feeds.URL + Feeds.REMINDER + "/" + user_id + Feeds.CHILD + "/" + childId;
        Logger.logD(TAG, " ----sendRemindersAPI url  ----- : " + url);
        JSONObject jsonObject = new JSONObject();
        try {
            if (jsonReminderString != null) {
                jsonObject.put("userId", user_id);
                jsonObject.put("childId", childId);
                jsonObject.put("reminders", jsonReminderString);

                Logger.logD(TAG, " ---- sendRemindersAPI Params ----- : " + jsonObject.toString());
            }

        } catch (JSONException e) {
            Logger.logE(TAG, "json exe : ", e);
        } catch (Exception ex) {
            Logger.logE(TAG, "maptojson : ", ex);
        }
        final Map<String, String> params = new HashMap<>();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        String data = gson.toJson(response);
                        Logger.logD(TAG, " URL response : " + response.toString());
                        ReminderSavedResponse reminderSavedResponse = gson.fromJson(response.toString(), ReminderSavedResponse.class);
                        MyProgress.CancelDialog();
                        Logger.logD(TAG, " URL response : " + response.toString());
                        saveReminderCallBack.saveReminderResponseCallBack(reminderSavedResponse);

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

    public static void getRemindersAPI(final Context context, final String user_id, final String childId, final SaveReminderCallBack saveReminderCallBack) {
        MyProgress.show(context, "", "");
        String url = Feeds.URL + Feeds.REMINDER + "/" + user_id + Feeds.CHILD + "/" + childId;
        Logger.logD(TAG, " ----sendRemindersAPI url  ----- : " + url);
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest getReq = new JsonObjectRequest(Request.Method.GET, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Logger.logD(TAG, " URL response : " + response.toString());
                        GetReminderResponse getReminderResponse = gson.fromJson(response.toString(), GetReminderResponse.class);
                        MyProgress.CancelDialog();
                        Logger.logD(TAG, " URL response : " + response.toString());
                        saveReminderCallBack.getResponseCallBack(getReminderResponse);

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
