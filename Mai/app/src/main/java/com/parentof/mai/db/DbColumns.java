package com.parentof.mai.db;

/**
 * Created by mahiti on 30/1/17.
 */
public class DbColumns {

    // Graph columns

    public static final String CHILD_ID = "childId";
    public static final String CHILD_NAME = "childName";
    public static final String CHILD_IMAGE = "childImage";
    public static final String CHILD_HEIGHT = "childHeight";
    public static final String CHILD_AGE = "childAge";
    public static final String P_ID = "pId";
    public static final String AGE_MODE = "ageMode";
    public static final String CHILD_OPTION_FLAG = "selectedOption";

    // Bio table columns
    public static final String BIO_P_ID = "pId";
    public static final String BIO_PULSE = "pulse";
    public static final String BIO_BP = "bp";
    public static final String BIO_HEIGHT = "height";
    public static final String BIO_WEIGHT = "weight";
    public static final String BIO_BMI = "bmi";
    public static final String BIO_CHILD_ID = "childId";
    public static final String BIO_OBSERVATION = "observation";

    // Condition columns
    public static final String CON_P_ID = "pId";
    public static final String CON_HAIR = "hair";
    public static final String CON_NAIL = "nail";
    public static final String CON_NOSE = "nose";
    public static final String CON_EAR = "ear";
    public static final String CON_PURE = "pure";
    public static final String CON_SKIN = "skin";
    public static final String CON_THROAT = "throat";
    public static final String CON_CHILD_ID = "childId";

    //Eye columns

    public static final String EYE_VIS_LEFT = "visualLeft";
    public static final String EYE_VIS_RIGHT = "visualRight";

    public static final String EYE_REF_LEFT = "refractiveLeft";
    public static final String EYE_REF_RIGHT = "refractivevisualRight";

    public static final String EYE_COLOR_LEFT = "colorLeft";
    public static final String EYE_COLOR_RIGHT = "colorRight";

    public static final String EYE_SQUINT_LEFT = "squintLeft";
    public static final String EYE_SQUINT_RIGHT = "squintRight";

    public static final String EYE_ALL_LEFT = "allergyLeft";
    public static final String EYE_ALL_RIGHT = "allergyRight";
    public static final String EYE_P_ID = "pId";
    public static final String EYE_CHILD_ID = "childId";

    //Dental columns
    public static final String DENTAL_P_ID = "pId";
    public static final String DENTAL_CHILD_ID = "childId";
    public static final String DENTAL_SELECTED_STRING = "dentalString";

    //Physical Columns
    public static final String ICTERUS = "icterus";
    public static final String CYANOSIS = "cyanosis";
    public static final String CLUBBING = "clubbing";
    public static final String OEDEMA = "oedema";
    public static final String SACRAT = "sacrat";
    public static final String PERI_ORBITAL = "periOrbital";
    public static final String LYMPHADENOPATHY = "lymphadenopathy";
    public static final String CERVICAL_POSTERIOR = "cervicalPosterior";
    public static final String AXILLARY = "axillary";
    public static final String OCCIPITAL = "occipital";
    public static final String CERVICAL_ANTERIOR = "cervicalAnterior";
    public static final String FPITROCHLEAR = "fpitrochlear";
    public static final String PHY_P_ID = "pId";
    public static final String PHY_CHILD_ID = "childId";

    //systematic Columns
    public static final String SYS_CARD = "cardiovascular";
    public static final String SYS_RESPIRATORY = "respiratory";
    public static final String SYS_ABDOMINAL = "abdominal";
    public static final String SYS_MUSCULOSKELETAl = "musculoskeletal";
    public static final String SYS_P_ID = "pId";
    public static final String SYS_CHILD_ID = "childId";

    /*
    *  String childId;
    int pId;
    String aSkin;
    String sTongue;
    String aSclera;
    String aLips;
    String aNailBed;*/

    public static final String ANA_CHILD_ID = "childId";
    public static final String ANA_P_ID = "pId";
   /* public static final String ANA_SKIN = "Skin";
    public static final String ANA_TONGUE = "Tongue";
    public static final String ANA_SCLERA = "Sclera";
    public static final String ANA_LIPS = "Lips";
    public static final String ANA_NAIL_NED = "Nail bed";*/
    public static final String ANA_CHECKED_STRING = "anaemia_checked";

    public static final String VACCIN_ID = "vaccineId";
    public static final String VACCINE_CATEGORY_NAME = "vaccineCategoryName";
    public static final String VACCINE_DATE = "vaccineDate";
    public static final String VACCINE_HELPTEXT = "vaccineHelpText";
    public static final String V_P_ID = "pId";
    public static final String V_CHILD_ID ="childId";
    public static final String VACCINE_NAME ="vaccineName";
    public static final String IMMUNISATION_TABLE = "immunization_table" ;


    public static final String REMINDER_TITLE = "title";
    public static final String REMINDER_LOCATION = "location";
    public static final String REMINDER_TIME = "reminder_time";
    public static final String REMINDER_DESCRIPTION = "description";
    public static final String REMINDER_HELPTEXT = "help_text";
    public static final String REMINDER_DATE = "reminder_date";
    public static final String REMINDER_CHILD_ID = "child_id";
    public static final String REMINDER_USER_ID = "user_id";
    public static final String REMINDER_Created_DATE_TIME = "created_date_time";
    public static final String REMINDER_SERVER_ID = "server_id";
    public static final String REMINDER_ID = "reminder_id";
    public static final String REMINDER_STATUS = "reminder_status";


