package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.MobOTPRespCallbackInterface;
import com.parentof.mai.api.SignApiRestAdapter;
import com.parentof.mai.api.callbackinterfaces.OTPSubmitInterface;
import com.parentof.mai.model.mobotpcreateusermodels.MobOTPRespModel;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Feeds;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.MyProgress;
import com.parentof.mai.utils.ToastUtils;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sandeep HR on 03/01/17.
 */

public class OTPSubmitAPI {
    Context context;

    public OTPSubmitAPI(Context context) {
        this.context=context;
    }

    private static final String TAG = " OTPSubmitAPI ";

    public void callOTPSubmitAPI(String app_id, String token, String user_id,final MobOTPRespCallbackInterface callBackResponse) {
        //String s="/"+otp+ Feeds.APP_ID;

        Logger.logD(TAG," CALL API "+ Feeds.BASEURL+ Feeds.BACKEND+ Feeds.OTP);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Feeds.BASEURL+Feeds.BACKEND+ Feeds.OTP).setRequestInterceptor(new SignApiRestAdapter.MyRetrofitInterceptor()).build();

        Logger.logD(TAG, " ------- params ---- \n app_id : "+app_id+" \n token : "+token+"\nuser_id : "+user_id);
        final OTPSubmitInterface otpSubmitInterface =restAdapter.create(OTPSubmitInterface.class);
        MyProgress.show(context, "", "");

        otpSubmitInterface.postOTP(app_id,token,user_id, new Callback<MobOTPRespModel>() {
            @Override
            public void success(MobOTPRespModel otpResponseModel, Response response) {

                Gson gson = new Gson();
                String data = gson.toJson(otpResponseModel);
                Logger.logD(TAG," URL response : "+data);
                MyProgress.CancelDialog();
               // callBackResponse.otpSubmitResp(otpResponseModel);
            }

            @Override
            public void failure(RetrofitError error) {
                MyProgress.CancelDialog();
                error.printStackTrace();
                ToastUtils.displayToast(Constants.SERVER_ERROR, context);
            }
        });

    }
}
