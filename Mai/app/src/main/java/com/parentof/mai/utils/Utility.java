package com.parentof.mai.utils;

/**
 * Created by sandeep HR on 01/03/17.
 */

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utility {


    private static String TAG = "Utility";

    public static int setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
        return params.height;
    }


    static String ARRAY_DIVIDER = "#a1r2ra5yd2iv1i9der";

    public static String serialize(List<String> content) {
        return TextUtils.join(ARRAY_DIVIDER, content);
    }

    public static String[] derialize(String content) {
        return content.split(ARRAY_DIVIDER);
    }

    // Converting time format to simple time format
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

    // getting the current time
    public static Date currentTimeWithSnoozeTime(int mints) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH); // time format.......
        c.add(Calendar.MINUTE, mints);
        String time = df.format(c.getTime()); // converting to string from time format..
        Date yourDate = null;
        try {
            yourDate = df.parse(time); // parse time
        } catch (ParseException e) {
            Logger.logE("TAG", "Exception in date", e);
        }
        //return current time...
        return yourDate;
    }

    // getting the current time
    public static Date currentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH); // time format.......
        String time = df.format(c.getTime()); // converting to string from time format..
        Date yourDate = null;
        try {
            yourDate = df.parse(time); // parse time
        } catch (ParseException e) {
            Logger.logE("TAG", "Exception in date", e);
        }
        //return current time...
        return yourDate;
    }


    // getting the morningTime
    public static Date getSnoozeTime(String time) {

        Date mrgDate = null;
        try {
            mrgDate = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            Logger.logE("TAG", "Exception in mrg date", e);
        }
        //return morningTime...
        return mrgDate;
    }

    public static String currentDate() {
        String currentDate = null;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            currentDate = df.format(date);
        } catch (Exception e) {
            Logger.logE("TAG", "Exception in mrg date", e);
        }
        return currentDate;
    }

    public static Date returnCurrentDate() {
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            date = calendar.getTime();
            String currentDate;
            currentDate = df.format(date);
            date = df.parse(currentDate);
        } catch (Exception e) {
            Logger.logE("TAG", "Exception in mrg date", e);
        }
        return date;
    }

    public static Date returnCurrentTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a", Locale.ENGLISH); // time format.......
        String time = df.format(c.getTime()); // converting to string from time format..
        Date yourDate = null;
        try {
            yourDate = df.parse(time); // parse time
            System.out.println(" --------------- Current Time: " + yourDate);
        } catch (ParseException e) {
            Logger.logE("TAG", "Exception in date", e);
        }
        //return current time...
        return yourDate;
    }

    public static String currentDateTime() {
        String newFormat = "Today";
        try {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH);
            newFormat = formatter.format(date);

            System.out.println(" --------------- date formatted 22222 : " + newFormat);
        } catch (Exception e) {
            Logger.logE(TAG, "currentDateTime :  ", e);
        }
        return newFormat;
    }


    public static String dateTo_dMy_Format(String date) {
        String newFormat = "dd/MM/yyyy";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date date1 = dateFormat.parse(date);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            newFormat = formatter.format(date1);

            System.out.println(" --------------- date formatted 22222 : " + newFormat);
        } catch (Exception e) {
            Logger.logE(TAG, "formattedDateForChildDOB : ", e);
        }
        return newFormat;
    }

    public static String dateTO_Mdy_Format(String date) {
        String newFormat = "dd/MM/yyyy";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date1 = dateFormat.parse(date);

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            newFormat = formatter.format(date1);

            System.out.println(" --------------- date formatted 22222 : " + newFormat);
        } catch (Exception e) {
            Logger.logE(TAG, "formattedDateForChildDOB : ", e);
        }
        return newFormat;
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String dateToMonthNameFormat(String dateFromString) {
        String resultFormat = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date date1 = dateFormat.parse(dateFromString);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM,yyyy", Locale.ENGLISH);
            resultFormat = simpleDateFormat.format(date1);
            System.out.println(" --------------- date formatted  : " + resultFormat);
        } catch (ParseException e) {
            Logger.logE(TAG, "dateToMonthNameFormat : ", e);
        }
        return resultFormat;
    }

    public static String dateToMonthFullNameFormat(String dateFromString) {
        String resultFormat = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date1 = dateFormat.parse(dateFromString);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd", Locale.ENGLISH);
            resultFormat = simpleDateFormat.format(date1);
            System.out.println(" --------------- date formatted  : " + resultFormat);
        } catch (ParseException e) {
            Logger.logE(TAG, "dateToMonthNameFormat : ", e);
        }
        return resultFormat;
    }

    public static boolean validatedDatesEqualOrNot(String selDate) {
        try {
            Date currentDate = Utility.returnCurrentDate();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date selectedDate = df.parse(selDate);
            if (currentDate.equals(selectedDate)) {
                System.out.println(" --------------- date equal  : ");
                return true;
            } else
                System.out.println(" --------------- date Not equal  : ");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
