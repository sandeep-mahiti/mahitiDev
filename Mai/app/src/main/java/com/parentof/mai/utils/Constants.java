package com.parentof.mai.utils;

import com.parentof.mai.R;

/**
 * Created by sandeep HR on 02/01/17.
 */

public class Constants {

    public static final String PROJECT = "Mai";

    //Preferences constants
    public static final String MAIPREF = "MaiPrefs";
    public static final String MOBILENUM = "mN";
    public static final String MOB_OTP_VERIFIED = "mobVrfd";
    public static final String EMAIL_OTP_VERIFIED = "emVrfd";
    public static final String SEC_QST_VERIFIED = "secQstVrfd";

    public static final String USER_DATA = "usrDat";

    public static final String USER_VERIFIED = "usrvrfd";

    public static final String MOBNUM_CHANGE = "frmNmChng";

    public static final String GENERAL = "General";
    public static final String MEDICAL = "Medical";
    public static final String ADDITIONAL = "Additional";

    public static final String NEW_USER = "newuser";
    public static final String WLCM = "Welcome, ";

    public static final String USER_FNAME = "usrFname";
    //bean const
    public static final String SEC_QST_BEAN = "secQuestions";

    public static final String SELECTED_CHILD = "slctdchld";
    public static final String UPDATE_CHILD_ID = "updtchldId";

    public static final String GET_USER_ADDITIONAL = "getUserAdditional";

    public static final String NO_INTERNET = "Please check your internet connection and try again!";

    //child details constants
    public static final String CHILD_FIRSTNAME = "firstName";
    public static final String CHILD_LASTNAME = "lastName";
    public static final String CHILD_NICKNAME = "nickName";
    public static final String CHILD_GENDER = "gender";
    public static final String CHILD_DOB = "dob";
    public static final String CHILD_BLOODGROUP = "bloodGroup";
    public static final String CHILD_SCHOOL = "school";
    public static final String CHILD_CLASS = "class";
    public static final String CHILD_SECTION = "section";
    public static final String CHILD_ROLLNUMBER = "rollNumber";

    public static final int CHILD_MIN_AGE = 1;
    public static final int CHILD_MAX_AGE = 16;
    public static final int PARENT_MIN_AGE = 16;
    public static final String SERVER_ERROR = "Server Error , Please try again!";

    public static final CharSequence[] bloodGroupArray = {"A+", "O+", "B+", "AB+", "A-", "O-", "B-", "AB-"};
    public static final CharSequence[] classesArray = {"Play home", "L.K.G/Mont 1", "U.K.G/Mont 2", "Grade 1", "Grade 2", "Grade 3",
            "Grade 4", "Grade 5", "Grade 6", "Grade 7", "Grade 8", "Grade 9", "Grade 10"};


    public static final String BIOMETRICS = "BIOMETRICS";
    public static final String CONDITION = "CONDITION";
    public static final String EYE = "EYE";
    public static final String DENTAL = "DENTAL";
    public static final String PHYSICAL_EXAMINATION = "PHYSICAL EXAMINATION";
    public static final String ANAEMIA_SCREENING = "ANAEMIA SCREENING";
    public static final String SYSTEMATIC_EXAMINATION = "SYSTEMATIC EXAMINATION";

    public static final String SUMMARY = "SUMMARY";

    public static final String _ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String BUNDLE_SKILLOBJ = "skillObj";

    public static final String BUNDLE_DPOBJ = "dpObj";
    public static final String BUNDLE_QUESTOBJ = "qusesObj";
    public static final String INTENT_QUESTOBJ = "actvtModel";
    public static final String INTENT_SKIllOBJ = "skillModel";
    public static final String BUNDLE_QSTNANSLIST = "qstnAnsList";
    public static final String BUNDLE_DPID = "dpId";
    public static final String BUNDLE_CHILDOBJ = "chldObj";
    public static final String BUNDLE_SELDPOBJ = "selDpObj";
    public static final String BUNDLE_SKILLSOBJ = "allskillsObj";
    public static final String BUNDLE_SELSKILLOBJ = "selSkillObj";
    public static final String BUNDLE_IPMODEL = "ipModel";


    public static final String INTENT_TOIPACTFROM = "toIPActScreenFrom";
    public static final String INTENT_FROMREPORTS = "fromReportsScreen";
    public static final String INTENT_FROMIPACTIVATED = "fromIPActvtdScreen";


    public static String CHILD_LIST_BEAN = "childListBean";
    public static String CHILD_ID_STR = "";
    public static int[] CHILD_GIRL_IMAGES = {R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3, R.drawable.girl_4, R.drawable.girl_5};
    public static int[] CHILD_BOY_IMAGES = {R.drawable.boy_1, R.drawable.boy_2, R.drawable.boy_3, R.drawable.boy_4, R.drawable.boy_5};
    public static int[] PARENT_IMAGES = {R.drawable.user_1, R.drawable.user_2, R.drawable.user_3, R.drawable.user_4, R.drawable.user_5};

