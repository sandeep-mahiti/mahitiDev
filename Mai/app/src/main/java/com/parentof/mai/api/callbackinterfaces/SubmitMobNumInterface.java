package com.parentof.mai.api.callbackinterfaces;

import com.parentof.mai.model.mobilerespmodel.MobileSubmitModel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by sandeep HR on 02/01/17.
 */

public interface SubmitMobNumInterface {

    @GET("/")
    void getOTP(Callback<MobileSubmitModel> callback);
}
