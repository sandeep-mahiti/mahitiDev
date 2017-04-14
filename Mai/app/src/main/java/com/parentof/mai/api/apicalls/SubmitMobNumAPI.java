package com.parentof.mai.api.apicalls;

import android.content.Context;

import com.google.gson.Gson;
import com.parentof.mai.activityinterfaces.MobileNumCallbackInterface;
import com.parentof.mai.api.SignApiRestAdapter;
import com.parentof.mai.api.callbackinterfaces.SubmitMobNumInterface;

import com.parentof.mai.model.mobilerespmodel.MobileSubmitModel;
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
 * Created by sandeep HR on 02/01/17.
 */

public class SubmitMobNumAPI {


    Context context;

    public SubmitMobNumAPI(Context context) {
        this.context=context;
    }

    private static final String TAG = " RequestCallApi ";

    public void callAPI(String mobilenum, final MobileNumCallbackInterface callBackResponse) {
        MyProgress.show(context, "", "");
        String s="/"+mobilenum+Feeds.APP_ID;
        Logger.logD(TAG," CALL API "+ Feeds.BASEURL+ Feeds.BACKEND+Feeds.OTP+s);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Feeds.BASEURL+Feeds.BACKEND+Feeds.OTP+s).setRequestInterceptor(new SignApiRestAdapter.MyRetrofitInterceptor()).build();

        final SubmitMobNumInterface submitMobNumInterface =restAdapter.create(SubmitMobNumInterface.class);


        submitMobNumInterface.getOTP(new Callback<MobileSubmitModel>() {
            @Override
            public void success(MobileSubmitModel mobileSubmitModel, Response response) {
                Gson gson = new Gson();
                String data = gson.toJson(mobileSubmitModel);
                Logger.logD(TAG," URL response : "+data);
                Logger.logD(TAG," URL response : "+response.toString());
                 MyProgress.CancelDialog();
                callBackResponse.getMobileNumResp(mobileSubmitModel);

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
