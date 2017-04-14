package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.EmailRespCallbackInterface;
import com.parentof.mai.api.SignApiRestAdapter;
import com.parentof.mai.api.callbackinterfaces.SubmitEmailInterface;
import com.parentof.mai.model.emailrespmodels.EmailRespModel;
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
 * Created by sandeep HR on 04/01/17.
 */

public class EmailSubmitAPI {
    Context context;

    public EmailSubmitAPI(Context context) {
        this.context=context;
    }

    private static final String TAG = " OTPSubmitAPI ";

    public void callEmailSubmitAPI(String email, String mobile,final EmailRespCallbackInterface callBackResponse) {

        MyProgress.show(context, "", "");
        String mobile_num="/"+mobile;
        RestAdapter restAdapter;
        Logger.logD(TAG," CALL API "+ Feeds.BASEURL+ Feeds.BACKEND+ Feeds.OTP+Feeds.EMAIL+"/"+email+Feeds.APP_ID);
        //if(mobile==null || "".equals(mobile)) {
             restAdapter = new RestAdapter.Builder().setEndpoint(Feeds.BASEURL + Feeds.BACKEND + Feeds.OTP +Feeds.EMAIL+ "/" + email  + Feeds.APP_ID).setRequestInterceptor(new SignApiRestAdapter.MyRetrofitInterceptor()).build();

            Logger.logD(TAG, " ------- params ---- \n app_id : "+Feeds.APP_ID+" \n user_id(mobile) : "+mobile+"\n email : "+email);
        final SubmitEmailInterface subEmial =restAdapter.create(SubmitEmailInterface.class);
        subEmial.getEmailOTP(new Callback<EmailRespModel>() {
            @Override
            public void success(EmailRespModel otpResponseModel, Response response) {
                Gson gson = new Gson();
                String data = gson.toJson(otpResponseModel);
                Logger.logD(TAG," URL response : "+data);
                MyProgress.CancelDialog();
                callBackResponse.emailResp(otpResponseModel);

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