    //Decision Points Table and columns
    public static final String DP_SINO="sino";
    public static final String DP_PID="parentId";
    public static final String DP_CID="childId";
    public static final String DP_ID="dpId";
    public static final String DP_NAME="dpName";
    public static final String DP_ACTIVE="dpActive";
    public static final String DP_COMP_PERCENT="dpCompPer";
    public static final String DP_DATE="dpCreatedDate";
    public static final String DP_COVER="dpCover";
    public static final String DP_IMAGE_SPATH="dpImgPath";
    public static final String DP_TYPE = "dpType";

    //Skills Table and columns
    public static final String S_SINO = "si_no";
    public static final String S_CID = "child_id";
    public static final String S_DPID = "dp_id";
    public static final String S_ID = "skill_id";
    public static final String S_NAME ="skill_name";
    public static final String S_RANK = "skill_rank";
    public static final String S_COVER = "skill_cover";
    public static final String S_THUMB = "skill_thumb";
    public static final String S_DESCRIPTION = "skill_description";
    public static final String S_ISLOCKED = "skill_isLocked";
    public static final String S_COMP_PERCENT = "comp_percent";
    public static final String S_QUESTIONS_LEFT = "skill_QLeft";
    public static final String S_DATE = "date";

    //Activate SKill response with Questions
    public static final String Q_SINO = "sino" ;
    public static final String Q_CID = "child_id";
    public static final String Q_DP_ID ="dp_Id";
    public static final String Q_SKILL_ID ="skill_Id";
    public static final String Q_QUESTION_ID = "q_Id";
    public static final String Q_QUESTION ="question";
    public static final String Q_ANSWER ="answer";
    public static final String Q_INDICATOR ="indicator" ;
    public static final String Q_A_DATE = "ansdate";



    // Improvement plan columns....


    public static final String IMP_DURATION = "duration";

    public static final String IMP_CAN_BE_MARKED_AS_DONE = "canBeMarkedAsDone";
     /* public static final String IMP_TASK_NAME = "taskName";
    public static final String IMP_TASK_OBJECTIVES = "taskObjectives";
    public static final String IMP_DOS = "dos";

    public static final String IMP_DONTS = "donts";
    public static final String IMP_REMINDER = "reminder";
    public static final String IMP_REMINDER_QN = "reminderQn";
    public static final String IMP_QUALIFY_QN = "qualifyingQn";*/

    //
    public static final String I_SINO = "siNo";
    public static final String I_ID = "intervention_Id";
    public static final String I_DURATION = "duration";
    public static final String I_REMINDER = "reminder";
    public static final String I_DOS = "dos";
    public static final String I_DONTS = "donts";
    public static final String I_REMINDER_QN = "reminderQn";
    public static final String I_QUALIFY_QN = "qualifyingQn";
    public static final String I_LEVEL = "level";
    public static final String I_TASK_NAME = "taskName";
    public static final String I_TASK_OBJECTIVES = "taskObjectives";
    public static final String I_TASK_STEPS="taskSteps";
    public static final String I_ACTIVE = "active";
    public static final String I_PLAY_PAUSE="playpause";
    public static final String I_SKILL_ID = "skillId";
    public static final String I_SKILL_NAME = "skillName";
    public static final String I_DP_ID = "dp_id";
    public static final String I_DP_NAME = "dpName";
    public static final String I_CHILD_ID = "child_id";
    public static final String I_USER_ID = "user_id";
    public static final String I_ACT_DTIME = "activationDTime";
    public static final String I_COMPLETE = "complete";


    public static final String IPL_SINO = "id ";
    public static final String IPL_IID = "intervention_id ";
    public static final String IPL_CID = "child_id";
    public static final String IPL_ANS = "answer";
    public static final String IPL_REM_TYPE = "reminder_type ";
    public static final String IPL_DPID = "dp_id";
    public static final String IPL_UPDATE_TIME = "updated_time ";

    /* play pause details table*/
    static final String PP_SINO="si_no";
    static final String PP_CID="child_id";
    static final String PP_IVID = "intervention_id";
    static final String PP_TVSTATUS = "intervention_status";
    static final String PP_UPDATE_TIME = "date_time" ;

    /* STatistics Detail Table */
    static final String ST_SINO = "si_no";
    static final String ST_PID = "parentId";
    static final String ST_CHILDID = "childId";
    static final String ST_DPID = "dpId";
    static final String ST_DPNAME = "dpName"; //ST_DPIMAGE
    static final String ST_DPIMAGE = "dpImage";
    static final String ST_TOTQUESTIONS = "totQstns";
    static final String ST_ANSQUESTIONS = "ansQstns";
    static final String ST_TOTCT = "totCT";
    static final String ST_ACHCT = "achCT";
    static final String ST_TOTIND = "totIndicators";
    static final String ST_ACHIND = "achIndicators";
    static final String ST_DATETIME = "dateTime";



}
