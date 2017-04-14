package com.parentof.mai.receivers;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.parentof.mai.activityinterfaces.SMSCallBackInterface;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {
    String smsFormNumber = "";
    String smsMessageBody = "";
    SharedPreferences preferences;
    String TAG = "SMS RECEiVER";
    private static SMSCallBackInterface mListener;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Call OnReceive-");
        if (null != context && intent != null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);

            //---get the SMS message passed in---
            Bundle bundle = intent.getExtras();
            StringBuilder finalSMSBody = new StringBuilder();
            if (bundle != null) {
                //---retrieve the SMS message received---
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus == null)
                    return;
                SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                for (int i = 0; i < msgs.length; i++) {
                    smsFormNumber = msgs[i].getOriginatingAddress(); // get the number from sender
                    Log.d(TAG, "smsFormNumber--" + smsFormNumber);
                    finalSMSBody.append(msgs[i].getMessageBody()); // append the message body to final string
                }
                if (smsFormNumber != null) {
                    smsMessageBody = finalSMSBody.toString(); // getting sms body and storing into final string
                    Log.d(TAG, "smsMessageBody--" + smsMessageBody);

                }
                if (smsMessageBody != null && !smsMessageBody.isEmpty() && smsFormNumber.contains("askMAI")) {
                        Pattern p = Pattern.compile("(|^)\\d{4}");
                        Matcher m = p.matcher(smsMessageBody);
                        if (m.find()) {
                            String otp = m.group(0);
                            Logger.logD(Constants.PROJECT, "SMS OTP--" + otp);
                            if (mListener!=null && otp != null)
                                mListener.populateSMSOtp(otp);
                        }
                        /*String otp = smsMessageBody.substring(30, 34);
                        Logger.logD(Constants.PROJECT, "SMS OTP--" + otp);
                        mListener.populateSMSOtp(otp);*/
                }
            }

        }
    }


    public static void bindListener(SMSCallBackInterface listener) {
        mListener = listener;
    }

    public void showToast(Context context, String message) {
        //Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