    public static final String[] vaccinationGroups = {"BCG", "OPV 0", "Hep-B 1",

            "DTwP 1", "IPV 1", "Hep-B 2", "Hib 1", "Rotavirus 1", "PCV 1",

            "DTwP 2", "IPV 2", "Hib 2", "Rotavirus 2", "PCV 2",
            "DTwP 3", "IPV 3", "Hib 3", "Rotavirus 3", "PCV 3",
            "OPV 1", "Hep-B 3",
            "OPV 2", "MMR-1",
            "Typhoid Conjugate Vaccine",
            "Hep-A 1",
            "MMR 2", "Varicella 1", "PCV booster",
            "DTwP B1/DTaP B1", "IPV B1", "Hib B1",
            "Hep-A 2",
            "Booster of Typhoid Conjugate Vaccine",
            "DTwP B2/DTaP B2", "OPV 3", "Varicella 2", "MMR 3",
            "Tdap/Td", "HPV"};

    public static String SELECTED_CATEGORY = "selectedCategory";

    public static final String[] helpText = {"Administer these vaccines to all newborns before hospital discharge",
            "Administer these vaccines to all newborns before hospital discharge",
            "Administer these vaccines to all newborns before hospital discharge",

            "DTaP vaccine/combinations should preferably be avoided for the primary series",
            "DTaP vaccine/combinations should preferably be avoided for the primary series",
            "DTaP vaccine/combinations should preferably be avoided for the primary series",
            "DTaP vaccine/combinations should be preferred in certain specific circumstances/conditions only",
            "DTaP vaccine/combinations should be preferred in certain specific circumstances/conditions only",
            "No need of repeating/giving additional doses of whole-cell pertussis (wP) vaccine to a child who has earlier completed their primary schedule with acellular pertussis (aP) vaccine-containing products",

            "If RV1 is chosen, the first dose should be given at 10 weeks",
            "If RV1 is chosen, the first dose should be given at 10 weeks",
            "If RV1 is chosen, the first dose should be given at 10 weeks",
            "If RV1 is chosen, the first dose should be given at 10 weeks",
            "If RV1 is chosen, the first dose should be given at 10 weeks",

            "Only 2 doses of RV1 are recommended.",
            "Only 2 doses of RV1 are recommended.",
            "Only 2 doses of RV1 are recommended.",
            "If RV1 is chosen, the 2nd dose should be given at 14 weeks",
            "If RV1 is chosen, the 2nd dose should be given at 14 weeks",

            "Hepatitis-B: The final (3rd or 4th ) dose in the HepB vaccine series should be administered no earlier than age 24 weeks and at least 16 weeks after the first dose.\n",
            "Hepatitis-B: The final (3rd or 4th ) dose in the HepB vaccine series should be administered no earlier than age 24 weeks and at least 16 weeks after the first dose.\n",

            "Measles-containing vaccine ideally should not be administered before completing 270 days or 9 months of life",
            "The 2nd dose must follow in 2nd year of life; No need to give stand-alone measles vaccine",
            "Currently, two typhoid conjugate vaccines, Typbar-TCV® and PedaTyph® available in Indian market; either can be used",
            "Single dose for live attenuated H2-strain Hep-A vaccine Two doses for all inactivated Hep-A vaccines are recommended",
            "The 2nd dose must follow in 2nd year of life", "The 2nd dose must follow in 2nd year of life",
            "However, it can be given at anytime 4-8 weeks after the 1st dose",
            "The first booster (4th dose) may be administered as early as age 12 months, provided at least 6 months have elapsed since the third dose.",
            "The first booster (4th dose) may be administered as early as age 12 months, provided at least 6 months have elapsed since the third dose.",
            "1st & 2nd boosters should preferably be of DTwP",
            "Hepatitis A: 2nd dose for inactivated vaccines only",
            "A booster dose of Typhoid conjugate vaccine (TCV), if primary dose is given at 9-12 months",
            "Varicella: the 2nd dose can be given at anytime 3 months after the 1st dose.",
            "MMR: the 3rd dose is recommended at 4-6 years of age.",
            "MMR: the 3rd dose is recommended at 4-6 years of age.",
            "MMR: the 3rd dose is recommended at 4-6 years of age.", "Tdap: is preferred to Td followed by Td every 10 years",
            "HPV: Only 2 doses of either of the two HPV vaccines for adolescent/preadolescent girls aged 9-14 years;", "Only 2 doses of either of the two HPV vaccines for adolescent/preadolescent girls aged 9-14 years;", "39"};


    // Decision POInts types      String[] dpTitles={"NEW DPs","PENDING DPs","SKILLED DPs","COMPLETED DPs"};
    public static final String DECISION_POINTS = "dpObj";
    public static final String NEW_DPS = "NEW DPs";
    public static final String PENDING_DPS = "PENDING DPs";
    public static final String SKILLED_DPS = "SKILLED DPs";
    public static final String COMPLETED_DPS = "COMPLETED DPs";
    public static  String IID = "";

}
