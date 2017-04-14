package com.parentof.mai.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.parentof.mai.R;
import com.parentof.mai.activityinterfaces.ActivateSkillRespCallback;
import com.parentof.mai.activityinterfaces.GetDPSkillsRespCallback;
import com.parentof.mai.activityinterfaces.GetSkillQuestionsCallback;
import com.parentof.mai.api.apicalls.ActivateDPSKillAPI;
import com.parentof.mai.api.apicalls.GetDPSkillsAPI;
import com.parentof.mai.api.apicalls.GetSkillQuestionsAPI;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.db.DbConstants;
import com.parentof.mai.model.activateskillmodel.ActivateSkillRespModel;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.views.activity.MobileNumberActivity;
import com.parentof.mai.views.activity.SettingsActivity;
import com.parentof.mai.views.activity.dpchat.ChatActivity;
import com.parentof.mai.views.activity.reports_chathistory_activity.ChildReports_HistoryActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by mahiti on 3/1/17.
 */
public class CommonClass implements GetDPSkillsRespCallback, ActivateSkillRespCallback, GetSkillQuestionsCallback {

    Context context;

    private static final String TAG = "itmFrgmn ";

    private Child childBean;
    private String childId;

    private SharedPreferences prefs;
    private DatabaseHelper databaseHelper;
    private AllDP selectedDP;
    private GetSkillsRespModel getSKillRespModel;
    private SkillData selectedSkill;


    private GetSkillQuestionsRespModel skillQstnRespModel;


    public CommonClass(Activity activity, Context context, Child childBean, AllDP selectedDP) {
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
        this.prefs = context.getSharedPreferences(Constants.MAIPREF, Context.MODE_PRIVATE);
        this.childBean = childBean;
        this.childId = childBean.getChild().getId();
        this.selectedDP = selectedDP;
    }

    public static void loadMobileActivity(Activity activity) {
        Intent i = new Intent(activity, MobileNumberActivity.class);  //SecurityQuestionActivity , IntroductionActivity
        activity.startActivity(i);
        activity.finish();

    }

