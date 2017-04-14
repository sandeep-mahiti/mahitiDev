package com.parentof.mai.utils;

/**
 * Created by sandeep HR on 24/02/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.parentof.mai.model.getchildrenmodel.Child_;

import java.util.Calendar;

/**
 * Created by mahiti on 24/2/17.
 */

public class StringUtils {
    String[] replaceNames = {"{Child_First_Name}", "{Child_Last_Name}",
            "{Child_Nick_name}", "{Child_Age}",
            "{Parent_First_Name}", "{Parent_Last_Name}", "{Parent_Relation}",
            "{DP_Name}", "{SA_Name}", "{Child_Gender<him/her>}", "{Child_Gender<his/her>}",
            "{Child_Gender<he/she>}", "{Child_Gender<himself/herself>}", "{Activity_Name}"};

    public String replaceLabel(Child_ child, String dpName, String skName, Context context, String displayText, String activityName) {
        try {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
            displayText = displayText.replace(replaceNames[0], child.getFirstName());
            displayText = displayText.replace(replaceNames[1], child.getLastName());
            if (child.getNickName() != null)
                displayText = displayText.replace(replaceNames[2], child.getNickName());

    /*if(child.getDob()!=null && child.getDob().contains("T"))
        displayText = displayText.replace(replaceNames[3], child.getDob()); //TODO actual age has to be setted*/

            if (!pref.getString(PreferencesConstants.FIRST_NAME, "").isEmpty())
                displayText = displayText.replace(replaceNames[4], pref.getString(PreferencesConstants.FIRST_NAME, ""));
            if (!pref.getString(PreferencesConstants.LAST_NAME, "").isEmpty())
                displayText = displayText.replace(replaceNames[5], pref.getString(PreferencesConstants.LAST_NAME, ""));
            if (!pref.getString(PreferencesConstants.RELATION, "").isEmpty())
                displayText = displayText.replace(replaceNames[6], pref.getString(PreferencesConstants.RELATION, ""));

            displayText = displayText.replace(replaceNames[7], dpName);
            displayText = displayText.replace(replaceNames[8], skName);
            if (child.getGender() != null && "male".equalsIgnoreCase(child.getGender())) {
                displayText = displayText.replace(replaceNames[9], "him");
                displayText = displayText.replace(replaceNames[10], "his");
                displayText = displayText.replace(replaceNames[11], "he");
                displayText = displayText.replace(replaceNames[12], "himself");
            } else if (child.getGender() != null) {
                displayText = displayText.replace(replaceNames[9], "her");
                displayText = displayText.replace(replaceNames[10], "her");
                displayText = displayText.replace(replaceNames[11], "she");
                displayText = displayText.replace(replaceNames[12], "herself");
            }
            displayText = displayText.replace(replaceNames[13], activityName);
        }
        catch(Exception e){
            Logger.logE("TAG", "replaceLabel", e);
        }
        return displayText;
    }

    /**
     * Method to extract the user's age from the entered Date of Birth.
     *
     * @return ageS String The user's age in years based on the supplied DoB.
     */
    private String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


}