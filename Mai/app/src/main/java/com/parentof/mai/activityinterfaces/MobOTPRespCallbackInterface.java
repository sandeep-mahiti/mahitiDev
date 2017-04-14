package com.parentof.mai.activityinterfaces;

import com.parentof.mai.model.mobileotprespmodel.MobileOTPRespModel;


/**
 * Created by sandeep HR on 03/01/17.
 */

public interface MobOTPRespCallbackInterface {
    void otpSubmitResp(MobileOTPRespModel otpResponseModel);
    //void otpSubmitResp(JSONObject otpResponseModel);
}
