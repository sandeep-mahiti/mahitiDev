package com.parentof.mai.api.callbackinterfaces;

import com.parentof.mai.model.EmailResponseModel;
import com.parentof.mai.model.emailrespmodels.EmailRespModel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by sandeep HR on 04/01/17.
 */

public interface SubmitEmailInterface {
    @GET("/")
    void getEmailOTP(Callback<EmailRespModel> callback);
}