    public static void loadSettingsActivity(Activity activity) {
        Intent i = new Intent(activity, SettingsActivity.class);  //SecurityQuestionActivity , IntroductionActivity
        activity.startActivity(i);

    }

    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }


    public static int getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        return ageInt + 2;
    }

    public static boolean getImageFromDirectory(ImageView childImageView, String id) {
        try {
            File destinationFolder = new File(Environment.getExternalStorageDirectory(), "/Mai");
            if (destinationFolder.exists()) {
                String[] images = destinationFolder.list();
                //Log.d("Image Path", "createImagePathfileName-->" + images.length);
                for (int i = 0; i < images.length; i++) {
                    Log.d("Image Path", "Inside for lop Img --" + images[i]);
                    if (images[i].contains(id)) {
                        Log.d("Image Path", "createImagePath contains --" + images[i]);
                        File tempDeleteFile = new File(destinationFolder.getAbsolutePath() + "/" + images[i]);
                        Log.d("Image Path", "tempDeleteFile contains --" + tempDeleteFile);
                        Bitmap mBitmap = BitmapFactory.decodeFile(tempDeleteFile.getAbsolutePath());
                        Log.d("Image Path", "mBitmap --" + mBitmap);
                        if (mBitmap != null) {
                            childImageView.setImageBitmap(mBitmap);
                            return true;
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean getUserImageFromDirectory(ImageView childImageView, String id) {
        try {
            File destinationFolder = new File(Environment.getExternalStorageDirectory(), "/Mai");
            if (destinationFolder.exists()) {
                String[] images = destinationFolder.list();
                Log.d("Image Path", "createImagePathfileName-->" + images.length);
                for (int i = 0; i < images.length; i++) {
                    Log.d("Image Path", "Inside for lop Img --" + images[i]);
                    if (images[i].equals(id + ".png")) {
                        Log.d("Image Path", "createImagePath contains --" + images[i]);
                        File tempDeleteFile = new File(destinationFolder.getAbsolutePath() + "/" + images[i]);
                        Log.d("Image Path", "tempDeleteFile contains --" + tempDeleteFile);
                        Bitmap mBitmap = BitmapFactory.decodeFile(tempDeleteFile.getAbsolutePath());
                        Log.d("Image Path", "mBitmap --" + mBitmap);
                        if (mBitmap != null) {
                            childImageView.setImageBitmap(mBitmap);
                            return true;
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static File getFileImageFromFolder(String childId) {
        File tempDeleteFile = null;
        try {
            File destinationFolder = new File(Environment.getExternalStorageDirectory(), "/Mai");
            if (destinationFolder.exists()) {
                String[] images = destinationFolder.list();
                for (int i = 0; i < images.length; i++) {
                    Log.d("Image Path", "Inside for lop Img --" + images[i]);
                    if (images[i].contains(childId)) {
                        Log.d("Image Path", "createImagePath contains --" + images[i]);
                        tempDeleteFile = new File(destinationFolder.getAbsolutePath() + "/" + images[i]);
                        Log.d("Image Path", "tempDeleteFile contains --" + tempDeleteFile);

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempDeleteFile;
    }

    public static String getDate(String dateString) {
        String dt = null;
        try {
            if (!dateString.contains("T"))
                return dateString;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = null;
            try {
                value = formatter.parse(dateString);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            dateFormatter.setTimeZone(TimeZone.getDefault());

            try {
                dt = dateFormatter.format(value);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            Logger.logE("getDate", " ChildGen Frag , getChildren error", e);
        }
        if (dt == null)
            return dateString;
        else
            return dt;

    }


    public static Date stringToDate(String dateTime) {
        // String strDate = "2013-05-15T10:00:00-0700";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        return date;
    }

    public static String convertDateTimeToTime(String date) {
        String reminderTime = "";
        try {
            // DateFormat currentDf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
            DateFormat currentDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
            Date reminderDate = null;
            reminderDate = currentDf.parse(date);
            reminderTime = simpleDateFormat.format(reminderDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reminderTime;

    }

    public static String convertingOneDateToOther(String date) {
        String reminderTime = "";
        try {
            // DateFormat currentDf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
            DateFormat currentDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
            Date reminderDate = null;
            reminderDate = currentDf.parse(date);
            reminderTime = simpleDateFormat.format(reminderDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reminderTime;

    }

    public static Date currentDateTime() {
        //  String currentDate = null;
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            date = calendar.getTime();
            Date today = new Date();
            String currentDate = df.format(today);
            date = df.parse(currentDate);
        } catch (Exception e) {
            Logger.logE("TAG", "Exception in mrg date", e);
        }
        return date;
    }

    public static Date returnDateTime(String reminderDate) {
        //  String currentDate = null;
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            date = df.parse(reminderDate);
        } catch (Exception e) {
            Logger.logE("TAG", "Exception in mrg date", e);
        }
        return date;
    }

    public static String BitMapToString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static int getChildAge(int DOByear, int DOBmonth, int DOBday) {

        int age;

        final Calendar calenderToday = Calendar.getInstance();
        int currentYear = calenderToday.get(Calendar.YEAR);
        int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
        int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);

        age = currentYear - DOByear;

        if (DOBmonth > currentMonth) {
            --age;
        } else if (DOBmonth == currentMonth) {
            if (DOBday > todayDay) {
                --age;
            }
        }
        return age;
    }


    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static void setGravatarUserImage(String relation, ImageView imageView) {
        Logger.logD(Constants.PROJECT, "Relation--" + relation);
        switch (relation) {
            case "Father":
                imageView.setImageResource(R.drawable.user_1);
                break;

            case "Mother":
                imageView.setImageResource(R.drawable.user_4);
                break;

            case "Grandfather":
                imageView.setImageResource(R.drawable.user_3);
                break;

            case "Grandmother":
                imageView.setImageResource(R.drawable.user_6);
                break;
            case "Guardian - Male":
                imageView.setImageResource(R.drawable.user_2);
                break;

            case "Guardian - Female":
                imageView.setImageResource(R.drawable.user_5);
                break;
            default:
                break;

        }
    }


    public void checkIfDpisActive() {
        try {
            if (databaseHelper.selDPisActive(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP)) {//dpis active){

                this.getSKillRespModel = databaseHelper.selAllFromSkillsTable(childId, selectedDP.getId());//from skill table
                if (!this.getSKillRespModel.getData().isEmpty()) {

                    decideNextScreen();
                } else {
                    //ToastUtils.displayToast("Contacting Server!", getActivity());
                    Logger.logD(TAG, " No skills for selected DP, Contacting server...");
                    callSkillsAPI(selectedDP.getId());
                }

            } else {
                callSkillsAPI(selectedDP.getId());
            }

        } catch (Exception e) {
            ToastUtils.displayToast("checkIfDpisActive", context);
            Logger.logE(TAG, "checkIfDpisActive : ", e);
        }
    }


    private void decideNextScreen() {
        try{
            //check if any questions are answered for selected DP.
            List<Question> qaList = databaseHelper.selQAfrmSkillQATable(childId, selectedDP.getId(), null, 2 );
            if(qaList!=null && !qaList.isEmpty() && qaList.size()>0) {
                goToSkillsArea();

            }else{

                checkForCompSkills();
            }
        }catch (Exception e){
            Logger.logE(TAG, "decideNextScreen : ", e);
        }
    }

    private void checkForCompSkills() {
        try {
            this.selectedSkill = databaseHelper.selLeastRankUnlockedSkill(childId, selectedDP.getId(), true);//getSelSkill();
            //
            if (this.selectedSkill != null && selectedSkill.getCompleted()!=null) { //selectedSkill.getRank().equalsIgnoreCase("1")) {
                checkDBForQA();
                //callActivtSkillAPI();
            } else {
                this.selectedSkill=databaseHelper.selLeastRankUnlockedSkill(childId, selectedDP.getId(), false);
                if(this.selectedSkill != null && selectedSkill.getCompleted()!=null){
                    goToSkillsArea();
                }else{
                    ToastUtils.displayToast("Plan is locked! please try another.", context);
                    Logger.logD(TAG, "checkForCompSkills else... skill is locked");
                }

            }

        } catch (Exception e) {
            Logger.logE(TAG, "checkForCompSkills : ", e);
        }
    }




    private void checkDBForQA() {
        try {
            this.skillQstnRespModel = databaseHelper.selFromQAsTable(selectedDP.getId(), selectedSkill.getId());//from SKill questions table
            if (skillQstnRespModel.getData().getQuestions().isEmpty()) {
                callActivtSkillAPI();
            } else {
                moveToNextActivity();
            }
        } catch (Exception e) {
            Logger.logE(TAG, "checkDBForQA", e);
        }
    }


    private void goToSkillsArea() {
        try {
            this.selectedSkill=databaseHelper.selSkill(childId, selectedDP.getId());
            this.skillQstnRespModel = databaseHelper.selFromQAsTable(selectedDP.getId(), selectedSkill.getId());
            Bundle b = new Bundle();
            b.putParcelable(Constants.BUNDLE_QUESTOBJ, skillQstnRespModel);
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSKillRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            Intent i = new Intent(context, ChildReports_HistoryActivity.class);
            i.putExtras(b);
            context.startActivity(i);
        } catch (Exception e) {
            Logger.logE(TAG, "goToSkillsArea", e);
        }
    }



   /* private SkillData getSelSkill() {
        SkillData selSkill = null;
        try {
            List<SkillData> skillsList = getSKillRespModel.getData();
            for (SkillData skill : skillsList) {
                if (skill.getRank().equalsIgnoreCase("1"))
                    selSkill = skill;

            }


            for (int i = 0; i < skillsList.size(); i++) {
                for (int j = 1; j <= skillsList.size(); j++) {
                    if (skillsList.get(i).getRank().equalsIgnoreCase(String.valueOf(j)) && skillsList.get(i).getCompleted() < 100) {
                        selSkill = skillsList.get(i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Logger.logE(TAG, " DPIdClSkilAPI : ", e);
        }
        return selSkill;
    }*/

    private void callSkillsAPI(String dpId) {
        try {
            if (CheckNetwork.checkNet(context)) {
                GetDPSkillsAPI.getSkillsList(context, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, dpId, this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }
        } catch (Exception e) {
            Logger.logE(TAG, " callSkillsAPI : ", e);
        }
    }




    @Override
    public void skillResp(GetSkillsRespModel getSkillsRespModel) {
        try {
            if (getSkillsRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                ToastUtils.displayToast(getSkillsRespModel.getStatus(), context);
                // if(gotoChat){
                this.getSKillRespModel = getSkillsRespModel;
                if (!getSkillsRespModel.getData().isEmpty() && getSkillsRespModel.getData().size() > 0) {
                    // String skillId = getSkillsId();
                    databaseHelper.delskillsfromTable(childId, selectedDP.getId());
                    databaseHelper.insToSkillsTable(getSkillsRespModel, childId, selectedDP.getId());
                    checkForCompSkills();
                    //this.selectedSkill = databaseHelper.selLeastRankSkill(childId, selectedDP.getId());;//databaseHelper.selLeastRankSkill(childId, selectedDP.getId());
                    // if(selectedDP.getActive().equalsIgnoreCase("false")){

                    /*}
                    else{
                        GetSkillQuestionsAPI.callSkillQuestions(this, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId()  , this);
                    }*/
                }

            } else {
                ToastUtils.displayToast(getSkillsRespModel.getStatus(), context);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " skillResp : ", e);
        }
    }



    private void callActivtSkillAPI() {
        try {
            if (CheckNetwork.checkNet(context)) {
                ActivateDPSKillAPI.actSkill(context, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId(), this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }
        } catch (Exception e) {
            Logger.logE(TAG, " callActvtSkillAPI : ", e);
        }
    }

    @Override
    public void activateSkillResp(ActivateSkillRespModel activateSkillRespModel) {

        try {
            if (activateSkillRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                // ToastUtils.displayToast(activateSkillRespModel.getStatus(), this);
                // if(gotoChat){
                callSkillQuestionsAPI();
            } else {
                ToastUtils.displayToast(activateSkillRespModel.getStatus(), context);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " skillResp : ", e);
        }

    }

    private void callSkillQuestionsAPI() {
        try {
            if (CheckNetwork.checkNet(context)) {
                GetSkillQuestionsAPI.callSkillQuestions(context, String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(), selectedSkill.getId(), this);
            } else {
                Logger.logD(TAG, " Check Internet and try again ");
            }

        } catch (Exception e) {
            Logger.logE(TAG, "calSkilQAPI", e);
        }
    }

    @Override
    public void getSkillQuestions(GetSkillQuestionsRespModel skillQuestionsRespModel) {
        try {
            if (skillQuestionsRespModel.getStatus().equalsIgnoreCase(ResponseConstants.SUCCESS_RESPONSE)) {
                // ToastUtils.displayToast(skillQuestionsRespModel.getStatus(), this);
                // if(gotoChat){

                if (skillQuestionsRespModel.getData() != null && skillQuestionsRespModel.getData().getQuestions().size() > 0) {
                    this.skillQstnRespModel = skillQuestionsRespModel;
                    databaseHelper.delfromQATable(DbConstants.SKILLS_QSTN_TABLE, selectedDP.getId(), selectedSkill.getId());
                    databaseHelper.insToSkillQstnTable(skillQuestionsRespModel.getData().getQuestions(), childId, selectedDP.getId(), selectedSkill.getId());
                    databaseHelper.updateDPType(String.valueOf(prefs.getInt(Constants._ID, -1)), childId, selectedDP.getId(), "active");
                    moveToNextActivity();
                }


            } else {
                ToastUtils.displayToast(skillQuestionsRespModel.getStatus(), context);
            }
        } catch (Exception e) {
            Logger.logE(TAG, " skillResp : ", e);
        }
    }

    private void moveToNextActivity() {
        try {
            Bundle b = new Bundle();
            b.putParcelable(Constants.BUNDLE_QUESTOBJ, skillQstnRespModel);
            b.putParcelable(Constants.BUNDLE_SKILLSOBJ, getSKillRespModel);
            b.putParcelable(Constants.BUNDLE_SELSKILLOBJ, selectedSkill);
            b.putParcelable(Constants.BUNDLE_CHILDOBJ, childBean);
            b.putParcelable(Constants.BUNDLE_SELDPOBJ, selectedDP);
            Intent i = new Intent(context, ChatActivity.class);
            i.putExtras(b);
            context.startActivity(i);

        } catch (Exception e) {
            Logger.logE(TAG, " mvToNxActvt : ", e);
        }

    }


}
