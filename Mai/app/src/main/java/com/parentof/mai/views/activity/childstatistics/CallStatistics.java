package com.parentof.mai.views.activity.childstatistics;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;

import com.parentof.mai.activityinterfaces.StatisticsRespCallBack;
import com.parentof.mai.api.apicalls.StatisticsAPI;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.decisionpointsmodel.DPRespModel;
import com.parentof.mai.model.decisionpointsmodel.Data;
import com.parentof.mai.utils.CheckNetwork;
import com.parentof.mai.utils.Logger;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by sandeep HR on 31/03/17.
 */
public class CallStatistics extends AsyncTask<Context ,Void, Void>{

    Context context;
    DPRespModel dpListModel;
    String userId;
    String childId;
    DatabaseHelper dbHelper;
    List<AllDP> dpData;
    StatisticsRespCallBack activity;
    String TAG="CallStatistics ";

    public CallStatistics(Context context, StatisticsRespCallBack activity, String userId, String chidlId) {
       // this.dpListModel=dpListModel;
        dbHelper=new DatabaseHelper(context);
        this.activity=activity;
        this.context=context;
        this.userId=userId;
        this.childId=chidlId;
    }



    @Override
    protected Void doInBackground(Context... params) {
        try {
            dpData=dbHelper.selOneFromDPTable(userId, childId );
            if (Looper.myLooper() == null)
                Looper.prepare();

            for(int i=0;i<dpData.size(); i++) {
                StatisticsAPI.getUserDetails(context, userId, childId, dpData.get(i).getId(), activity);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG,"doInBackground ",e );
        }

        return null;
    }
}
