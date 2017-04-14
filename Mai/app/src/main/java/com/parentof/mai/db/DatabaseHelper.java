package com.parentof.mai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.parentof.mai.model.ChildBean;
import com.parentof.mai.model.ImprovementPlanModel.Data;
import com.parentof.mai.model.ImprovementPlanModel.HomeTaskBean;
import com.parentof.mai.model.allActivatedInterventionModel.Datum;
import com.parentof.mai.model.dayLoggedModel.DayLoggedModel;
import com.parentof.mai.model.decisionpointsmodel.ActiveDP;
import com.parentof.mai.model.decisionpointsmodel.AllDP;
import com.parentof.mai.model.decisionpointsmodel.CompletedDP;
import com.parentof.mai.model.decisionpointsmodel.DPRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.GetSkillQuestionsRespModel;
import com.parentof.mai.model.getSkillQuestionsModel.Question;
import com.parentof.mai.model.healthReportModel.AnaemiaModel;
import com.parentof.mai.model.healthReportModel.BioMetricBean;
import com.parentof.mai.model.healthReportModel.ConditionModel;
import com.parentof.mai.model.healthReportModel.DentalModel;
import com.parentof.mai.model.healthReportModel.EyeModel;
import com.parentof.mai.model.healthReportModel.PhysicalModel;
import com.parentof.mai.model.healthReportModel.SystematicModel;
import com.parentof.mai.model.reminders.ReminderModel;
import com.parentof.mai.model.skillrespmodel.GetSkillsRespModel;
import com.parentof.mai.model.skillrespmodel.SkillData;
import com.parentof.mai.model.statisticsmodel.StatisticsRespModel;
import com.parentof.mai.utils.CommonClass;
import com.parentof.mai.utils.Constants;
import com.parentof.mai.utils.Logger;
import com.parentof.mai.utils.Utility;
import com.parentof.mai.views.activity.immuniazation.ImmunizationBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahiti on 30/1/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "DBHelper";

    public DatabaseHelper(Context context) {
        super(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQueryChildTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.CHILD_TABLE + "(" + DbColumns.P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.CHILD_ID + " TEXT , " +
                DbColumns.CHILD_NAME + " TEXT, " + DbColumns.CHILD_IMAGE + " TEXT, " + DbColumns.CHILD_AGE + " INTEGER, " +
                DbColumns.AGE_MODE + " TEXT, " + DbColumns.CHILD_OPTION_FLAG + " TEXT, " +
                DbColumns.CHILD_HEIGHT + " DOUBLE " + ")";

        String createBioTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.BIO_TABLE + "(" + DbColumns.BIO_P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.BIO_CHILD_ID + " TEXT, " + DbColumns.BIO_PULSE + " TEXT, " + DbColumns.BIO_BP + " TEXT, " + DbColumns.BIO_HEIGHT + " TEXT, " +
                DbColumns.BIO_OBSERVATION + " TEXT, " +
                DbColumns.BIO_WEIGHT + " TEXT, " + DbColumns.BIO_BMI + " TEXT " + ")";

        String createConditionTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.CONDITION_TABLE + "(" + DbColumns.CON_P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.CON_SKIN + " TEXT, " + DbColumns.CON_NAIL + " TEXT, " + DbColumns.CON_NOSE + " TEXT, " +
                DbColumns.CON_HAIR + " TEXT, " + DbColumns.CON_CHILD_ID + " TEXT, " + DbColumns.CON_THROAT + " TEXT, " +
                DbColumns.CON_EAR + " TEXT, " + DbColumns.CON_PURE + " TEXT " + ")";

        String createEyeTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.EYE_TABLE + "(" + DbColumns.EYE_P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.EYE_VIS_LEFT + " TEXT, " + DbColumns.EYE_VIS_RIGHT + " TEXT, " + DbColumns.EYE_REF_LEFT + " TEXT, " +
                DbColumns.EYE_REF_RIGHT + " TEXT, " + DbColumns.EYE_CHILD_ID + " TEXT, " +
                DbColumns.EYE_SQUINT_LEFT + " TEXT, " + DbColumns.EYE_SQUINT_RIGHT + " TEXT, " +
                DbColumns.EYE_ALL_LEFT + " TEXT, " + DbColumns.EYE_ALL_RIGHT + " TEXT, " +
                DbColumns.EYE_COLOR_LEFT + " TEXT, " + DbColumns.EYE_COLOR_RIGHT + " TEXT " + ")";

        String createDentalTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.DENTAL_TABLE + "(" + DbColumns.DENTAL_P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.DENTAL_CHILD_ID + " TEXT, " + DbColumns.DENTAL_SELECTED_STRING + " TEXT " + ")";

        String createPhysicalTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.PHYSICAL_TABLE + "(" + DbColumns.PHY_P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.ICTERUS + " TEXT, " + DbColumns.CYANOSIS + " TEXT, " + DbColumns.CLUBBING + " TEXT, " +
                DbColumns.OEDEMA + " TEXT, " + DbColumns.PHY_CHILD_ID + " TEXT, " +
                DbColumns.SACRAT + " TEXT, " + DbColumns.PERI_ORBITAL + " TEXT, " +
                DbColumns.LYMPHADENOPATHY + " TEXT, " + DbColumns.CERVICAL_ANTERIOR + " TEXT, " +
                DbColumns.CERVICAL_POSTERIOR + " TEXT, " + DbColumns.AXILLARY + " TEXT, " +
                DbColumns.OCCIPITAL + " TEXT, " + DbColumns.FPITROCHLEAR + " TEXT " + ")";

        String createSysTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.SYSTEMATIC_TABLE + "(" + DbColumns.SYS_P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.SYS_CARD + " TEXT, " + DbColumns.SYS_RESPIRATORY + " TEXT, " + DbColumns.SYS_ABDOMINAL + " TEXT, " +
                DbColumns.SYS_MUSCULOSKELETAl + " TEXT, " + DbColumns.SYS_CHILD_ID + " TEXT " + ")";

        String createAnaemiaTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.ANAEMIA_TABLE + "(" + DbColumns.ANA_P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.ANA_CHILD_ID + " TEXT, " + DbColumns.ANA_CHECKED_STRING + " TEXT " + ")";

        String createImmunisationTable = "CREATE TABLE IF NOT EXISTS " + DbColumns.IMMUNISATION_TABLE + "(" + DbColumns.V_P_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.VACCIN_ID + " TEXT , " +
                DbColumns.VACCINE_CATEGORY_NAME + " TEXT, " + DbColumns.VACCINE_DATE + " TEXT, " + DbColumns.VACCINE_HELPTEXT + " TEXT, " +
                DbColumns.V_CHILD_ID + " TEXT, " + DbColumns.VACCINE_NAME + " TEXT " + ")";

        String createReminderTable = "CREATE TABLE IF NOT EXISTS " + DbConstants.REMINDER_TABLE + "(" + DbColumns.REMINDER_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DbColumns.REMINDER_USER_ID + " TEXT , " + DbColumns.REMINDER_HELPTEXT + " TEXT, " +
                DbColumns.REMINDER_LOCATION + " TEXT, " + DbColumns.REMINDER_SERVER_ID + " TEXT, " +
                DbColumns.REMINDER_CHILD_ID + " TEXT, " + DbColumns.REMINDER_TITLE + " TEXT, " +
                DbColumns.REMINDER_DATE + " DATETIME, " + DbColumns.REMINDER_STATUS + " TEXT, " +
                DbColumns.REMINDER_TIME + " DATETIME, " + DbColumns.REMINDER_DESCRIPTION + " TEXT " + ")";

        //DECISION POINTS TABLE
        String createDecisionPointsTable = "CREATE TABLE IF NOT EXISTS " +
                DbConstants.DECISION_POINTS_TABLE +
                " ( " +
                DbColumns.DP_SINO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                DbColumns.DP_PID + " TEXT , " +
                DbColumns.DP_CID + " TEXT, " +
                DbColumns.DP_ID + " TEXT, " +
                DbColumns.DP_NAME + " TEXT, " +
                DbColumns.DP_ACTIVE + " INTEGER, " +
                DbColumns.DP_COMP_PERCENT + " INTEGER, " +
                DbColumns.DP_DATE + " DATE, " +
                DbColumns.DP_TYPE + " TEXT, " +
                DbColumns.DP_COVER + " TEXT, " +
                DbColumns.DP_IMAGE_SPATH + " TEXT " +
                " ) ";

        //DECISION POINTS TABLE
        String createSkillsTable = "CREATE TABLE IF NOT EXISTS " +
                DbConstants.SKILLS_TABLE +
                " ( " +
                DbColumns.S_SINO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                DbColumns.S_CID + " TEXT, " +
                DbColumns.S_DPID + " TEXT, " +
                DbColumns.S_ID + " TEXT, " +
                DbColumns.S_NAME + " TEXT, " +
                DbColumns.S_RANK + " TEXT, " +
                DbColumns.S_COVER + " TEXT, " +
                DbColumns.S_THUMB + " TEXT, " +
                DbColumns.S_DESCRIPTION + " TEXT, " +
                DbColumns.S_ISLOCKED + " TEXT, " +
                DbColumns.S_QUESTIONS_LEFT + " INTEGER, " +
                DbColumns.S_COMP_PERCENT + " INTEGER, " +
                DbColumns.S_DATE + " TEXT " +
                " ) ";

        //DECISION POINTS TABLE
        String createSkillQuestAnsTable = "CREATE TABLE IF NOT EXISTS " +
                DbConstants.SKILLS_QSTN_TABLE +
                " ( " +
                DbColumns.Q_SINO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                DbColumns.Q_CID + " TEXT, " +
                DbColumns.Q_DP_ID + " TEXT, " +
                DbColumns.Q_SKILL_ID + " TEXT, " +
                DbColumns.Q_QUESTION_ID + " TEXT, " +
                DbColumns.Q_QUESTION + " TEXT, " +
                DbColumns.Q_ANSWER + " TEXT, " +
                DbColumns.Q_INDICATOR + " TEXT, " +
                DbColumns.Q_A_DATE + " TEXT " +
                " ) ";

        /*CREATE TABLE "intervention_plan_log" ("id" INTEGER PRIMARY KEY  NOT NULL , "intervention_id" TEXT, "child_id" TEXT, "answer" TEXT, "reminder_type" TEXT, "updated_time" DATETIME, "dp_id" TEXT)*/


        String createIPsTable = "CREATE TABLE " +
                DbConstants.IMPROVEMENT_PLAN_TABLE +
                " ( " +
                DbColumns.I_SINO + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
                DbColumns.I_ID + " TEXT NOT NULL , " +
                DbColumns.I_DURATION + " INTEGER, " +
                DbColumns.I_REMINDER + " TEXT, " +
                DbColumns.I_DOS + " TEXT, " +
                DbColumns.I_DONTS + " TEXT, " +
                DbColumns.I_REMINDER_QN + " TEXT, " +
                DbColumns.I_QUALIFY_QN + " TEXT, " +
                DbColumns.I_LEVEL + " INTEGER, " +
                DbColumns.I_TASK_NAME + " TEXT, " +
                DbColumns.I_TASK_OBJECTIVES + " TEXT, " +
                DbColumns.I_TASK_STEPS + " TEXT, " +
                DbColumns.I_ACTIVE + " TEXT, " +
                DbColumns.I_PLAY_PAUSE + " INTEGER, " +
                DbColumns.I_SKILL_ID + " TEXT, " +
                DbColumns.I_SKILL_NAME + " TEXT, " +
                DbColumns.I_DP_ID + " TEXT, " +
                DbColumns.I_DP_NAME + " TEXT, " +
                DbColumns.I_CHILD_ID + " TEXT, " +
                DbColumns.I_USER_ID + " TEXT, " +
                DbColumns.I_ACT_DTIME + " TEXT, " +
                DbColumns.I_COMPLETE + " TEXT " +
                ")";

        String createIPLogtable = " CREATE TABLE " +
                DbConstants.IP_PLAN_LOG_TABLE +
                " ( " +
                DbColumns.IPL_SINO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                DbColumns.IPL_IID + " TEXT, " +
                DbColumns.IPL_CID + " TEXT, " +
                DbColumns.IPL_ANS + " TEXT, " +
                DbColumns.IPL_REM_TYPE + " TEXT, " +
                DbColumns.IPL_DPID + " TEXT, " +
                DbColumns.IPL_UPDATE_TIME + " TEXT" +
                ")";

        String createPlayPauseDetailTable = " CREATE TABLE " +
                DbConstants.PP_DETAIL_TABLE +
                " ( " +
                DbColumns.PP_SINO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                DbColumns.PP_CID + " TEXT, " +
                DbColumns.PP_IVID + " TEXT, " +
                DbColumns.PP_TVSTATUS + " TEXT, " +
                DbColumns.PP_UPDATE_TIME + " TEXT" +
                ")";

        String createStatisticsTable = " CREATE TABLE " +
                DbConstants.STATISTICS_DETAIL_TABLE +
                " ( " +
                DbColumns.ST_SINO + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , " +
                DbColumns.ST_PID + " TEXT, " +
                DbColumns.ST_CHILDID + " TEXT, " +
                DbColumns.ST_DPID + " TEXT, " +
                DbColumns.ST_DPNAME + " TEXT, " +
                DbColumns.ST_DPIMAGE + " TEXT, " +
                DbColumns.ST_TOTQUESTIONS + " INTEGER, " +
                DbColumns.ST_ANSQUESTIONS + " INTEGER, " +
                DbColumns.ST_TOTCT + " INTEGER, " +
                DbColumns.ST_ACHCT + " INTEGER, " +
                DbColumns.ST_TOTIND + " INTEGER, " +
                DbColumns.ST_ACHIND + " INTEGER, " +
                DbColumns.ST_DATETIME + " TEXT" +
                ")";



        Logger.logD(TAG, DbConstants.CHILD_TABLE + "onCreate Main Query ==> " + createQueryChildTable);
        Logger.logD(TAG, DbConstants.CHILD_TABLE + "createBioTable Main Query ==> " + createBioTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createConditionTable  Query ==> " + createConditionTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createEyeTable  Query ==> " + createEyeTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createDentalTable  Query ==> " + createDentalTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createPhysicalTable  Query ==> " + createPhysicalTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createSysTable  Query ==> " + createSysTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createAnaemiaTable  Query ==> " + createAnaemiaTable); //createIPsTable


        Logger.logD(TAG, DbConstants.DECISION_POINTS_TABLE + " Decision POints ==> " + createDecisionPointsTable);
        Logger.logD(TAG, DbConstants.SKILLS_TABLE + " skills table ==> " + createSkillsTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createIPsTable  Query ==> " + createIPsTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createIPLogtable Query ==> " + createIPLogtable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createPlayPauseDetailTable  Query ==> " + createPlayPauseDetailTable);
        Logger.logD(TAG, DbConstants.CONDITION_TABLE + " createStatisticsTable  Query ==> " + createStatisticsTable);

        db.execSQL(createQueryChildTable);
        db.execSQL(createBioTable);
        db.execSQL(createConditionTable);
        db.execSQL(createEyeTable);
        db.execSQL(createDentalTable);
        db.execSQL(createPhysicalTable);
        db.execSQL(createSysTable);
        db.execSQL(createAnaemiaTable);
        db.execSQL(createImmunisationTable);
        db.execSQL(createReminderTable);
        db.execSQL(createDecisionPointsTable);
        db.execSQL(createSkillsTable);
        db.execSQL(createSkillQuestAnsTable);
        db.execSQL(createIPsTable);
        db.execSQL(createIPLogtable);
        db.execSQL(createPlayPauseDetailTable);
        db.execSQL(createStatisticsTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Logger.logD(TAG, " - OnUpgrade --");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.CHILD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.BIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.CONDITION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.EYE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.DENTAL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.PHYSICAL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.SYSTEMATIC_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.ANAEMIA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbColumns.IMMUNISATION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.REMINDER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.DECISION_POINTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.SKILLS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.SKILLS_QSTN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.IMPROVEMENT_PLAN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.IP_PLAN_LOG_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.PP_DETAIL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.STATISTICS_DETAIL_TABLE);
        onCreate(db); // Create tables again

    }

    public void insertChildAge(ChildBean childBean) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (childBean != null) {
                contentValues.put(DbColumns.CHILD_NAME, childBean.getChildName());
                contentValues.put(DbColumns.CHILD_ID, childBean.getChildId());
                contentValues.put(DbColumns.CHILD_OPTION_FLAG, childBean.getChildOptionFlag());
                contentValues.put(DbColumns.CHILD_AGE, childBean.getChildAge());
                contentValues.put(DbColumns.CHILD_HEIGHT, childBean.getChildHeight());
                contentValues.put(DbColumns.AGE_MODE, childBean.getAgeMode());
                Logger.logD(TAG, "Insert Child Values--> " + contentValues);
                sqLiteDatabase.insert(DbConstants.CHILD_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "Insert error--", e);
        }
    }

    public List<ChildBean> getChildAgeHeight(String childId, String option) {
        List<ChildBean> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.CHILD_TABLE + " WHERE " + DbColumns.CHILD_ID + "='" + childId + "'and " + DbColumns.CHILD_OPTION_FLAG + "='" + option + "'";
            Logger.logD(TAG, "GetChild Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ChildBean childBean = new ChildBean();
                    childBean.setChildName(cursor.getString(cursor.getColumnIndex(DbColumns.CHILD_NAME)));
                    childBean.setChildAge(cursor.getInt(cursor.getColumnIndex(DbColumns.CHILD_AGE)));
                    childBean.setChildHeight(cursor.getDouble(cursor.getColumnIndex(DbColumns.CHILD_HEIGHT)));
                    childBean.setpId(cursor.getInt(cursor.getColumnIndex(DbColumns.P_ID)));
                    childBean.setAgeMode(cursor.getString(cursor.getColumnIndex(DbColumns.AGE_MODE)));
                    childBean.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.CHILD_ID)));
                    childBean.setChildOptionFlag(cursor.getString(cursor.getColumnIndex(DbColumns.CHILD_OPTION_FLAG)));
                    list.add(childBean);
                    Logger.logD(TAG, "getChildAgeHeight -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getChildAgeHeight error--", e);
        }
        return list;
    }

    public void updateChildAgeHeight(ChildBean childBean, int id) {
        try {
            Logger.logD(TAG, " - updateChildAgeHeight --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.CHILD_NAME, childBean.getChildName());
            contentValues.put(DbColumns.CHILD_AGE, childBean.getChildAge());
            contentValues.put(DbColumns.CHILD_HEIGHT, childBean.getChildHeight());
            contentValues.put(DbColumns.AGE_MODE, childBean.getAgeMode());
            contentValues.put(DbColumns.CHILD_ID, childBean.getChildId());
            contentValues.put(DbColumns.CHILD_OPTION_FLAG, childBean.getChildOptionFlag());
            sqLiteDatabase.update(DbConstants.CHILD_TABLE, contentValues, DbColumns.P_ID + "=" + id, null);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "updateChildAgeHeight error--", e);
        }

    }


    public double getChildMaxHeight(String childId, String option) {
        double maxHeight = 0;
        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT MAX(" + DbColumns.CHILD_HEIGHT + ") as " + DbColumns.CHILD_HEIGHT + " FROM " + DbConstants.CHILD_TABLE + " WHERE " + DbColumns.CHILD_ID + "='" + childId + "'and " + DbColumns.CHILD_OPTION_FLAG + "='" + option + "'";
            Logger.logD(TAG, "getChildMaxHeight Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, "getCount getCount-->" + cursor.getCount());

            if (cursor.getCount() != 0 && cursor.moveToFirst()) {
                do {
                    maxHeight = cursor.getDouble(cursor.getColumnIndex(DbColumns.CHILD_HEIGHT));
                    Logger.logD(TAG, "Get max height-->" + cursor.getDouble(cursor.getColumnIndex(DbColumns.CHILD_HEIGHT)));
                } while (cursor.moveToNext());
            }
//sqLiteDatabase.close();
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxHeight;
    }


    public void insertBioMetricData(BioMetricBean bioMetricBean) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (bioMetricBean != null) {
                contentValues.put(DbColumns.BIO_CHILD_ID, bioMetricBean.getChildId());
                contentValues.put(DbColumns.BIO_PULSE, bioMetricBean.getPulse());
                contentValues.put(DbColumns.BIO_BP, bioMetricBean.getBp());
                contentValues.put(DbColumns.BIO_HEIGHT, bioMetricBean.getHeight());
                contentValues.put(DbColumns.BIO_WEIGHT, bioMetricBean.getWeight());
                contentValues.put(DbColumns.BIO_BMI, bioMetricBean.getBmi());
                contentValues.put(DbColumns.BIO_OBSERVATION, bioMetricBean.getObservation());
                Logger.logD(TAG, " insertBioMetricData Values--> " + contentValues);
                sqLiteDatabase.insert(DbConstants.BIO_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "insertBioMetricData error--", e);
        }
    }


    public List<BioMetricBean> getBioMetric(String childId) {
        List<BioMetricBean> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.BIO_TABLE + " WHERE " + DbColumns.BIO_CHILD_ID + "='" + childId + "'";
            Logger.logD(TAG, "GetChild Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    BioMetricBean bioMetricBean = new BioMetricBean();
                    bioMetricBean.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.BIO_CHILD_ID)));
                    bioMetricBean.setPulse(cursor.getString(cursor.getColumnIndex(DbColumns.BIO_PULSE)));
                    bioMetricBean.setBp(cursor.getString(cursor.getColumnIndex(DbColumns.BIO_BP)));
                    bioMetricBean.setHeight(cursor.getString(cursor.getColumnIndex(DbColumns.BIO_HEIGHT)));
                    bioMetricBean.setWeight(cursor.getString(cursor.getColumnIndex(DbColumns.BIO_WEIGHT)));
                    bioMetricBean.setBmi(cursor.getString(cursor.getColumnIndex(DbColumns.BIO_BMI)));
                    bioMetricBean.setObservation(cursor.getString(cursor.getColumnIndex(DbColumns.BIO_OBSERVATION)));
                    bioMetricBean.setPid(cursor.getInt(cursor.getColumnIndex(DbColumns.BIO_P_ID)));
                    list.add(bioMetricBean);
                    Logger.logD(TAG, "getBioMetric -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getBioMetric error--", e);
        }
        return list;
    }

    public void updateBioMetricBean(BioMetricBean bioMetricBean, int id) {
        try {
            Logger.logD(TAG, " - updateBioMetricBean --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.BIO_CHILD_ID, bioMetricBean.getChildId());
            contentValues.put(DbColumns.BIO_BP, bioMetricBean.getBp());
            contentValues.put(DbColumns.BIO_HEIGHT, bioMetricBean.getHeight());
            contentValues.put(DbColumns.BIO_WEIGHT, bioMetricBean.getWeight());
            contentValues.put(DbColumns.BIO_BMI, bioMetricBean.getBmi());
            contentValues.put(DbColumns.BIO_OBSERVATION, bioMetricBean.getObservation());
            Logger.logD(TAG, "updateBioMetricBean -->  " + contentValues);
            sqLiteDatabase.update(DbConstants.BIO_TABLE, contentValues, DbColumns.BIO_P_ID + "=" + id, null);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }

    }
//Condition

    public void insertCondition(ConditionModel conditionModel) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (conditionModel != null) {
                contentValues.put(DbColumns.CON_CHILD_ID, conditionModel.getConChildId());
                contentValues.put(DbColumns.CON_HAIR, conditionModel.getHair());
                contentValues.put(DbColumns.CON_SKIN, conditionModel.getSkin());
                contentValues.put(DbColumns.CON_NAIL, conditionModel.getNail());
                contentValues.put(DbColumns.CON_NOSE, conditionModel.getNose());
                contentValues.put(DbColumns.CON_EAR, conditionModel.getEar());
                contentValues.put(DbColumns.CON_PURE, conditionModel.getPureTone());
                contentValues.put(DbColumns.CON_THROAT, conditionModel.getThroat());
                Logger.logD(TAG, "insertCondition Values--> " + contentValues);
                sqLiteDatabase.insert(DbConstants.CONDITION_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "insertCondition error--", e);
        }
    }

    public List<ConditionModel> getConditionalData(String childId) {
        List<ConditionModel> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.CONDITION_TABLE + " WHERE " + DbColumns.CON_CHILD_ID + "='" + childId + "'";
            Logger.logD(TAG, "getConditionalData Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ConditionModel conditionModel = new ConditionModel();
                    conditionModel.setConChildId(cursor.getString(cursor.getColumnIndex(DbColumns.CON_CHILD_ID)));
                    conditionModel.setHair(cursor.getString(cursor.getColumnIndex(DbColumns.CON_HAIR)));
                    conditionModel.setSkin(cursor.getString(cursor.getColumnIndex(DbColumns.CON_SKIN)));
                    conditionModel.setNail(cursor.getString(cursor.getColumnIndex(DbColumns.CON_NAIL)));
                    conditionModel.setNose(cursor.getString(cursor.getColumnIndex(DbColumns.CON_NOSE)));
                    conditionModel.setEar(cursor.getString(cursor.getColumnIndex(DbColumns.CON_EAR)));
                    conditionModel.setPureTone(cursor.getString(cursor.getColumnIndex(DbColumns.CON_PURE)));
                    conditionModel.setConPid(cursor.getInt(cursor.getColumnIndex(DbColumns.CON_P_ID)));
                    conditionModel.setThroat(cursor.getString(cursor.getColumnIndex(DbColumns.CON_THROAT)));
                    list.add(conditionModel);
                    Logger.logD(TAG, "getConditionalData -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getConditionalData error--", e);
        }
        return list;
    }

    public void updateConditionalData(ConditionModel conditionModel, int id) {
        try {
            Logger.logD(TAG, " - updateBioMetricBean --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.CON_CHILD_ID, conditionModel.getConChildId());
            contentValues.put(DbColumns.CON_HAIR, conditionModel.getHair());
            contentValues.put(DbColumns.CON_SKIN, conditionModel.getSkin());
            contentValues.put(DbColumns.CON_NAIL, conditionModel.getNail());
            contentValues.put(DbColumns.CON_NOSE, conditionModel.getNose());
            contentValues.put(DbColumns.CON_EAR, conditionModel.getEar());
            contentValues.put(DbColumns.CON_PURE, conditionModel.getPureTone());
            contentValues.put(DbColumns.CON_THROAT, conditionModel.getThroat());
            Logger.logD(TAG, "updateBioMetricBean -->  " + contentValues);
            sqLiteDatabase.update(DbConstants.CONDITION_TABLE, contentValues, DbColumns.CON_P_ID + "=" + id, null);
        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }
    }


    public void insertEye(EyeModel eyeModel) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (eyeModel != null) {

                contentValues.put(DbColumns.EYE_VIS_LEFT, eyeModel.getVisLeft());
                contentValues.put(DbColumns.EYE_VIS_RIGHT, eyeModel.getVisRight());

                contentValues.put(DbColumns.EYE_REF_LEFT, eyeModel.getRefLeft());
                contentValues.put(DbColumns.EYE_REF_RIGHT, eyeModel.getRefRight());

                contentValues.put(DbColumns.EYE_COLOR_LEFT, eyeModel.getColorLeft());
                contentValues.put(DbColumns.EYE_COLOR_RIGHT, eyeModel.getColorRight());

                contentValues.put(DbColumns.EYE_SQUINT_LEFT, eyeModel.getSquintLeft());
                contentValues.put(DbColumns.EYE_SQUINT_RIGHT, eyeModel.getSquintRight());

                contentValues.put(DbColumns.EYE_ALL_LEFT, eyeModel.getAllLeft());
                contentValues.put(DbColumns.EYE_ALL_RIGHT, eyeModel.getAllRight());

                contentValues.put(DbColumns.EYE_CHILD_ID, eyeModel.getChildId());

                Logger.logD(TAG, "insertEye Values--> " + contentValues);
                sqLiteDatabase.insert(DbConstants.EYE_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "insertEye error--", e);
        }
    }

    public List<EyeModel> getEyeData(String childId) {
        List<EyeModel> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.EYE_TABLE + " WHERE " + DbColumns.EYE_CHILD_ID + "='" + childId + "'";
            Logger.logD(TAG, "getEyeData Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    EyeModel eyeModel = new EyeModel();
                    eyeModel.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_CHILD_ID)));

                    eyeModel.setVisLeft(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_VIS_LEFT)));
                    eyeModel.setVisRight(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_VIS_RIGHT)));

                    eyeModel.setRefLeft(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_REF_LEFT)));
                    eyeModel.setRefRight(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_REF_RIGHT)));

                    eyeModel.setColorLeft(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_COLOR_LEFT)));
                    eyeModel.setColorRight(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_COLOR_RIGHT)));

                    eyeModel.setSquintLeft(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_SQUINT_LEFT)));
                    eyeModel.setSquintRight(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_SQUINT_RIGHT)));

                    eyeModel.setAllLeft(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_ALL_LEFT)));
                    eyeModel.setAllRight(cursor.getString(cursor.getColumnIndex(DbColumns.EYE_ALL_RIGHT)));


                    eyeModel.setpId(cursor.getInt(cursor.getColumnIndex(DbColumns.EYE_P_ID)));

                    list.add(eyeModel);
                    Logger.logD(TAG, "getEyeData -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getEyeData error--", e);
        }
        return list;
    }

    public void updateEyeData(EyeModel eyeModel, int id) {
        try {
            Logger.logD(TAG, " - updateEyeData --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.EYE_VIS_LEFT, eyeModel.getVisLeft());
            contentValues.put(DbColumns.EYE_VIS_RIGHT, eyeModel.getVisRight());

            contentValues.put(DbColumns.EYE_REF_LEFT, eyeModel.getRefLeft());
            contentValues.put(DbColumns.EYE_REF_RIGHT, eyeModel.getRefRight());

            contentValues.put(DbColumns.EYE_COLOR_LEFT, eyeModel.getColorLeft());
            contentValues.put(DbColumns.EYE_COLOR_RIGHT, eyeModel.getColorRight());

            contentValues.put(DbColumns.EYE_SQUINT_LEFT, eyeModel.getSquintLeft());
            contentValues.put(DbColumns.EYE_SQUINT_RIGHT, eyeModel.getSquintRight());

            contentValues.put(DbColumns.EYE_ALL_LEFT, eyeModel.getAllLeft());
            contentValues.put(DbColumns.EYE_ALL_RIGHT, eyeModel.getAllRight());

            contentValues.put(DbColumns.EYE_CHILD_ID, eyeModel.getChildId());

            Logger.logD(TAG, "updateEyeData -->  " + contentValues);
            sqLiteDatabase.update(DbConstants.EYE_TABLE, contentValues, DbColumns.EYE_P_ID + "=" + id, null);
        } catch (Exception e) {
            Logger.logE(TAG, "updateEyeData error--", e);
        }
    }


    public void insertDentalData(DentalModel dentalModel) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (dentalModel != null) {
                contentValues.put(DbColumns.DENTAL_SELECTED_STRING, dentalModel.getCheckedListString());
                contentValues.put(DbColumns.DENTAL_CHILD_ID, dentalModel.getChildId());
                /*contentValues.put(DbColumns.DENTAL_P_ID, dentalModel.getpId());*/
                Logger.logD(TAG, "insertEye insertDentalData--> " + contentValues);
                sqLiteDatabase.insert(DbConstants.DENTAL_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "insertEye error--", e);
        }
    }

    public DentalModel getDentalData(String childId) {
        DentalModel dentalModel = new DentalModel();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.DENTAL_TABLE + " WHERE " + DbColumns.DENTAL_CHILD_ID + "='" + childId + "'";
            Logger.logD(TAG, "getEyeData Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    dentalModel.setCheckedListString(cursor.getString(cursor.getColumnIndex(DbColumns.DENTAL_SELECTED_STRING)));
                    dentalModel.setpId(cursor.getInt(cursor.getColumnIndex(DbColumns.DENTAL_P_ID)));
                    Logger.logD(TAG, "getDentalData -->  " + cursor.getString(cursor.getColumnIndex(DbColumns.DENTAL_SELECTED_STRING)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "getDentalData error--", e);
        }
        return dentalModel;
    }

    public void updateDentalData(DentalModel dentalModel, int id) {
        try {
            Logger.logD(TAG, " - updateEyeData --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.DENTAL_SELECTED_STRING, dentalModel.getCheckedListString());
            contentValues.put(DbColumns.DENTAL_CHILD_ID, dentalModel.getChildId());
            Logger.logD(TAG, "updateEyeData -->  " + contentValues);
            sqLiteDatabase.update(DbConstants.DENTAL_TABLE, contentValues, DbColumns.DENTAL_P_ID + "=" + id, null);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "updateEyeData error--", e);
        }
    }


    /* PHYSICAL  */

    public void insertPhysical(PhysicalModel physicalModel) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (physicalModel != null) {

                contentValues.put(DbColumns.ICTERUS, physicalModel.getIcterus());
                contentValues.put(DbColumns.CYANOSIS, physicalModel.getCyanosis());

                contentValues.put(DbColumns.CLUBBING, physicalModel.getClubbing());
                contentValues.put(DbColumns.OEDEMA, physicalModel.getOedema());

                contentValues.put(DbColumns.SACRAT, physicalModel.getSacrat());
                contentValues.put(DbColumns.PERI_ORBITAL, physicalModel.getPeriOrbital());

                contentValues.put(DbColumns.LYMPHADENOPATHY, physicalModel.getLymphadenopathy());
                contentValues.put(DbColumns.CERVICAL_ANTERIOR, physicalModel.getCervicalAnterior());

                contentValues.put(DbColumns.CERVICAL_POSTERIOR, physicalModel.getCervicalPosterior());
                contentValues.put(DbColumns.AXILLARY, physicalModel.getAxillary());

                contentValues.put(DbColumns.OCCIPITAL, physicalModel.getOccipital());
                contentValues.put(DbColumns.FPITROCHLEAR, physicalModel.getFpitrochlear());

                contentValues.put(DbColumns.PHY_CHILD_ID, physicalModel.getChildId());

                Logger.logD(TAG, "insertPhysical Values--> " + contentValues);
                sqLiteDatabase.insert(DbConstants.PHYSICAL_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "insertPhysical error--", e);
        }
    }

    public List<PhysicalModel> getPhysicalData(String childId) {
        List<PhysicalModel> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.PHYSICAL_TABLE + " WHERE " + DbColumns.PHY_CHILD_ID + "='" + childId + "'";
            Logger.logD(TAG, "getPhysicalData Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    PhysicalModel physicalModel = new PhysicalModel();
                    physicalModel.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.PHY_CHILD_ID)));

                    physicalModel.setIcterus(cursor.getString(cursor.getColumnIndex(DbColumns.ICTERUS)));
                    physicalModel.setCyanosis(cursor.getString(cursor.getColumnIndex(DbColumns.CYANOSIS)));

                    physicalModel.setClubbing(cursor.getString(cursor.getColumnIndex(DbColumns.CLUBBING)));
                    physicalModel.setOedema(cursor.getString(cursor.getColumnIndex(DbColumns.OEDEMA)));

                    physicalModel.setSacrat(cursor.getString(cursor.getColumnIndex(DbColumns.SACRAT)));
                    physicalModel.setPeriOrbital(cursor.getString(cursor.getColumnIndex(DbColumns.PERI_ORBITAL)));

                    physicalModel.setLymphadenopathy(cursor.getString(cursor.getColumnIndex(DbColumns.LYMPHADENOPATHY)));
                    physicalModel.setCervicalAnterior(cursor.getString(cursor.getColumnIndex(DbColumns.CERVICAL_ANTERIOR)));

                    physicalModel.setCervicalPosterior(cursor.getString(cursor.getColumnIndex(DbColumns.CERVICAL_POSTERIOR)));
                    physicalModel.setAxillary(cursor.getString(cursor.getColumnIndex(DbColumns.AXILLARY)));

                    physicalModel.setOccipital(cursor.getString(cursor.getColumnIndex(DbColumns.OCCIPITAL)));
                    physicalModel.setFpitrochlear(cursor.getString(cursor.getColumnIndex(DbColumns.FPITROCHLEAR)));

                    physicalModel.setpId(cursor.getInt(cursor.getColumnIndex(DbColumns.PHY_P_ID)));

                    list.add(physicalModel);
                    Logger.logD(TAG, "getPhysicalData -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getPhysicalData error--", e);
        }
        return list;
    }

    public void updatePhyData(PhysicalModel physicalModel, int id) {
        try {
            Logger.logD(TAG, " - updatePhyData --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.ICTERUS, physicalModel.getIcterus());
            contentValues.put(DbColumns.CYANOSIS, physicalModel.getCyanosis());

            contentValues.put(DbColumns.CLUBBING, physicalModel.getClubbing());
            contentValues.put(DbColumns.OEDEMA, physicalModel.getOedema());

            contentValues.put(DbColumns.SACRAT, physicalModel.getSacrat());
            contentValues.put(DbColumns.PERI_ORBITAL, physicalModel.getPeriOrbital());

            contentValues.put(DbColumns.LYMPHADENOPATHY, physicalModel.getLymphadenopathy());
            contentValues.put(DbColumns.CERVICAL_ANTERIOR, physicalModel.getCervicalAnterior());

            contentValues.put(DbColumns.CERVICAL_POSTERIOR, physicalModel.getCervicalPosterior());
            contentValues.put(DbColumns.AXILLARY, physicalModel.getAxillary());

            contentValues.put(DbColumns.OCCIPITAL, physicalModel.getOccipital());
            contentValues.put(DbColumns.FPITROCHLEAR, physicalModel.getFpitrochlear());

            contentValues.put(DbColumns.PHY_CHILD_ID, physicalModel.getChildId());

            Logger.logD(TAG, "updatePhyData -->  " + contentValues);
            sqLiteDatabase.update(DbConstants.PHYSICAL_TABLE, contentValues, DbColumns.PHY_P_ID + "=" + id, null);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "updatePhyData error--", e);
        }
    }


    /*Syatamatic...*/

    public void insertSystematicData(SystematicModel systematicModel) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (systematicModel != null) {
                contentValues.put(DbColumns.SYS_CARD, systematicModel.getCardDiovascular());
                contentValues.put(DbColumns.SYS_RESPIRATORY, systematicModel.getRespiratory());
                contentValues.put(DbColumns.SYS_ABDOMINAL, systematicModel.getAbdominal());
                contentValues.put(DbColumns.SYS_MUSCULOSKELETAl, systematicModel.getMusculoskeletal());
                contentValues.put(DbColumns.SYS_CHILD_ID, systematicModel.getChildId());
                Logger.logD(TAG, "insertSystematicData --> " + contentValues);
                sqLiteDatabase.insert(DbConstants.SYSTEMATIC_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "insertSystematicData error--", e);
        }
    }

    public List<SystematicModel> getSyData(String childId) {
        List<SystematicModel> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.SYSTEMATIC_TABLE + " WHERE " + DbColumns.SYS_CHILD_ID + "='" + childId + "'";
            Logger.logD(TAG, "getSyData Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            SystematicModel systematicModel = new SystematicModel();
            if (cursor.moveToFirst()) {
                do {
                    systematicModel.setCardDiovascular(cursor.getString(cursor.getColumnIndex(DbColumns.SYS_CARD)));
                    systematicModel.setRespiratory(cursor.getString(cursor.getColumnIndex(DbColumns.SYS_RESPIRATORY)));
                    systematicModel.setAbdominal(cursor.getString(cursor.getColumnIndex(DbColumns.SYS_ABDOMINAL)));
                    systematicModel.setMusculoskeletal(cursor.getString(cursor.getColumnIndex(DbColumns.SYS_MUSCULOSKELETAl)));
                    systematicModel.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.SYS_ABDOMINAL)));
                    systematicModel.setpId(cursor.getInt(cursor.getColumnIndex(DbColumns.SYS_P_ID)));
                    list.add(systematicModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "getSyData error--", e);
        }
        return list;
    }

    public void updateSysData(SystematicModel systematicModel, int id) {
        try {
            Logger.logD(TAG, " - updateSysData --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.SYS_CARD, systematicModel.getCardDiovascular());
            contentValues.put(DbColumns.SYS_RESPIRATORY, systematicModel.getRespiratory());
            contentValues.put(DbColumns.SYS_ABDOMINAL, systematicModel.getAbdominal());
            contentValues.put(DbColumns.SYS_MUSCULOSKELETAl, systematicModel.getMusculoskeletal());
            contentValues.put(DbColumns.SYS_CHILD_ID, systematicModel.getChildId());
            Logger.logD(TAG, "updateSysData -->  " + contentValues);
            sqLiteDatabase.update(DbConstants.SYSTEMATIC_TABLE, contentValues, DbColumns.SYS_P_ID + "=" + id, null);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "updateSysData error--", e);
        }
    }

    /*Anaemia Methods*/

    public void insertAnaemiaData(AnaemiaModel anaemiaModel) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (anaemiaModel != null) {
                contentValues.put(DbColumns.ANA_CHECKED_STRING, anaemiaModel.getAnaemiaCheckedString());
                contentValues.put(DbColumns.ANA_CHILD_ID, anaemiaModel.getChildId());
                /*contentValues.put(DbColumns.DENTAL_P_ID, dentalModel.getpId());*/
                Logger.logD(TAG, " insertAnaemiaData--> " + contentValues);
                sqLiteDatabase.insert(DbConstants.ANAEMIA_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "insertAnaemiaData error--", e);
        }
    }

    public AnaemiaModel getAnaemiaData(String childId) {
        AnaemiaModel anaemiaModel = new AnaemiaModel();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.ANAEMIA_TABLE + " WHERE " + DbColumns.ANA_CHILD_ID + "='" + childId + "'";
            Logger.logD(TAG, "getAnaemiaData Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    anaemiaModel.setAnaemiaCheckedString(cursor.getString(cursor.getColumnIndex(DbColumns.ANA_CHECKED_STRING)));
                    anaemiaModel.setpId(cursor.getInt(cursor.getColumnIndex(DbColumns.ANA_P_ID)));
                    Logger.logD(TAG, "getAnaemiaData -->  " + cursor.getString(cursor.getColumnIndex(DbColumns.ANA_CHECKED_STRING)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "getAnaemiaData error--", e);
        }
        return anaemiaModel;
    }

    public void updateAnaemiaData(AnaemiaModel anaemiaModel, int id) {
        try {
            Logger.logD(TAG, " - updateAnaemiaData --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.ANA_CHECKED_STRING, anaemiaModel.getAnaemiaCheckedString());
            contentValues.put(DbColumns.ANA_CHILD_ID, anaemiaModel.getChildId());
            Logger.logD(TAG, "updateAnaemiaData -->  " + contentValues);
            sqLiteDatabase.update(DbConstants.ANAEMIA_TABLE, contentValues, DbColumns.ANA_P_ID + "=" + id, null);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "updateAnaemiaData error--", e);
        }
    }

    public void insertImmunizationRecord(ImmunizationBean immunizationBean) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (immunizationBean != null) {
                //  contentValues.put(DbColumns.V_P_ID, immunizationBean.getpId());
                contentValues.put(DbColumns.VACCIN_ID, immunizationBean.getVaccineId());
                // contentValues.put(DbColumns.VACCINE_CATEGORY_NAME, immunizationBean.getVaccineCategoryName());
                contentValues.put(DbColumns.VACCINE_DATE, immunizationBean.getVaccineDate());
                contentValues.put(DbColumns.VACCINE_HELPTEXT, immunizationBean.getVaccineHelpText());
                contentValues.put(DbColumns.VACCINE_NAME, immunizationBean.getVaccineName());
                contentValues.put(DbColumns.V_CHILD_ID, immunizationBean.getChildId());
                Logger.logD(TAG, "insertImmunizationRecord Values--> " + contentValues);
                sqLiteDatabase.insert(DbColumns.IMMUNISATION_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection
            }
        } catch (Exception e) {
            Logger.logE(TAG, "Insert error--", e);
        }
    }

    public List<ImmunizationBean> getImmunizationRecords(String childId) {
        List<ImmunizationBean> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbColumns.IMMUNISATION_TABLE + " WHERE " + DbColumns.V_CHILD_ID + "='" + childId + "'";
            Logger.logD(TAG, "GetChild Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ImmunizationBean immunizationBean = new ImmunizationBean();
                    immunizationBean.setpId(cursor.getInt(cursor.getColumnIndex(DbColumns.V_P_ID)));
                    immunizationBean.setVaccineId(cursor.getString(cursor.getColumnIndex(DbColumns.VACCIN_ID)));
                    //  immunizationBean.setVaccineCategoryName(cursor.getString(cursor.getColumnIndex(DbColumns.VACCINE_CATEGORY_NAME)));
                    immunizationBean.setVaccineDate(cursor.getString(cursor.getColumnIndex(DbColumns.VACCINE_DATE)));
                    immunizationBean.setVaccineHelpText(cursor.getString(cursor.getColumnIndex(DbColumns.VACCINE_HELPTEXT)));
                    immunizationBean.setVaccineName(cursor.getString(cursor.getColumnIndex(DbColumns.VACCINE_NAME)));
                    immunizationBean.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.V_CHILD_ID)));
                    list.add(immunizationBean);
                    Logger.logD(TAG, "getChildAgeHeight -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getChildAgeHeight error--", e);
        }
        return list;
    }


    public List<ImmunizationBean> getImmunizationRecords() {
        List<ImmunizationBean> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbColumns.IMMUNISATION_TABLE;
            Logger.logD(TAG, "GetChild Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ImmunizationBean immunizationBean = new ImmunizationBean();
                    immunizationBean.setpId(cursor.getInt(cursor.getColumnIndex(DbColumns.V_P_ID)));
                    immunizationBean.setVaccineId(cursor.getString(cursor.getColumnIndex(DbColumns.VACCIN_ID)));
                    //  immunizationBean.setVaccineCategoryName(cursor.getString(cursor.getColumnIndex(DbColumns.VACCINE_CATEGORY_NAME)));
                    immunizationBean.setVaccineDate(cursor.getString(cursor.getColumnIndex(DbColumns.VACCINE_DATE)));
                    immunizationBean.setVaccineHelpText(cursor.getString(cursor.getColumnIndex(DbColumns.VACCINE_HELPTEXT)));
                    immunizationBean.setVaccineName(cursor.getString(cursor.getColumnIndex(DbColumns.VACCINE_NAME)));
                    immunizationBean.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.V_CHILD_ID)));
                    list.add(immunizationBean);
                    Logger.logD(TAG, "getImmunizationRecords -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getChildAgeHeight error--", e);
        }
        return list;
    }


    public void updateImmunizationRecord(ImmunizationBean immunizationBean, int id) {
        try {
            Logger.logD(TAG, " - updateChildAgeHeight --" + id);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.V_P_ID, immunizationBean.getpId());
            contentValues.put(DbColumns.VACCIN_ID, immunizationBean.getVaccineId());
            contentValues.put(DbColumns.VACCINE_CATEGORY_NAME, immunizationBean.getVaccineCategoryName());
            contentValues.put(DbColumns.VACCINE_DATE, immunizationBean.getVaccineDate());
            contentValues.put(DbColumns.VACCINE_HELPTEXT, immunizationBean.getVaccineHelpText());
            contentValues.put(DbColumns.VACCINE_NAME, immunizationBean.getVaccineName());
            sqLiteDatabase.update(DbColumns.IMMUNISATION_TABLE, contentValues, DbColumns.V_P_ID + "=" + id, null);
        } catch (Exception e) {
            Logger.logE(TAG, "updateChildAgeHeight error--", e);
        }

    }

    public void insertReminder(ReminderModel reminderModel) {
        try {

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.REMINDER_USER_ID, reminderModel.getUserId());
            contentValues.put(DbColumns.REMINDER_CHILD_ID, reminderModel.getChildId());
            contentValues.put(DbColumns.REMINDER_TITLE, reminderModel.getTitle());
            contentValues.put(DbColumns.REMINDER_DESCRIPTION, reminderModel.getDescription());
            contentValues.put(DbColumns.REMINDER_HELPTEXT, reminderModel.getHelpText());
            contentValues.put(DbColumns.REMINDER_LOCATION, reminderModel.getLocation());
            contentValues.put(DbColumns.REMINDER_DATE, reminderModel.getReminderDate());
            contentValues.put(DbColumns.REMINDER_TIME, reminderModel.getReminderTime());
            //contentValues.put(DbColumns.REMINDER_Created_DATE_TIME, reminderModel.getCreatedDateTime());
            contentValues.put(DbColumns.REMINDER_SERVER_ID, reminderModel.getCreatedDateTime());
            contentValues.put(DbColumns.REMINDER_STATUS, reminderModel.getReminderStatus());
           // contentValues.put(DbColumns.REMINDER_ID, reminderModel.getId());
            sqLiteDatabase.insert(DbConstants.REMINDER_TABLE, null, contentValues);
            Logger.logD("updating db", "insertReminder contentValues db ====>>>>" + contentValues);
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "updateChildAgeHeight error--", e);
        }
    }
    public List<ReminderModel> getAllReminders(String childId, String currentDate, String reminderStatus) {
        List<ReminderModel> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            //  String query = "SELECT * FROM " + DbConstants.REMINDER_TABLE + " where " + DbColumns.REMINDER_CHILD_ID + " = '" + childId + "' AND " + DbColumns.REMINDER_DATE + ">='" + currentDate + "' AND " + DbColumns.REMINDER_STATUS + " = '" + reminderStatus + "'";
            String query = "SELECT * FROM " + DbConstants.REMINDER_TABLE + " where " + DbColumns.REMINDER_CHILD_ID + " = '" + childId + "' AND " + DbColumns.REMINDER_DATE + ">='" + currentDate +
                    "' AND " + DbColumns.REMINDER_STATUS + " = '" + reminderStatus + "' ORDER BY datetime(" + DbColumns.REMINDER_DATE + ") ASC ";
            // String query = "SELECT * FROM " + DbConstants.REMINDER_TABLE + " where " + DbColumns.REMINDER_USER_ID + " = '" + userId + "'";
            Logger.logD(TAG, "getAllReminders Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ReminderModel model = new ReminderModel();
                    // model.setUserId(userId);
                    model.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_CHILD_ID)));
                    model.setServerId(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_SERVER_ID)));
                    model.setTitle(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_TITLE)));
                    model.setDescription(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_DESCRIPTION)));
                    model.setHelpText(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_HELPTEXT)));
                    model.setReminderTime(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_TIME)));
                    model.setReminderDate(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_DATE)));
                    //model.setCreatedDateTime(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_Created_DATE_TIME)));
                    model.setLocation(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_LOCATION)));
                    model.setReminderStatus(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_STATUS)));
                    model.setId(cursor.getInt(cursor.getColumnIndex(DbColumns.REMINDER_ID)));
                    list.add(model);
                } while (cursor.moveToNext());
            }
            Logger.logD(TAG, "getAllReminders : " + DbConstants.REMINDER_TABLE + " result : " + DatabaseUtils.dumpCursorToString(cursor));
            cursor.close();
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "Fetching All reminders error" + e.getMessage(), e);
        }
        return list;
    }



    public void deleteReminderFromTable(String cId, int reminderId) {
        int icount = 0;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "DELETE FROM " + DbConstants.REMINDER_TABLE + " WHERE " + DbColumns.REMINDER_ID + "=" + reminderId + " AND " + DbColumns.REMINDER_CHILD_ID + "='" + cId + "'";
            Logger.logD(TAG, "Del deleteReminderFromTable : " + query);
            Cursor mcursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, "deleteReminderFromTable : " + DbConstants.REMINDER_TABLE + " result : " + DatabaseUtils.dumpCursorToString(mcursor));
            mcursor.close();
        } catch (Exception e) {
            Logger.logE(TAG, "dpTableIsEmpty--", e);
        }

    }

    public List<ReminderModel> getAllRemindersStatus(String childId, String currentDate) {
        List<ReminderModel> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            String query = "SELECT * FROM " + DbConstants.REMINDER_TABLE + " where " + DbColumns.REMINDER_CHILD_ID + " = '" + childId + "' AND " + DbColumns.REMINDER_DATE + ">='" + currentDate + "'";
            // String query = "SELECT * FROM " + DbConstants.REMINDER_TABLE + " where " + DbColumns.REMINDER_USER_ID + " = '" + userId + "'";
            Logger.logD(TAG, "getAllReminders Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    ReminderModel model = new ReminderModel();
                    model.setId(cursor.getInt(cursor.getColumnIndex(DbColumns.REMINDER_ID)));
                    // model.setUserId(userId);
                    model.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_CHILD_ID)));
                    model.setServerId(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_SERVER_ID)));
                    model.setTitle(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_TITLE)));
                    model.setDescription(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_DESCRIPTION)));
                    model.setHelpText(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_HELPTEXT)));
                    model.setReminderTime(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_TIME)));
                    model.setReminderDate(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_DATE)));
                    //model.setCreatedDateTime(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_Created_DATE_TIME)));
                    model.setLocation(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_LOCATION)));
                    model.setReminderStatus(cursor.getString(cursor.getColumnIndex(DbColumns.REMINDER_STATUS)));
                    list.add(model);
                } while (cursor.moveToNext());
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "Fetching All reminders error" + e.getMessage(), e);
        }
        return list;
    }


    public void updateReminderStatus(String status, int pId) {
        Logger.logD("updating db", "updating db status====>>>>" + status + "==" + pId);
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbColumns.REMINDER_STATUS, status);
        int id = database.update(DbConstants.REMINDER_TABLE, values, DbColumns.REMINDER_ID + "=" + pId, null);
        Logger.logD("updating db", "updating db====>>>>" + id);
    }


    public void insToDPTable(DPRespModel alldpOb, String pId, String cId) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (alldpOb != null && alldpOb.getData() != null) {
               /* int activeDP=0;
                if(allDP.getActive()){
                    activeDP=1;
                }*/
                if (!alldpOb.getData().getAllDP().isEmpty() && alldpOb.getData().getAllDP().size() > 0)
                    for (AllDP allDP : alldpOb.getData().getAllDP()) {
                        contentValues.put(DbColumns.DP_PID, pId);
                        contentValues.put(DbColumns.DP_CID, cId);
                        contentValues.put(DbColumns.DP_ID, allDP.getId());
                        contentValues.put(DbColumns.DP_NAME, allDP.getName());
                        contentValues.put(DbColumns.DP_ACTIVE, allDP.getActive());
                        contentValues.put(DbColumns.DP_COMP_PERCENT, allDP.getCompPercent());
                        contentValues.put(DbColumns.DP_DATE, allDP.getCreatedDate());
                        contentValues.put(DbColumns.DP_TYPE, "all");
                        contentValues.put(DbColumns.DP_IMAGE_SPATH, allDP.getImage());
                        contentValues.put(DbColumns.DP_COVER, allDP.getCover());
                        Logger.logD(TAG, "Inserted DecisionPoint Values--> " + contentValues);
                        sqLiteDatabase.insert(DbConstants.DECISION_POINTS_TABLE, null, contentValues);
                        // Closing database connection
                    }


                if (!alldpOb.getData().getActiveDP().isEmpty() && alldpOb.getData().getActiveDP().size() > 0)
                    for (ActiveDP allDP : alldpOb.getData().getActiveDP()) {
                        contentValues.put(DbColumns.DP_PID, pId);
                        contentValues.put(DbColumns.DP_CID, cId);
                        contentValues.put(DbColumns.DP_ID, allDP.getId());
                        contentValues.put(DbColumns.DP_NAME, allDP.getName());
                        contentValues.put(DbColumns.DP_ACTIVE, allDP.getActive());
                        contentValues.put(DbColumns.DP_COMP_PERCENT, allDP.getCompPercent());
                        contentValues.put(DbColumns.DP_DATE, allDP.getCreatedDate());
                        contentValues.put(DbColumns.DP_TYPE, "active");
                        contentValues.put(DbColumns.DP_IMAGE_SPATH, allDP.getImage());
                        contentValues.put(DbColumns.DP_COVER, allDP.getCover());
                        Logger.logD(TAG, "Inserted DecisionPoint Values--> " + contentValues);
                        sqLiteDatabase.insert(DbConstants.DECISION_POINTS_TABLE, null, contentValues);
                        // Closing database connection
                    }

                if (!alldpOb.getData().getCompletedDP().isEmpty() && alldpOb.getData().getCompletedDP().size() > 0)
                    for (CompletedDP allDP : alldpOb.getData().getCompletedDP()) {
                        contentValues.put(DbColumns.DP_PID, pId);
                        contentValues.put(DbColumns.DP_CID, cId);
                        contentValues.put(DbColumns.DP_ID, allDP.getId());
                        contentValues.put(DbColumns.DP_NAME, allDP.getName());
                        contentValues.put(DbColumns.DP_ACTIVE, allDP.getActive());
                        contentValues.put(DbColumns.DP_COMP_PERCENT, allDP.getCompPercent());
                        contentValues.put(DbColumns.DP_DATE, CommonClass.getDate(allDP.getCreatedDate()));
                        contentValues.put(DbColumns.DP_TYPE, "completed");
                        contentValues.put(DbColumns.DP_IMAGE_SPATH, allDP.getImage());
                        contentValues.put(DbColumns.DP_COVER, allDP.getCover());
                        Logger.logD(TAG, "Inserted DecisionPoint Values--> " + contentValues);
                        sqLiteDatabase.insert(DbConstants.DECISION_POINTS_TABLE, null, contentValues);
                        // Closing database connection
                    }
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "Insert error--", e);
        }
    }

    public boolean isTableNotEmpty(String tableName) {
        int icount = 0;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String count = "SELECT count(*) FROM " + tableName;
            Cursor mcursor = sqLiteDatabase.rawQuery(count, null);
            if (mcursor != null && mcursor.getCount() > 0 && mcursor.moveToFirst()) {
                icount = mcursor.getInt(0);
            }
            mcursor.close();
            return icount > 0;
        } catch (Exception e) {
            Logger.logE(TAG, "dpTableIsEmpty--", e);
        }
        return icount > 0;
    }

    public void delAllfromTable(String tableName, String pId, String cId) {
        int icount = 0;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "DELETE FROM " + tableName + " WHERE " + DbColumns.DP_PID + "='" + pId + "' AND " + DbColumns.DP_CID + "='" + cId + "'";
            Logger.logD(TAG, "Del query : " + query);
            Cursor mcursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, "delTable : " + tableName + " result : " + DatabaseUtils.dumpCursorToString(mcursor));
            //icount =  mcursor.getInt(0);
            mcursor.close();

        } catch (Exception e) {
            Logger.logE(TAG, "dpTableIsEmpty--", e);
        }

    }


    public List<AllDP> selDPBasedOnTypeAll(String pId, String cId, /*String dpId,*/ String dpType) {

        List<AllDP> dpList = new ArrayList<>();

        try {
            Logger.logD(TAG, " selctig on Constraint :  \n PID  : " + pId + "\n CID : " + cId);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + pId + "' AND " + DbColumns.DP_CID + "='" + cId + "' AND " + DbColumns.DP_TYPE + "='" + dpType + "'";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    AllDP allDPObj = new AllDP();
                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/

                    allDPObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ID)));
                    allDPObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.DP_NAME)));
                    allDPObj.setActive(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ACTIVE)));
                    allDPObj.setCompPercent(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_COMP_PERCENT)));
                    allDPObj.setCreatedDate(cursor.getString(cursor.getColumnIndex(DbColumns.DP_DATE)));
                    allDPObj.setImage(cursor.getString(cursor.getColumnIndex(DbColumns.DP_IMAGE_SPATH)));
                    allDPObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.DP_COVER)));


                    dpList.add(allDPObj);
                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
            }

        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }
        return dpList;
    }

    public String selRecDateOfAllDPs(String userId, String childId, String dpType) {
        String recDate = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String count = " SELECT MAX (" + DbColumns.DP_DATE + ") FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + userId + "' AND " + DbColumns.DP_CID + "='" + childId + "' AND  " + DbColumns.DP_TYPE + "='" + dpType + "'";
            Cursor mcursor = sqLiteDatabase.rawQuery(count, null);
            if (mcursor.getCount() > 0 && mcursor.moveToFirst())
                recDate = mcursor.getString(0);
            mcursor.close();
            Logger.logD(TAG, "selRecDateOfAllDPs RecentDate -- : " + recDate);
        } catch (Exception e) {
            Logger.logE(TAG, "selRecDateOfAllDPs error--", e);
        }

        return recDate;
    }


    public List<AllDP> selNewDPsFromDB(String userID, String cId, String dpType, String lastUpdatedDate) {
        List<AllDP> dpList = new ArrayList<>();
        ;
        try {
            /*select *
                    from MyTable
            where mydate >= Datetime('2000-01-01 00:00:00');*/

            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + userID + "' AND " + DbColumns.DP_CID + "='" + cId + "' AND " + DbColumns.DP_TYPE + "='" + dpType + "' AND " + DbColumns.DP_DATE + ">     '" + lastUpdatedDate + "'";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {

                do {
                    AllDP allDPObj = new AllDP();
                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/

                    allDPObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ID)));
                    allDPObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.DP_NAME)));
                    allDPObj.setActive(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ACTIVE)));
                    allDPObj.setCompPercent(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_COMP_PERCENT)));
                    allDPObj.setCreatedDate(cursor.getString(cursor.getColumnIndex(DbColumns.DP_DATE)));
                    allDPObj.setImage(cursor.getString(cursor.getColumnIndex(DbColumns.DP_IMAGE_SPATH)));
                    allDPObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.DP_COVER)));


                    dpList.add(allDPObj);
                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();

            }
        } catch (Exception e) {
            Logger.logE(TAG, "selRecDateOfAllDPs error--", e);
        }

        return dpList;
    }


    public List<ActiveDP> selDPBasedOnTypeActive(String pId, String cId, /*String dpId,*/ String dpType) {

        List<ActiveDP> dpList = new ArrayList<>();

        try {
            Logger.logD(TAG, " selctig on Constraint :  \n PID  : " + pId + "\n CID : " + cId);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + pId + "' AND " + DbColumns.DP_CID + "='" + cId + "' AND " + DbColumns.DP_TYPE + "='" + dpType + "'";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    ActiveDP actDPObj = new ActiveDP();
                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/

                    actDPObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ID)));
                    actDPObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.DP_NAME)));
                    actDPObj.setActive(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ACTIVE)));
                    actDPObj.setCompPercent(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_COMP_PERCENT)));
                    actDPObj.setCreatedDate(cursor.getString(cursor.getColumnIndex(DbColumns.DP_DATE)));
                    actDPObj.setImage(cursor.getString(cursor.getColumnIndex(DbColumns.DP_IMAGE_SPATH)));
                    actDPObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.DP_COVER)));


                    dpList.add(actDPObj);
                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
            }

        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }
        return dpList;
    }

    public List<CompletedDP> selDPBasedOnTypeComp(String pId, String cId, /*String dpId,*/ String dpType) {

        List<CompletedDP> dpList = new ArrayList<>();

        try {
            Logger.logD(TAG, " selctig on Constraint :  \n PID  : " + pId + "\n CID : " + cId);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + pId + "' AND " + DbColumns.DP_CID + "='" + cId + "' AND " + DbColumns.DP_TYPE + "='" + dpType + "'";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    CompletedDP compDPObj = new CompletedDP();
                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/
                    compDPObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ID)));
                    compDPObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.DP_NAME)));
                    compDPObj.setActive(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ACTIVE)));
                    compDPObj.setCompPercent(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_COMP_PERCENT)));
                    compDPObj.setCreatedDate(cursor.getString(cursor.getColumnIndex(DbColumns.DP_DATE)));
                    compDPObj.setImage(cursor.getString(cursor.getColumnIndex(DbColumns.DP_IMAGE_SPATH)));
                    compDPObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.DP_COVER)));
                    dpList.add(compDPObj);
                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
            }

        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }
        return dpList;
    }

    public AllDP selDPbasedonDPID(String pId, String cId, String dpID) {


        AllDP allDPObj = new AllDP();
        try {
            Logger.logD(TAG, " selctig on Constraint :  \n PID  : " + pId + "\n CID : " + cId );
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + pId + "' AND " + DbColumns.DP_CID + "='" + cId + "' AND " + DbColumns.DP_ID + "='" + dpID + "' ";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {

                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/
                    allDPObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ID)));
                    allDPObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.DP_NAME)));
                    allDPObj.setActive(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ACTIVE)));
                    allDPObj.setCompPercent(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_COMP_PERCENT)));
                    allDPObj.setCreatedDate(cursor.getString(cursor.getColumnIndex(DbColumns.DP_DATE)));
                    allDPObj.setImage(cursor.getString(cursor.getColumnIndex(DbColumns.DP_IMAGE_SPATH)));
                    allDPObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.DP_COVER)));

                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();

            }

        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }

        return allDPObj;
    }


    public List<AllDP> selOneFromDPTable(String pId, String cId) {

        List<AllDP> allDPObjList = new ArrayList<>();

        try {
            Logger.logD(TAG, " selctig on Constraint :  \n PID  : " + pId + "\n CID : " + cId );
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + pId + "' AND " + DbColumns.DP_CID + "='" + cId + "' AND  "+DbColumns.DP_TYPE +"= 'active'";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {

                do {
                    AllDP allDPObj = new AllDP();
                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/
                    allDPObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ID)));
                    allDPObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.DP_NAME)));
                    allDPObj.setActive(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ACTIVE)));
                    allDPObj.setCompPercent(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_COMP_PERCENT)));
                    allDPObj.setCreatedDate(cursor.getString(cursor.getColumnIndex(DbColumns.DP_DATE)));
                    allDPObj.setImage(cursor.getString(cursor.getColumnIndex(DbColumns.DP_IMAGE_SPATH)));
                    allDPObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.DP_COVER)));

                    allDPObjList.add(allDPObj);
                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
            }

        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }

        return allDPObjList;
    }

   /* public DPRespModel selForChildFromDPTable(String pId, String childId) {
        List<AllDP> list = new ArrayList<>();
        AllDP allDPObj = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query = "SELECT * FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + pId + "' AND "+DbColumns.DP_CID + "='" + childId + "'";
            Logger.logD(TAG, "getConditionalData Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    allDPObj = new AllDP();
                    *//*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*//*

                    allDPObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ID)));
                    allDPObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.DP_NAME)));
                    allDPObj.setActive(cursor.getString(cursor.getColumnIndex(DbColumns.DP_ACTIVE)));
                    allDPObj.setCompPercent(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_COMP_PERCENT)));
                    allDPObj.setCreatedDate(cursor.getString(cursor.getColumnIndex(DbColumns.DP_DATE)));
                    allDPObj.setImage(cursor.getString(cursor.getColumnIndex(DbColumns.DP_IMAGE_SPATH)));
                    list.add(allDPObj);
                    Logger.logD(TAG, "getConditionalData -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getConditionalData error--", e);
        }
        return list;
    }*/


    public void insToSkillsTable(GetSkillsRespModel skillObjList, String cId, String dpId) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (skillObjList != null && skillObjList.getData() != null) {
                if (!skillObjList.getData().isEmpty() && skillObjList.getData().size() > 0)
                    for (SkillData skillObj : skillObjList.getData()) {

                        contentValues.put(DbColumns.S_CID, cId);
                        contentValues.put(DbColumns.S_DPID, dpId);
                        contentValues.put(DbColumns.S_ID, skillObj.getId());
                        contentValues.put(DbColumns.S_NAME, skillObj.getName());
                        contentValues.put(DbColumns.S_RANK, skillObj.getRank());
                        contentValues.put(DbColumns.S_ISLOCKED, skillObj.getIsLocked());
                        contentValues.put(DbColumns.S_COVER, skillObj.getCover());
                        contentValues.put(DbColumns.S_THUMB, skillObj.getThumb());
                        contentValues.put(DbColumns.S_DESCRIPTION, skillObj.getDescription());
                        contentValues.put(DbColumns.S_QUESTIONS_LEFT, skillObj.getQuestionsLeft());
                        contentValues.put(DbColumns.S_COMP_PERCENT, skillObj.getCompleted()); //S_NOOFQUESTIONS_LEFT
                        contentValues.put(DbColumns.S_DATE, getCurrentDate());

                        Logger.logD(TAG, "Inserted Skills Values--> " + contentValues);
                        sqLiteDatabase.insert(DbConstants.SKILLS_TABLE, null, contentValues);
                        // Closing database connection
                    }

            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "Insert error--", e);
        }
    }

    public void delskillsfromTable(String cId, String dpId) {
        int icount = 0;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "DELETE FROM " + DbConstants.SKILLS_TABLE + " WHERE " + DbColumns.S_CID + "='" + cId + "' AND " + DbColumns.S_DPID + "='" + dpId + "'";
            Logger.logD(TAG, "Del query : " + query);
            Cursor mcursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, "delTable : " + DbConstants.SKILLS_TABLE + " result : " + DatabaseUtils.dumpCursorToString(mcursor));
            //icount =  mcursor.getInt(0);
            mcursor.close();

        } catch (Exception e) {
            Logger.logE(TAG, "dpTableIsEmpty--", e);
        }

    }

    public SkillData selLeastRankUnlockedSkill(String childId, String selectedDPId, boolean i) {
        List<SkillData> skillObjList = new ArrayList<>();
        SkillData retSkillObj = new SkillData();
        String query = null;
        try {

            Logger.logD(TAG, " selLeastRankUnlockedSkill selctig on Constraint :  \n  " + "\n CID : " + childId + "\n DPID : " + selectedDPId);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            if(i)
             query = "SELECT * FROM " + DbConstants.SKILLS_TABLE + " WHERE " + DbColumns.S_CID + " = '" + childId + "' AND " + DbColumns.S_DPID + " = '" + selectedDPId + "' AND " + DbColumns.S_COMP_PERCENT + "  <100   AND " + DbColumns.S_ISLOCKED + "= 'false' ORDER BY "+DbColumns.S_RANK+" ASC LIMIT 1;" ; /*+ DbColumns.S_RANK + " = (select MIN( '"+ DbColumns.S_RANK+"') FROM " + DbConstants.SKILLS_TABLE*/
            else
                query = "SELECT * FROM " + DbConstants.SKILLS_TABLE + " WHERE " + DbColumns.S_CID + " = '" + childId + "' AND " + DbColumns.S_DPID + " = '" + selectedDPId + "' AND " + DbColumns.S_ISLOCKED + "= 'false' ORDER BY "+DbColumns.S_RANK+" ASC LIMIT 1;" ; /*+ DbColumns.S_RANK + " = (select MIN( '"+ DbColumns.S_RANK+"') FROM " + DbConstants.SKILLS_TABLE*/
            Logger.logD(TAG, " selLeastRankUnlockedSkill QUERY : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, " selLeastRankUnlockedSkill selecting skills : " + DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.moveToFirst()) {


                do {
                    SkillData skillObj = new SkillData();
                /*boolean active=false;
                if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                    active=true;*/

                    skillObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.S_ID)));
                    skillObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.S_NAME)));
                    skillObj.setRank(cursor.getString(cursor.getColumnIndex(DbColumns.S_RANK)));
                    skillObj.setIsLocked(cursor.getString(cursor.getColumnIndex(DbColumns.S_ISLOCKED)));
                    skillObj.setCompleted(cursor.getInt(cursor.getColumnIndex(DbColumns.S_COMP_PERCENT)));
                    skillObj.setDescription(cursor.getString(cursor.getColumnIndex(DbColumns.S_DESCRIPTION)));
                    skillObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.S_COVER)));
                    skillObj.setQuestionsLeft(cursor.getInt(cursor.getColumnIndex(DbColumns.S_QUESTIONS_LEFT)));
                    skillObj.setThumb(cursor.getString(cursor.getColumnIndex(DbColumns.S_THUMB)));


                    skillObjList.add(skillObj);
                } while (cursor.moveToNext());
                cursor.close();
                Collections.sort(skillObjList);
                retSkillObj = skillObjList.get(0);
                cursor.close();
                sqLiteDatabase.close();
            }

            return retSkillObj;
        } catch (Exception e) {
            Logger.logE(TAG, "selLeastRankSkill", e);
        }
        return retSkillObj;
    }


    public SkillData selSkill(String cId, String dpId) {
        SkillData skillObj = new SkillData();
        try{
            Logger.logD(TAG, " selSkill  on Constraint :  \n CID  : " + cId + "\n DPID : " + dpId);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.SKILLS_TABLE + " WHERE " + DbColumns.S_CID + "='" + cId + "' AND " + DbColumns.S_DPID + "='" + dpId + "'  AND " + DbColumns.S_ISLOCKED + "='false' ORDER BY "+DbColumns.S_RANK+" ASC LIMIT 1"  ;
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, " selSkill  cursor : " + DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.moveToFirst()) {


                do {

                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/

                    skillObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.S_ID)));
                    skillObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.S_NAME)));
                    skillObj.setRank(cursor.getString(cursor.getColumnIndex(DbColumns.S_RANK)));
                    skillObj.setIsLocked(cursor.getString(cursor.getColumnIndex(DbColumns.S_ISLOCKED)));
                    skillObj.setCompleted(cursor.getInt(cursor.getColumnIndex(DbColumns.S_COMP_PERCENT)));
                    skillObj.setDescription(cursor.getString(cursor.getColumnIndex(DbColumns.S_DESCRIPTION)));
                    skillObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.S_COVER)));
                    skillObj.setQuestionsLeft(cursor.getInt(cursor.getColumnIndex(DbColumns.S_QUESTIONS_LEFT)));
                    skillObj.setThumb(cursor.getString(cursor.getColumnIndex(DbColumns.S_THUMB)));



                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
            }

        }catch (Exception e){
            Logger.logE(TAG, "selSkill : ", e);
        }

        return skillObj;
    }

    public int updateSkillsCompPer(String cId, String dpId, String skillId, int comppercent) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbColumns.S_COMP_PERCENT, comppercent);
        Logger.logD(TAG, "updateAnswertoDB \n DPID :" + dpId + " \n skillId " + skillId + " \n cId : " + cId);
        String condtion = DbColumns.S_CID + "= '" + cId + "' AND " + DbColumns.S_DPID + "= '" + dpId + "' AND " + DbColumns.S_ID + "='" + skillId + "'";
        int insertedRecord = database.update(DbConstants.SKILLS_TABLE, values, condtion, null);
        Logger.logD(TAG, "record inserted : " + insertedRecord);
        return insertedRecord;
    }

    public String selSkillNameFromSKills(String childId, String dpId, String skillId) {
        String icount = "";
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String count = "SELECT " + DbColumns.S_NAME + " FROM " + DbConstants.SKILLS_TABLE + " WHERE " + DbColumns.S_CID + " = '" + childId + "' AND " + DbColumns.S_DPID + " = '" + dpId + "' AND  " + DbColumns.S_ID + " = '" + skillId + "'";
            Cursor mcursor = sqLiteDatabase.rawQuery(count, null);
            if (mcursor != null && mcursor.moveToFirst())
                icount = mcursor.getString(0);
            mcursor.close();
            return icount;
        } catch (Exception e) {
            Logger.logE(TAG, "dpTableIsEmpty--", e);
        }
        return icount;
    }

    public GetSkillsRespModel selAllFromSkillsTable(String cId, String dpId) {

        GetSkillsRespModel getSkillsRespModel = new GetSkillsRespModel();
        List<SkillData> skillObjList = new ArrayList<>();

        try {
            Logger.logD(TAG, " selctig on Constraint :  \n CID  : " + cId + "\n DPID : " + dpId);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.SKILLS_TABLE + " WHERE " + DbColumns.S_CID + "='" + cId + "' AND " + DbColumns.S_DPID + "='" + dpId + "'";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, " selecting all skills cursor : " + DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.moveToFirst()) {


                do {
                    SkillData skillObj = new SkillData();
                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/

                    skillObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.S_ID)));
                    skillObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.S_NAME)));
                    skillObj.setRank(cursor.getString(cursor.getColumnIndex(DbColumns.S_RANK)));
                    skillObj.setIsLocked(cursor.getString(cursor.getColumnIndex(DbColumns.S_ISLOCKED)));
                    skillObj.setCompleted(cursor.getInt(cursor.getColumnIndex(DbColumns.S_COMP_PERCENT)));
                    skillObj.setDescription(cursor.getString(cursor.getColumnIndex(DbColumns.S_DESCRIPTION)));
                    skillObj.setCover(cursor.getString(cursor.getColumnIndex(DbColumns.S_COVER)));
                    skillObj.setQuestionsLeft(cursor.getInt(cursor.getColumnIndex(DbColumns.S_QUESTIONS_LEFT)));
                    skillObj.setThumb(cursor.getString(cursor.getColumnIndex(DbColumns.S_THUMB)));


                    skillObjList.add(skillObj);
                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
            }

            getSkillsRespModel.setData(skillObjList);

        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }
        return getSkillsRespModel;

    }


    public void insToSkillQstnTable(List<Question> qstAnsModel, String childId, String dpId, String skillId) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (!qstAnsModel.isEmpty() && qstAnsModel.size() > 0) {
                for (Question qstnObj : qstAnsModel) {

                    contentValues.put(DbColumns.Q_CID, childId);
                    contentValues.put(DbColumns.Q_DP_ID, dpId);
                    contentValues.put(DbColumns.Q_SKILL_ID, skillId);
                    contentValues.put(DbColumns.Q_QUESTION_ID, qstnObj.getId());
                    //StringUtils stringUtils = new StringUtils();
                    //String qstn=stringUtils.replaceLabel(childBean.getChild(), selectedDP.getName(), selectedSkill.getName(), this, skillQuestionsRespModel.getData().getQuestions().get(qstnCount).getQuestion())
                    contentValues.put(DbColumns.Q_QUESTION, qstnObj.getQuestion());
                    contentValues.put(DbColumns.Q_ANSWER, qstnObj.getAnswer());
                    contentValues.put(DbColumns.Q_INDICATOR, qstnObj.getIndicator());
                    contentValues.put(DbColumns.Q_A_DATE, getCurrentDate());

                    Logger.logD(TAG, "Inserted SKill Questions Values--> " + contentValues.toString());
                    sqLiteDatabase.insert(DbConstants.SKILLS_QSTN_TABLE, null, contentValues);
                    // Closing database connection
                }
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "Insert error--", e);
        }
    }

    public GetSkillQuestionsRespModel selFromQAsTable(String dpId, String skillId) {

        GetSkillQuestionsRespModel getSkillsQARespModel = new GetSkillQuestionsRespModel();
        List<Question> qstAnsModel = new ArrayList<>();

        try {
            Logger.logD(TAG, " selctig on Constraint :  \n DPID  : " + dpId + "\n SKILLID : " + skillId);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.SKILLS_QSTN_TABLE + " WHERE " + DbColumns.Q_DP_ID + "='" + dpId + "' AND " + DbColumns.Q_SKILL_ID + "='" + skillId + "'";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {


                do {
                    Question questionObj = new Question();
                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/

                    questionObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.Q_QUESTION_ID)));
                    questionObj.setQuestion(cursor.getString(cursor.getColumnIndex(DbColumns.Q_QUESTION)));
                    questionObj.setAnswer(cursor.getString(cursor.getColumnIndex(DbColumns.Q_ANSWER)));
                    questionObj.setIndicator(cursor.getString(cursor.getColumnIndex(DbColumns.Q_INDICATOR)));


                    qstAnsModel.add(questionObj);
                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
            }

            com.parentof.mai.model.getSkillQuestionsModel.Data dataObj = new com.parentof.mai.model.getSkillQuestionsModel.Data();
            dataObj.setQuestions(qstAnsModel);
            getSkillsQARespModel.setData(dataObj);

        } catch (Exception e) {
            Logger.logE(TAG, "selFromQAsTable error--", e);
        }
        return getSkillsQARespModel;

    }



   /* public int totNoOfQstns(String tableName, String dpId , String skillId){
        int icount=0;
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String count = "SELECT count(*) FROM "+tableName+" WHERE "+ DbColumns.Q_DP_ID + "='" + dpId + "' AND " + DbColumns.Q_SKILL_ID + "='" + skillId+"'";
            Cursor mcursor = sqLiteDatabase.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            mcursor.close();
            return icount ;
        }catch (Exception e){
            Logger.logE(TAG, "dpTableIsEmpty--", e);
        }
        return icount ;
    }*/

    public int noOfQstnsAnswered(String dpId, String skillId) {
        int icount = 0;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String count = "SELECT count(*) FROM " + DbConstants.SKILLS_QSTN_TABLE + " WHERE " + DbColumns.Q_DP_ID + "='" + dpId + "' AND " + DbColumns.Q_SKILL_ID + "='" + skillId + "' AND ( " + DbColumns.Q_ANSWER + " IS NOT NULL" + " OR " + DbColumns.Q_ANSWER + "!='' )";
            Cursor mcursor = sqLiteDatabase.rawQuery(count, null);
            mcursor.moveToFirst();
            icount = mcursor.getInt(0);
            mcursor.close();
            return icount;
        } catch (Exception e) {
            Logger.logE(TAG, "dpTableIsEmpty--", e);
        }
        return icount;
    }


    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    private String getCurrentDateOnly() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }

    public void delfromQATable(String tableName, String dpId, String skId) {
        int icount = 0;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "DELETE FROM " + tableName + " WHERE " + DbColumns.Q_DP_ID + "='" + dpId + "' AND " + DbColumns.Q_SKILL_ID + "='" + skId + "'";
            Logger.logD(TAG, "Del query : " + query);
            Cursor mcursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, "delTable : " + tableName + " result : " + DatabaseUtils.dumpCursorToString(mcursor));
            //icount =  mcursor.getInt(0);
            mcursor.close();

        } catch (Exception e) {
            Logger.logE(TAG, "dpTableIsEmpty--", e);
        }

    }

    public List<Question> selQAfrmSkillQATable(String childId, String dpID, String skillId, int fromChat) {
        List<Question> list = new ArrayList<>();

        try {
            String query = null;
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            switch (fromChat) {
                case 1:
                    query = "SELECT * FROM " + DbConstants.SKILLS_QSTN_TABLE + " WHERE " + DbColumns.Q_DP_ID + "='" + dpID + "' AND " + DbColumns.Q_SKILL_ID + "='" + skillId + "' AND " + DbColumns.Q_ANSWER + " IS NULL ";
                    break;
                case 2:
                    query = "SELECT * FROM " + DbConstants.SKILLS_QSTN_TABLE + " WHERE " + DbColumns.Q_DP_ID + "='" + dpID + "' AND " + DbColumns.Q_CID + "='" + childId + "' AND " + DbColumns.Q_ANSWER + " IS NOT NULL ";
                    break;
                case 3:
                    query = "SELECT * FROM " + DbConstants.SKILLS_QSTN_TABLE + " WHERE " + DbColumns.Q_DP_ID + "='" + dpID + "' AND " + DbColumns.Q_CID + "='" + childId + "' ";
                    break;
                default:
                    break;
            }

            Logger.logD(TAG, "getConditionalData Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    Question skillQAObj = new Question();
                   /* boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/
                    skillQAObj.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.Q_CID)));
                    skillQAObj.setDpId(cursor.getString(cursor.getColumnIndex(DbColumns.Q_DP_ID)));
                    skillQAObj.setSkillId(cursor.getString(cursor.getColumnIndex(DbColumns.Q_SKILL_ID)));
                    skillQAObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.Q_QUESTION_ID)));
                    skillQAObj.setQuestion(cursor.getString(cursor.getColumnIndex(DbColumns.Q_QUESTION)));
                    skillQAObj.setAnswer(cursor.getString(cursor.getColumnIndex(DbColumns.Q_ANSWER)));
                    skillQAObj.setIndicator(cursor.getString(cursor.getColumnIndex(DbColumns.Q_INDICATOR)));
                    list.add(skillQAObj);
                    Logger.logD(TAG, "getConditionalData -->  " + list.size());
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "getConditionalData error--", e);
        }
        return list;
    }


    public int updateAnsToSkilQstnTab(String dpId, String skillId, String qId, String ans) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbColumns.Q_ANSWER, ans);
        values.put(DbColumns.Q_A_DATE, getCurrentDate());
        Logger.logD(TAG, "updateAnswertoDB \n DPID :" + dpId + " \n skillId " + skillId + " \n QuestionId : " + qId);
        String condtion = DbColumns.Q_DP_ID + "= '" + dpId + "' AND " + DbColumns.Q_SKILL_ID + "= '" + skillId + "' AND " + DbColumns.Q_QUESTION_ID + "='" + qId + "'";
        int insertedRecord = database.update(DbConstants.SKILLS_QSTN_TABLE, values, condtion, null);
        Logger.logD(TAG, "record inserted : " + insertedRecord);
        return insertedRecord;
    }

    public SkillData selOneSkillQstns(String dpId, String skillId) {

        SkillData skillObj = null;

        try {
            Logger.logD(TAG, " selctig on Constraint :  \nDPPID  : " + dpId + "\n SKILLID : " + skillId);
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT * FROM " + DbConstants.SKILLS_QSTN_TABLE + " WHERE " + DbColumns.Q_DP_ID + "='" + dpId + "' AND " + DbColumns.Q_SKILL_ID + "='" + skillId + "'";
            Logger.logD(TAG, " Query : ---> " + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                skillObj = new SkillData();
                do {

                    /*boolean active=false;
                    if(cursor.getInt(cursor.getColumnIndex(DbColumns.DP_ACTIVE))==1)
                        active=true;*/

                    skillObj.setId(cursor.getString(cursor.getColumnIndex(DbColumns.S_ID)));
                    skillObj.setName(cursor.getString(cursor.getColumnIndex(DbColumns.S_NAME)));
                    skillObj.setRank(cursor.getString(cursor.getColumnIndex(DbColumns.S_RANK)));
                    skillObj.setCompleted(cursor.getInt(cursor.getColumnIndex(DbColumns.S_COMP_PERCENT)));


                } while (cursor.moveToNext());
                cursor.close();
                sqLiteDatabase.close();
            }

        } catch (Exception e) {
            Logger.logE(TAG, "updateBioMetricBean error--", e);
        }
        return skillObj;
    }


    public void insImprovementPlanTable(Data data, String userId, String childId, AllDP selectedDP, SkillData selectedSkill) { // Message improvement plan....
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            if (data != null) {
                contentValues.put(DbColumns.I_ID, data.getId());
                contentValues.put(DbColumns.I_DURATION, data.getDuration());
                contentValues.put(DbColumns.I_DOS, Utility.serialize(data.getDos()));
                contentValues.put(DbColumns.I_DONTS, Utility.serialize(data.getDonts()));
                contentValues.put(DbColumns.I_REMINDER_QN, data.getReminderQn());
                contentValues.put(DbColumns.I_QUALIFY_QN, data.getQualifyingQn());
                contentValues.put(DbColumns.I_LEVEL, data.getLevel());
                //contentValues.put(DbColumns.IMP_DOS, data.getDos());

                contentValues.put(DbColumns.I_TASK_NAME, data.getTaskName());
                contentValues.put(DbColumns.I_TASK_OBJECTIVES, data.getTaskObjectives());
                contentValues.put(DbColumns.I_TASK_STEPS, Utility.serialize(data.getTaskSteps()));
                contentValues.put(DbColumns.I_ACTIVE, String.valueOf(data.getCanBeMarkedAsDone()));
                contentValues.put(DbColumns.I_PLAY_PAUSE, "Play");
                contentValues.put(DbColumns.I_SKILL_ID, selectedSkill.getId());
                contentValues.put(DbColumns.I_SKILL_NAME, selectedSkill.getName());
                contentValues.put(DbColumns.I_DP_ID, selectedDP.getId());
                contentValues.put(DbColumns.I_DP_NAME, selectedDP.getName());
                contentValues.put(DbColumns.I_CHILD_ID, childId);
                contentValues.put(DbColumns.I_USER_ID, userId);
                contentValues.put(DbColumns.I_REMINDER, data.getReminder());
                contentValues.put(DbColumns.I_ACT_DTIME, getCurrentDateOnly());
                contentValues.put(DbColumns.I_COMPLETE, "1"); //status 1 is for IP started and not completed
                Logger.logD(TAG, "insertImprovementPlanTable Values--> " + contentValues);
                long updated = sqLiteDatabase.insert(DbConstants.IMPROVEMENT_PLAN_TABLE, null, contentValues);
                Logger.logD(TAG, "insertImprovementPlanTable  : " + updated);
            }
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "insImprvmntPlanTab-- ", e);
        }
    }

    public void insertListImprovementPlanTable(List<Datum> datum, String userId, String childId) { // Message improvement plan....
        try {
            ContentValues contentValues = new ContentValues();
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            //  for (Datum data : datum) {
            for (int i = 0; i < datum.size(); i++) {
                Datum data = datum.get(i);
                contentValues.put(DbColumns.I_ID, data.getId());
                contentValues.put(DbColumns.I_DURATION, data.getDuration());
                contentValues.put(DbColumns.I_DOS, Utility.serialize(data.getDos()));
                contentValues.put(DbColumns.I_DONTS, Utility.serialize(data.getDonts()));
                contentValues.put(DbColumns.I_REMINDER_QN, data.getReminderQn());
                contentValues.put(DbColumns.I_QUALIFY_QN, data.getQualifyingQn());
                contentValues.put(DbColumns.I_LEVEL, data.getLevel());
                //contentValues.put(DbColumns.IMP_DOS, data.getDos());
                Logger.logD(Constants.PROJECT, "task Name-->" + data.getTaskName());
                Logger.logD(Constants.PROJECT, "task getTaskObjectives-->" + data.getTaskObjectives());
                contentValues.put(DbColumns.I_TASK_NAME, data.getTaskName());
                contentValues.put(DbColumns.I_TASK_OBJECTIVES, data.getTaskObjectives());
                contentValues.put(DbColumns.I_TASK_STEPS, Utility.serialize(data.getTaskSteps()));
                contentValues.put(DbColumns.I_ACTIVE, String.valueOf(data.getCanBeMarkedAsDone()));
                contentValues.put(DbColumns.I_PLAY_PAUSE, "Play");
                contentValues.put(DbColumns.I_SKILL_ID, data.getSkillId());
                contentValues.put(DbColumns.I_SKILL_NAME, data.getSkillName());
                contentValues.put(DbColumns.I_DP_ID, data.getDpId());
                contentValues.put(DbColumns.I_DP_NAME, data.getDpName());
                contentValues.put(DbColumns.I_CHILD_ID, childId);
                contentValues.put(DbColumns.I_USER_ID, userId);
                contentValues.put(DbColumns.I_REMINDER, data.getReminder());
                contentValues.put(DbColumns.I_ACT_DTIME, getCurrentDateOnly());
                contentValues.put(DbColumns.I_COMPLETE, "1"); //status 1 is for IP started and not completed
                Logger.logD(TAG, "insertListImprovementPlanTable Values--> " + contentValues);
                Logger.logD(TAG, " Values--> " + getStatusToUpdateInsert(childId, data.getId()));
                boolean isAvaliable = getStatusToUpdateInsert(childId, data.getId());
                if (isAvaliable) {
                    String condtion = DbColumns.I_ID + " = '" + data.getId() + "' AND " + DbColumns.I_CHILD_ID + " = '" + childId + "'";
                    int updated = sqLiteDatabase.update(DbConstants.IMPROVEMENT_PLAN_TABLE, contentValues, condtion, null);
                    Logger.logD(TAG, "updated record  : " + updated);
                } else {
                    long updated = sqLiteDatabase.insert(DbConstants.IMPROVEMENT_PLAN_TABLE, null, contentValues);
                    Logger.logD(TAG, "insert record  : " + updated);

                }
                //  }

            }
        } catch (Exception e) {
            Logger.logE(TAG, "insImprvmntPlanTab-- ", e);
        }
    }

    public boolean getStatusToUpdateInsert(String childId, String iId) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String query;
            query = "SELECT * FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_ID + "='" + iId + "' ";
            Logger.logD(TAG, "getStatusToUpdateInsert Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                if (iId.equals(cursor.getString(cursor.getColumnIndex(DbColumns.I_ID)))) {
                    return true;
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public long updateCompletionStatus(String childId, String interventionId, int status) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DbColumns.I_COMPLETE, status);
            String condtion = DbColumns.I_ID + " = '" + interventionId + "' AND " + DbColumns.I_CHILD_ID + " = '" + childId + "'";
            int insertedRecord = database.update(DbConstants.IMPROVEMENT_PLAN_TABLE, values, condtion, null);
            Logger.logD(TAG, "updateCompletionStatus record inserted : " + insertedRecord);
            return insertedRecord;

        } catch (Exception e) {
            Logger.logE(TAG, "updateCompletionStatus--", e);
        }
        return 0;
    }


    public Data selPlanFromIPTable(String childId, String dpID, String skillId) {
        //List<Question> list = new ArrayList<>();
        Data ipData = new Data();
        try {
            String query;
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            query = "SELECT * FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_DP_ID + "='" + dpID + "' AND " + DbColumns.I_SKILL_ID + "='" + skillId + "' AND " + DbColumns.I_COMPLETE + " ='1'";
            // query = "SELECT * FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId+"' AND "  + DbColumns.I_DP_ID + "='" + dpID + "' AND " + DbColumns.I_SKILL_ID + "='" + skillId + "' AND " + DbColumns.I_ACTIVE + " = '"+aTrue+"'";

            //query = "SELECT * FROM " + DbConstants.SKILLS_QSTN_TABLE + " WHERE " + DbColumns.Q_DP_ID + "='" + dpID + "' AND " + DbColumns.Q_SKILL_ID + "='" + skillId + "' AND " + DbColumns.Q_ANSWER + " IS NOT NULL ";


            Logger.logD(TAG, "selPlanFromIPTable Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {

                ipData.setId(cursor.getString(cursor.getColumnIndex(DbColumns.I_ID)));
                ipData.setDuration(cursor.getInt(cursor.getColumnIndex(DbColumns.I_DURATION)));
                ipData.setDos(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_DOS)))));
                ipData.setDonts(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_DONTS)))));
                ipData.setReminderQn(cursor.getString(cursor.getColumnIndex(DbColumns.I_REMINDER_QN)));
                ipData.setQualifyingQn(cursor.getString(cursor.getColumnIndex(DbColumns.I_QUALIFY_QN)));
                ipData.setLevel(cursor.getInt(cursor.getColumnIndex(DbColumns.I_LEVEL)));
                ipData.setTaskName(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_NAME)));
                ipData.setTaskObjectives(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_OBJECTIVES)));
                ipData.setTaskSteps(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_STEPS)))));
                ipData.setReminder(cursor.getString(cursor.getColumnIndex(DbColumns.I_REMINDER)));
                ipData.setCanBeMarkedAsDone(cursor.getString(cursor.getColumnIndex(DbColumns.I_ACTIVE)));
                ipData.setIsPaused(cursor.getString(cursor.getColumnIndex(DbColumns.I_PLAY_PAUSE)));

            }
            Logger.logD(TAG, "selPlanFromIPTable -->  " + DatabaseUtils.dumpCursorToString(cursor));
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "selPlanFromIPTable error--", e);
        }
        return ipData;
    }


    public List<HomeTaskBean> getChildImprovementPlan(String childId, String interventionId) {
        HomeTaskBean homeTaskBean = null;
        List<HomeTaskBean> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            String query = "SELECT * FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_COMPLETE + "='" + "1" + "' AND " + DbColumns.I_ID + "='" + interventionId + "'";
            Logger.logD(Constants.PROJECT, "ImprovementQuery-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(Constants.PROJECT, "ImprovementQuery getCount-->" + cursor.getCount());
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    Data data = new Data();
                    homeTaskBean = new HomeTaskBean();
                    homeTaskBean.setUserId(cursor.getString(cursor.getColumnIndex(DbColumns.I_USER_ID)));
                    homeTaskBean.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.I_CHILD_ID)));
                    homeTaskBean.setSkillId(cursor.getString(cursor.getColumnIndex(DbColumns.I_SKILL_ID)));
                    homeTaskBean.setDpId(cursor.getString(cursor.getColumnIndex(DbColumns.I_DP_ID)));

                    data.setId(cursor.getString(cursor.getColumnIndex(DbColumns.I_ID)));
                    data.setDuration(cursor.getInt(cursor.getColumnIndex(DbColumns.I_DURATION)));
                    data.setDos(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_DOS)))));
                    data.setDonts(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_DONTS)))));

                    data.setReminderQn(cursor.getString(cursor.getColumnIndex(DbColumns.I_REMINDER_QN)));
                    data.setQualifyingQn(cursor.getString(cursor.getColumnIndex(DbColumns.I_QUALIFY_QN)));
                    data.setLevel(cursor.getInt(cursor.getColumnIndex(DbColumns.I_LEVEL)));
                    data.setTaskName(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_NAME)));
                    data.setReminder(cursor.getString(cursor.getColumnIndex(DbColumns.I_REMINDER)));
                    data.setTaskObjectives(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_OBJECTIVES)));
                    data.setTaskSteps(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_STEPS)))));
                    homeTaskBean.setData(data);
                    homeTaskBean.setDpName(cursor.getString(cursor.getColumnIndex(DbColumns.I_DP_NAME)));
                    homeTaskBean.setSkillName(cursor.getString(cursor.getColumnIndex(DbColumns.I_SKILL_NAME)));
                    homeTaskBean.setActivationDate(cursor.getString(cursor.getColumnIndex(DbColumns.I_ACT_DTIME)));

                    //   homeTaskBean.getData().set(cursor.getString(cursor.getColumnIndex(DbColumns.I_ACTIVE)));
                    //    homeTaskBean.getData().set(cursor.getString(cursor.getColumnIndex(DbColumns.I_PLAY_PAUSE)));
                    list.add(homeTaskBean);

                } while (cursor.moveToNext());
                cursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<HomeTaskBean> getImprovementPlanForIps(String childId) {
        HomeTaskBean homeTaskBean = null;
        List<HomeTaskBean> list = new ArrayList<>();
        try {
            String query="";
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

              query = "SELECT * FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_COMPLETE + "='" + "1" + "'";
             Logger.logD(Constants.PROJECT, "getImprovementPlanForIps-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(Constants.PROJECT, "getImprovementPlanForIps getCount-->" + cursor.getCount());
            Data data = null;
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    homeTaskBean = new HomeTaskBean();
                    data = new Data();
                    homeTaskBean.setUserId(cursor.getString(cursor.getColumnIndex(DbColumns.I_USER_ID)));
                    homeTaskBean.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.I_CHILD_ID)));
                    homeTaskBean.setSkillId(cursor.getString(cursor.getColumnIndex(DbColumns.I_SKILL_ID)));
                    homeTaskBean.setDpId(cursor.getString(cursor.getColumnIndex(DbColumns.I_DP_ID)));

                    data.setId(cursor.getString(cursor.getColumnIndex(DbColumns.I_ID)));
                    data.setDuration(cursor.getInt(cursor.getColumnIndex(DbColumns.I_DURATION)));
                    data.setDos(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_DOS)))));
                    data.setDonts(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_DONTS)))));

                    data.setReminderQn(cursor.getString(cursor.getColumnIndex(DbColumns.I_REMINDER_QN)));
                    data.setQualifyingQn(cursor.getString(cursor.getColumnIndex(DbColumns.I_QUALIFY_QN)));
                    data.setLevel(cursor.getInt(cursor.getColumnIndex(DbColumns.I_LEVEL)));
                    data.setTaskName(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_NAME)));
                    data.setReminder(cursor.getString(cursor.getColumnIndex(DbColumns.I_REMINDER)));
                    data.setTaskObjectives(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_OBJECTIVES)));
                    data.setTaskSteps(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_STEPS)))));
                    homeTaskBean.setData(data);
                    homeTaskBean.setDpName(cursor.getString(cursor.getColumnIndex(DbColumns.I_DP_NAME)));
                    homeTaskBean.setSkillName(cursor.getString(cursor.getColumnIndex(DbColumns.I_SKILL_NAME)));
                    homeTaskBean.setActivationDate(cursor.getString(cursor.getColumnIndex(DbColumns.I_ACT_DTIME)));
                    list.add(homeTaskBean);
                    Logger.logD(Constants.PROJECT, "getImprovementPlanForIps name-->" + cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_NAME)));
                    // Logger.logD(Constants.PROJECT, "Cur--" +list.size()+"--"+DatabaseUtils.dumpCursorToString(cursor));

                } while (cursor.moveToNext());
                cursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG, "getImprovementPlanForIps --", e);
        }
        return list;
    }

    public List<HomeTaskBean> getCcompletedIPsForActivity(String childId) {
        HomeTaskBean homeTaskBean = null;
        List<HomeTaskBean> list = new ArrayList<>();
        try {

            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            String query = "SELECT * FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_COMPLETE + "='" + "3" + "'";
            Logger.logD(Constants.PROJECT, "getImprovementPlanForIps-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(Constants.PROJECT, "getImprovementPlanForIps getCount-->" + cursor.getCount());
            Data data = null;
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    homeTaskBean = new HomeTaskBean();
                    data = new Data();
                    homeTaskBean.setUserId(cursor.getString(cursor.getColumnIndex(DbColumns.I_USER_ID)));
                    homeTaskBean.setChildId(cursor.getString(cursor.getColumnIndex(DbColumns.I_CHILD_ID)));
                    homeTaskBean.setSkillId(cursor.getString(cursor.getColumnIndex(DbColumns.I_SKILL_ID)));
                    homeTaskBean.setDpId(cursor.getString(cursor.getColumnIndex(DbColumns.I_DP_ID)));

                    data.setId(cursor.getString(cursor.getColumnIndex(DbColumns.I_ID)));
                    data.setDuration(cursor.getInt(cursor.getColumnIndex(DbColumns.I_DURATION)));
                    data.setDos(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_DOS)))));
                    data.setDonts(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_DONTS)))));

                    data.setReminderQn(cursor.getString(cursor.getColumnIndex(DbColumns.I_REMINDER_QN)));
                    data.setQualifyingQn(cursor.getString(cursor.getColumnIndex(DbColumns.I_QUALIFY_QN)));
                    data.setLevel(cursor.getInt(cursor.getColumnIndex(DbColumns.I_LEVEL)));
                    data.setTaskName(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_NAME)));
                    data.setReminder(cursor.getString(cursor.getColumnIndex(DbColumns.I_REMINDER)));
                    data.setTaskObjectives(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_OBJECTIVES)));
                    data.setTaskSteps(Arrays.asList(Utility.derialize(cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_STEPS)))));
                    homeTaskBean.setData(data);
                    homeTaskBean.setDpName(cursor.getString(cursor.getColumnIndex(DbColumns.I_DP_NAME)));
                    homeTaskBean.setSkillName(cursor.getString(cursor.getColumnIndex(DbColumns.I_SKILL_NAME)));
                    homeTaskBean.setActivationDate(cursor.getString(cursor.getColumnIndex(DbColumns.I_ACT_DTIME)));
                    list.add(homeTaskBean);
                    Logger.logD(Constants.PROJECT, "getImprovementPlanForIps name-->" + cursor.getString(cursor.getColumnIndex(DbColumns.I_TASK_NAME)));
                    // Logger.logD(Constants.PROJECT, "Cur--" +list.size()+"--"+DatabaseUtils.dumpCursorToString(cursor));

                } while (cursor.moveToNext());
                cursor.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.logE(TAG, "getImprovementPlanForIps --", e);
        }
        return list;
    }


    private List<String> stringToList(String str) {
        List<String> stringList = null;
        try {
            String del = ".,";
            //  str=str.replace(". \",",del);
            Logger.logD(TAG, "stringToList --- >  " + " Str = " + str);
            if (str != null && !str.equals("")) {
                stringList = new ArrayList<>(Arrays.asList(str.split(del)));
            }
        } catch (Exception e) {
            Logger.logE(TAG, "stringToList--", e);
        }
        Logger.logD(TAG, "stringToList --- >  " + " final String List = " + stringList);
        return stringList;
    }


    public void delFromIPTable(String cId, String interventionId) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "DELETE FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + cId + "' AND " + DbColumns.I_ID + "='" + interventionId + "'";
            Logger.logD(TAG, "Del query : " + query);
            Cursor mcursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, "delTable : " + DbConstants.IMPROVEMENT_PLAN_TABLE + " result : " + DatabaseUtils.dumpCursorToString(mcursor));
            //icount =  mcursor.getInt(0);
            mcursor.close();

        } catch (Exception e) {
            Logger.logE(TAG, "delFromIPTable-- ", e);
        }
    }

    public void updateIPPlayStatus(String childId, String interventionId, String playStatus) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DbColumns.I_PLAY_PAUSE, playStatus);
            String condtion = DbColumns.I_ID + " = '" + interventionId + "' AND " + DbColumns.I_CHILD_ID + " = '" + childId + "'";
            int insertedRecord = database.update(DbConstants.IMPROVEMENT_PLAN_TABLE, values, condtion, null);
            Logger.logD(TAG, "updated play/pause Status record updated : " + insertedRecord);


        } catch (Exception e) {
            Logger.logE(TAG, "updateIPPlayStatus", e);
        }

    }

    public boolean checkFor3ActiveInterventions(String childId) {
        int icount = 0;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String count = "SELECT count(*) FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_COMPLETE + " = '1' ";
            Cursor mcursor = sqLiteDatabase.rawQuery(count, null);
            if (mcursor != null && mcursor.getCount() > 0 && mcursor.moveToFirst()) {
                icount = mcursor.getInt(0);
            }
            mcursor.close();

            return icount >= 3;
        } catch (Exception e) {
            Logger.logE(TAG, "checkForActiveInterventions--", e);
        }
        return false;
    }

    public boolean anyIPForThisSkill(String childId, String skillId) {
        int icount = 0;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "SELECT count(*) FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_SKILL_ID + "='" + skillId + "' AND " + DbColumns.I_COMPLETE + " ='1'";
            // String query = "SELECT count(*) FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_SKILL_ID + "='" + skillId+"'";
            Logger.logD(TAG, " anyIPForThisSkill ,  ---  query---> : " + query);
            Cursor mcursor = sqLiteDatabase.rawQuery(query, null);
            if (mcursor != null && mcursor.getCount() > 0 && mcursor.moveToFirst()) {
                icount = mcursor.getInt(0);
            }
            mcursor.close();
            return icount > 0;


        } catch (Exception e) {
            Logger.logE(TAG, "anyIPForThisSkill--", e);
        }
        return false;
    }


    public void insIPLogTable(DayLoggedModel dayLoggedModel) { // Message improvement plan....
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            // if (data != null) {

            contentValues.put(DbColumns.IPL_IID, dayLoggedModel.getiId());
            contentValues.put(DbColumns.IPL_CID, dayLoggedModel.getChildId());
            contentValues.put(DbColumns.IPL_ANS, dayLoggedModel.getAns());
            contentValues.put(DbColumns.IPL_REM_TYPE, dayLoggedModel.getTypeRem());
            contentValues.put(DbColumns.IPL_DPID, dayLoggedModel.getDpId());
            contentValues.put(DbColumns.IPL_UPDATE_TIME, dayLoggedModel.getCurrentDate());

            Logger.logD(TAG, "insIPLogTable Values--> " + contentValues);
            sqLiteDatabase.insert(DbConstants.IP_PLAN_LOG_TABLE, null, contentValues);
            //}
            sqLiteDatabase.close();
        } catch (Exception e) {
            Logger.logE(TAG, "insIPLogTable -- ", e);
        }
    }

    public List<DayLoggedModel> getIpLogTable(String childId, String iId) {
        DayLoggedModel dayLoggedModel = null;
        List<DayLoggedModel> list = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            //  dayLoggedModel = new DayLoggedModel();
            String query = "SELECT * FROM " + DbConstants.IP_PLAN_LOG_TABLE + " WHERE " + DbColumns.IPL_CID + "='" + childId + "' AND " + DbColumns.IPL_IID + "='" + iId + "'";
            Logger.logD(Constants.PROJECT, "getIpLogTable-->" + query);

            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(Constants.PROJECT, "getIpLogTable cursor count -->" + cursor.getCount());
            Logger.logD(TAG, " result : " + DatabaseUtils.dumpCursorToString(cursor));
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    dayLoggedModel = new DayLoggedModel();

                    dayLoggedModel.setChildId(cursor.getString(cursor.getColumnIndex("child_id")));
                    dayLoggedModel.setTypeRem(cursor.getString(cursor.getColumnIndex("reminder_type")));
                    dayLoggedModel.setDpId(cursor.getString(cursor.getColumnIndex("dp_id")));
                    dayLoggedModel.setCurrentDate(cursor.getString(cursor.getColumnIndex("updated_time")));
                    dayLoggedModel.setAns(cursor.getString(cursor.getColumnIndex("answer")));
                    dayLoggedModel.setiId(cursor.getString(cursor.getColumnIndex("id")));
                    list.add(dayLoggedModel);
                } while (cursor.moveToNext());
                cursor.close();
            }

        } catch (Exception e) {
            Logger.logE(TAG, "getIpLogTable-- ", e);
        }
        return list;
    }

   /* public void updateIPplayPause(String value){
        try{
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DbColumns.Q_ANSWER,ans);
            values.put(DbColumns.Q_A_DATE, getCurrentDate());
            Logger.logD(TAG, "updateAnswertoDB \n DPID :" + dpId + " \n skillId "+skillId+" \n QuestionId : " +qId);
            String condtion=DbColumns.Q_DP_ID+"= '"+dpId+"' AND "+DbColumns.Q_SKILL_ID+"= '"+skillId+"' AND "+DbColumns.Q_QUESTION_ID+"='"+qId+"'";
            int insertedRecord = database.update(DbConstants.SKILLS_QSTN_TABLE, values,condtion, null );
            Logger.logD(TAG,"record inserted : "+insertedRecord);
            return insertedRecord;

        }catch (Exception e ){
            Logger.logE(TAG, "updateIPplayPause--", e);
        }
    }*/

    public List<String> getIpsList(String childId) {
        List<String> interventionId = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String queryIntervention = "SELECT " + DbColumns.I_ID + " FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId + "' AND " + DbColumns.I_COMPLETE + " = '1'";
            //String queryIntervention = "SELECT "+DbColumns.I_ID+" FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId+"' AND " + DbColumns.I_COMPLETE + " = '1'";
            Cursor cursor = sqLiteDatabase.rawQuery(queryIntervention, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                do {
                    interventionId.add(cursor.getString(cursor.getColumnIndex(DbColumns.I_ID)));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Logger.logE(TAG, "getIpsList-- ", e);
        }
        return interventionId;
    }

    public int updateDPType(String pId, String childId, String dpId, String typeToUpdate) {
        try {

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DbColumns.DP_TYPE, typeToUpdate);
            String condtion = DbColumns.DP_PID + " = '" + pId + "' AND " + DbColumns.DP_CID + " = '" + childId + "' AND " + DbColumns.DP_ID + " = '" + dpId + "'";
            int insertedRecord = database.update(DbConstants.DECISION_POINTS_TABLE, values, condtion, null);
            Logger.logD(TAG, "update DP type : " + insertedRecord);
            return insertedRecord;

        } catch (Exception e) {
            Logger.logE(TAG, "updateDPType--", e);
        }

        return 0;
    }


    public boolean selDPisActive(String pId, String childId, AllDP selectedDP) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String getDPTypeQuery = "SELECT " + DbColumns.DP_TYPE + " FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_PID + "='" + pId + "' AND " + DbColumns.DP_CID + " = '" + childId + "' AND " + DbColumns.DP_ID + " = '" + selectedDP.getId() + "'";
            //String queryIntervention = "SELECT "+DbColumns.I_ID+" FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId+"' AND " + DbColumns.I_COMPLETE + " = '1'";
            Cursor cursor = sqLiteDatabase.rawQuery(getDPTypeQuery, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                String dpType = cursor.getString(cursor.getColumnIndex(DbColumns.DP_TYPE));
                if (dpType.equalsIgnoreCase("active"))
                    return true;
            }

        } catch (Exception e) {
            Logger.logE(TAG, "selDPisActive -- ", e);
        }
        return false;
    }


    public void insertPPStatus(String childId, String intrventionId, String playpause) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
                contentValues.put(DbColumns.PP_CID, childId);
                contentValues.put(DbColumns.PP_IVID, intrventionId);
                contentValues.put(DbColumns.PP_TVSTATUS, playpause);
                contentValues.put(DbColumns.PP_UPDATE_TIME, getCurrentDate());
                Logger.logD(TAG, " insertPPStatus --> " + contentValues);
                sqLiteDatabase.insert(DbConstants.PP_DETAIL_TABLE, null, contentValues);
                sqLiteDatabase.close();  // Closing database connection

        } catch (Exception e) {
            Logger.logE(TAG, "insertPPStatus  error--", e);
        }
    }

    public String selDPNameFromTable(String dpId){
        try{
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String getDPNameQuery = "SELECT " + DbColumns.DP_NAME + " FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_ID + "='" + dpId + "' ";
            Logger.logD(TAG, " selDPNameFromTable :  "+getDPNameQuery);
            //String queryIntervention = "SELECT "+DbColumns.I_ID+" FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId+"' AND " + DbColumns.I_COMPLETE + " = '1'";
            Cursor cursor = sqLiteDatabase.rawQuery(getDPNameQuery, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(DbColumns.DP_NAME));

            }
            Logger.logD(TAG, " selDPNameFromTable :  "+DatabaseUtils.dumpCursorToString(cursor));

        }catch (Exception e){
            Logger.logE(TAG, "selDPNameFromTable  error--", e);
        }
        return "";
    }


    public String selDPCoverFromTable(String dpId) {

        try{
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            String getDPNameQuery = "SELECT " + DbColumns.DP_COVER + " FROM " + DbConstants.DECISION_POINTS_TABLE + " WHERE " + DbColumns.DP_ID + "='" + dpId + "' ";
            Logger.logD(TAG, " selDPNameFromTable :  "+getDPNameQuery);
            //String queryIntervention = "SELECT "+DbColumns.I_ID+" FROM " + DbConstants.IMPROVEMENT_PLAN_TABLE + " WHERE " + DbColumns.I_CHILD_ID + "='" + childId+"' AND " + DbColumns.I_COMPLETE + " = '1'";
            Cursor cursor = sqLiteDatabase.rawQuery(getDPNameQuery, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(DbColumns.DP_COVER));

            }
            Logger.logD(TAG, " selDPNameFromTable :  "+DatabaseUtils.dumpCursorToString(cursor));


        }catch (Exception e){
            Logger.logE(TAG, "", e);
        }
        return "";
    }


    public void insertStatisticsDetails(StatisticsRespModel statisticsRespModel, String pId, String childId, String dpId, String dpName, String dpCoverImage) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbColumns.ST_PID, pId);
            contentValues.put(DbColumns.ST_CHILDID, childId);
            contentValues.put(DbColumns.ST_DPID, dpId);
            contentValues.put(DbColumns.ST_DPNAME, dpName);
            contentValues.put(DbColumns.ST_DPIMAGE, dpCoverImage);
            contentValues.put(DbColumns.ST_TOTQUESTIONS, statisticsRespModel.getData().getTotalQuestion());
            contentValues.put(DbColumns.ST_ANSQUESTIONS, statisticsRespModel.getData().getAnsweredQuestion());
            contentValues.put(DbColumns.ST_TOTCT, statisticsRespModel.getData().getCtTotal());
            contentValues.put(DbColumns.ST_ACHCT, statisticsRespModel.getData().getCtAchieved());
            contentValues.put(DbColumns.ST_TOTIND, statisticsRespModel.getData().getTotalIndicator());
            contentValues.put(DbColumns.ST_ACHIND, statisticsRespModel.getData().getAchievedIndicator());
            contentValues.put(DbColumns.ST_DATETIME, getCurrentDate());
            Logger.logD(TAG, "insertStatisticsDetails --> " + contentValues);
            sqLiteDatabase.insert(DbConstants.STATISTICS_DETAIL_TABLE, null, contentValues);
            sqLiteDatabase.close();  // Closing database connection

        } catch (Exception e) {
            Logger.logE(TAG, "IninsertStatisticsDetails error--", e);
        }
    }

    public void delFromStatisticsTable(String pId, String cId, String dpID) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String query = "DELETE FROM " + DbConstants.STATISTICS_DETAIL_TABLE + " WHERE "+DbColumns.ST_PID + "= '"+pId + "' AND "  + DbColumns.ST_CHILDID + "='" + cId + "' AND " + DbColumns.ST_DPID + "='" + dpID + "'";
            Logger.logD(TAG, "delFromStatisticsTable query : " + query);
            Cursor mcursor = sqLiteDatabase.rawQuery(query, null);
            Logger.logD(TAG, "delFromStatisticsTable : " + DbConstants.IMPROVEMENT_PLAN_TABLE + " result : " + DatabaseUtils.dumpCursorToString(mcursor));
            //icount =  mcursor.getInt(0);
            mcursor.close();

        } catch (Exception e) {
            Logger.logE(TAG, "delFromStatisticsTable-- ", e);
        }
    }

    public List<com.parentof.mai.model.statisticsmodel.Data> selFromStatisticsTable(String pId, String childId /*, String dpID*/) {
        //List<Question> list = new ArrayList<>();
        List<com.parentof.mai.model.statisticsmodel.Data> stData = new ArrayList<>();
        try {
            String query;
            SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

            query = "SELECT * FROM " + DbConstants.STATISTICS_DETAIL_TABLE + " WHERE " + DbColumns.ST_PID + "='" + pId + "' AND " + DbColumns.ST_CHILDID + "='" + childId + "' ";


            Logger.logD(TAG, "selFromStatisticsTable Query-->" + query);
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    com.parentof.mai.model.statisticsmodel.Data dataItem = new com.parentof.mai.model.statisticsmodel.Data();
                    dataItem.setDpId(cursor.getString(cursor.getColumnIndex(DbColumns.ST_DPID)));
                    dataItem.setDpName(cursor.getString(cursor.getColumnIndex(DbColumns.ST_DPNAME)));
                    dataItem.setDpImage(cursor.getString(cursor.getColumnIndex(DbColumns.ST_DPIMAGE)));
                    dataItem.setTotalQuestion(cursor.getInt(cursor.getColumnIndex(DbColumns.ST_TOTQUESTIONS)));
                    dataItem.setAnsweredQuestion(cursor.getInt(cursor.getColumnIndex(DbColumns.ST_ANSQUESTIONS)));
                    dataItem.setCtTotal(cursor.getInt(cursor.getColumnIndex(DbColumns.ST_TOTCT)));
                    dataItem.setCtAchieved(cursor.getInt(cursor.getColumnIndex(DbColumns.ST_ACHCT)));
                    dataItem.setTotalIndicator(cursor.getInt(cursor.getColumnIndex(DbColumns.ST_TOTIND)));
                    dataItem.setAchievedIndicator(cursor.getInt(cursor.getColumnIndex(DbColumns.ST_ACHIND)));
                    stData.add(dataItem);
                }while (cursor.moveToNext());


            }
            Logger.logD(TAG, "selFromStatisticsTable -->  " + DatabaseUtils.dumpCursorToString(cursor));
            cursor.close();
            sqLiteDatabase.close();

        } catch (Exception e) {
            Logger.logE(TAG, "selFromStatisticsTable error--", e);
        }
        return stData;
    }


}
