package com.parentof.mai.views.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.parentof.mai.R;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SplashScreen extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
        Utility.currentDateTime();
        prefs = getApplicationContext().getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    if(prefs.getBoolean(Constants.MOBNUM_CHANGE, false))
                        prefs.edit().putBoolean(Constants.MOBNUM_CHANGE, false).apply();

                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    //TODO uncomment for Dashboard access directly if user is verfied, mob, email, security questions
                    /*if(prefs.getBoolean(Constants.SEC_QST_VERIFIED,false)){
                        Intent intent = new Intent(SplashScreen.this, DashboardActivity.class);
                        startActivity(intent);
                    }else {
                        checkMobOrEmailVerified();
                    }*/
                   /* Intent i=new Intent(SplashScreen.this, MaiDashboardActivity.class);
                    startActivity(i);*/
                    //checkEmailVerified();
                    //checkSecQstnsVerified();
                    checkIfNewUser();
                }
            }
        };
        timerThread.start();
    }
    void hashMapDemo() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "venky");
        map.put("key2", "anusha");
        Logger.logD(Constants.PROJECT, "1st--" + map.get("key1") + "--2nd--" + map.get("key2"));

      /* for(int i=0;i<5;i++){

       }*/
        Map<String, String> map1 = new HashMap<>();
        map1.put("key1", "adda");
        map1.put("key1", "venkyrrtg");
        map1.put("key1", "anushaaaa");
        map1.put("key2", "Sadeep");
        Logger.logD(Constants.PROJECT, " map 1 1st--" + map1.get(0) + "--2nd--" + map1.get("key2"));

        Map<String,List<String>> map2 = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("xyz");
        list.add("abc");
        map2.put("key1", list);
        Logger.logD(Constants.PROJECT, " map 2 1st--" + map2.get("key1").get(1));
        Map<Float, String> map3 = new HashMap<>();
        map3.put(1.2f, "venky");
        map3.put(2.3f, "anusha");
        Logger.logD(Constants.PROJECT, " map 1 1st--" + map3.get(1.2f) + "--2nd--" + map3.get(2.3f));


    }
    private void checkIfNewUser() {
        if(prefs.getBoolean(Constants.USER_VERIFIED, false)){
            Intent intent = new Intent(SplashScreen.this, MaiDashboardActivity.class);
            startActivity(intent);
        }else{
           // checkSecQstnsVerified();
            Intent intent = new Intent(SplashScreen.this, IntroductionActivity.class);
            startActivity(intent);
        }
    }


    private void checkSecQstnsVerified() {
        if(prefs.getBoolean(Constants.SEC_QST_VERIFIED,false)){
            Intent intent = new Intent(SplashScreen.this, MaiDashboardActivity.class);
            startActivity(intent);
        }else {
            checkEmailVerified();
        }
    }


    private void checkEmailVerified() {
        if(prefs.getBoolean(Constants.EMAIL_OTP_VERIFIED,false)){
            Intent intent = new Intent(SplashScreen.this, SecurityQuestionActivity.class);
            startActivity(intent);
        }else {
            //checkMobileVerified();
            Intent intent = new Intent(SplashScreen.this, IntroductionActivity.class);
            startActivity(intent);
        }
    }

    private void checkMobileVerified() {
        if (prefs.getBoolean(Constants.MOB_OTP_VERIFIED, false)) {
            Intent i = new Intent(SplashScreen.this, ParentDetailsActivity.class);
            startActivity(i);
        } else {
            Intent intent = new Intent(SplashScreen.this, IntroductionActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
