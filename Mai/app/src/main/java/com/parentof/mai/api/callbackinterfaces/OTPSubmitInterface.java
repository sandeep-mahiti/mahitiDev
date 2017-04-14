package com.parentof.mai.api.callbackinterfaces;


import com.parentof.mai.model.mobotpcreateusermodels.MobOTPRespModel;
import com.parentof.mai.utils.Feeds;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by sandeep HR on 03/01/17.
 */

public interface OTPSubmitInterface {

    @FormUrlEncoded
    @POST("/")
    void postOTP(@Field("app_id") String app_id, @Field("token") String token, @Field("user_id") String user_id, Callback<MobOTPRespModel> callback);

}
