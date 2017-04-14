package com.parentof.mai.activityinterfaces;



import com.parentof.mai.model.emailotprespmodels.EmailOTPRespModel;

import org.json.JSONObject;

/**
 * Created by mahiti on 4/1/17.
 */
public interface EmailOTPRespCallBack {
    void emailOtpResponse(EmailOTPRespModel emailOTPRespModel);
    //void emailOtpResponse(JSONObject response);
}
