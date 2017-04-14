package com.parentof.mai.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.parentof.mai.activityinterfaces.ResponseForReminderSavedNot;
import com.parentof.mai.db.DatabaseHelper;
import com.parentof.mai.model.getchildrenmodel.Child;
import com.parentof.mai.model.reminders.ReminderModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by mahiti on 13/2/17.
 */
public class ReminderClass {


    // Add an event to the calendar of the user.
    public static void addEvent(Activity context, int _year, int _month, int _day, int _hour, int _minute, long _eventId, String title, String desc, String location, Child childBeanReminder, String userId, ResponseForReminderSavedNot responseForReminderSavedNot) {
        Logger.logD(Constants.PROJECT, "addEvent-->" +
                _year + "_year" + (_month - 1) + "--" + _day + "--" + _hour + " " + _minute + " " + _eventId);
        GregorianCalendar calDate = new GregorianCalendar(_year, _month - 1, _day, _hour, _minute);

        try {
            Logger.logD(Constants.PROJECT, "addEvent getTimeInMillis-->" + calDate.getTimeInMillis());
            Logger.logD(Constants.PROJECT, "addEvent Title-->" + title);
            ContentResolver cr = context.getContentResolver();
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
            values.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis() + 60 * 60 * 1000);
            values.put(CalendarContract.Events.TITLE, title);
            values.put(CalendarContract.Events.CALENDAR_ID, 1);
            values.put(CalendarContract.Events.DESCRIPTION, desc);
            values.put(CalendarContract.Events.EVENT_LOCATION, location);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID());
            System.out.println(Calendar.getInstance().getTimeZone().getID());
            Logger.logD(Constants.PROJECT, "addEvent _eventId-->" + _eventId);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Logger.logD(Constants.PROJECT, "addEvent values-->" + values);
            Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
            // yyyy-MM-dd HH:mm:ss

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            String reminderDate = df.format(calDate.getTime());

            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            ReminderModel reminderModel = new ReminderModel();
            reminderModel.setChildId(childBeanReminder.getChild().getId());
            reminderModel.setTitle(title);
            reminderModel.setDescription(desc);
            reminderModel.setLocation(location);
            reminderModel.setReminderStatus("open");

            reminderModel.setReminderDate(reminderDate);
            reminderModel.setReminderTime(String.valueOf(calDate.getTimeInMillis()));
            reminderModel.setUserId(userId);
            long eventID = Long.parseLong(uri.getLastPathSegment());
            // reminderModel.setId(Integer.parseInt(uri.getLastPathSegment()));
            List<ReminderModel> reminderModelList = new ArrayList<>();
            databaseHelper.insertReminder(reminderModel);
            reminderModelList.add(reminderModel);

            // get the event ID that is the last element in the Uri
            _eventId = Long.parseLong(uri.getLastPathSegment());

            Logger.logD(Constants.PROJECT, "addEvent _eventId-->" + eventID);
            responseForReminderSavedNot.reminderSaveResponse(String.valueOf(eventID), reminderModelList);

            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, calDate.getTimeInMillis());
            setReminder(context, cr, _eventId, 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // routine to add reminders with the event
    public static void setReminder(Activity context, ContentResolver cr, long eventID, int timeBefore) {
        try {
            Logger.logD(Constants.PROJECT, "setReminder eventID-->" + eventID);
            // Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(context) + "reminders");
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.MINUTES, timeBefore);
            values.put(CalendarContract.Reminders.EVENT_ID, eventID);
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            // values.put(CalendarContract.Reminders.DESCRIPTION, "Hello mai Description");
            //values.put(CalendarContract.Reminders.MINUTES, 1);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Logger.logD(Constants.PROJECT, "setReminder values-->" + values);

            //   Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");

            //  Uri uri = cr.insert(REMINDERS_URI, values);

            Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
            Cursor c = CalendarContract.Reminders.query(cr, eventID,
                    new String[]{CalendarContract.Reminders.MINUTES});
            if (c.moveToFirst()) {
                System.out.println("calendar"
                        + c.getInt(c.getColumnIndex(CalendarContract.Reminders.MINUTES)));
            }
            c.close();
            Logger.logD(Constants.PROJECT, "setReminder Cursor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Returns Calendar Base URI, supports both new and old OS.
     */
    private static String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }

    private static int getPrimaryCalendar(Context context) {
        // noinspection ResourceType
        Cursor managedCursor = context.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, new String[]{
                CalendarContract.Calendars._ID, CalendarContract.Calendars.IS_PRIMARY}, null, null, null);

        int calID = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (managedCursor != null && managedCursor.moveToFirst()) {
                do {
                    calID = managedCursor.getInt(managedCursor.getColumnIndex(CalendarContract.Calendars._ID));
                    int columnIndex = -1;
                    try {
                        columnIndex = managedCursor.getColumnIndex(CalendarContract.Calendars.IS_PRIMARY);
                    } catch (NullPointerException e) {
                        Log.d("tag", e.getMessage());
                    }
                    if (columnIndex != -1 && managedCursor.getInt(columnIndex) == 1) {
                        break;
                    } else {
                        calID = 1;
                    }
                } while (managedCursor.moveToNext());
                managedCursor.close();
            }
        }

        return calID;
    }

   /* // function to remove an event from the calendar using the eventId stored within the Task object.
    public void removeEvent(Context context) {
        ContentResolver cr = context.getContentResolver();

        int iNumRowsDeleted = 0;

        Uri eventsUri = Uri.parse(CALENDAR_URI_BASE+"events");
        Uri eventUri = ContentUris.withAppendedId(eventsUri, _eventId);
        iNumRowsDeleted = cr.delete(eventUri, null, null);

        Log.i(DEBUG_TAG, "Deleted " + iNumRowsDeleted + " calendar entry.");
    }


    public int updateEvent(Context context) {
        int iNumRowsUpdated = 0;
        GregorianCalendar calDate = new GregorianCalendar(_year, _month, _day, _hour, _minute);

        ContentValues event = new ContentValues();

        event.put(CalendarContract.Events.TITLE, _title);
        event.put("hasAlarm", 1); // 0 for false, 1 for true
        event.put(CalendarContract.Events.DTSTART, calDate.getTimeInMillis());
        event.put(CalendarContract.Events.DTEND, calDate.getTimeInMillis()+60*60*1000);

        Uri eventsUri = Uri.parse(CALENDAR_URI_BASE+"events");
        Uri eventUri = ContentUris.withAppendedId(eventsUri, _eventId);

        iNumRowsUpdated = context.getContentResolver().update(eventUri, event, null,
                null);

        // TODO put text into strings.xml
        Log.i(DEBUG_TAG, "Updated " + iNumRowsUpdated + " calendar entry.");

        return iNumRowsUpdated;
    }*/

   /* public void addEvent(CalendarEvent evt) {
        //Log.d(Params.LOG_APP, "Insert event ["+evt+"]");

        try {
            Uri evtUri = ctx.getContentResolver().insert(getCalendarUri("events"), CalendarEvent.toContentValues(evt));
            Log.d(Params.LOG_APP, "" + evtUri);
        } catch (Throwable t) {
            //Log.e(Params.LOG_APP, "", t);
        }
    }


    private String getCalendarUriBase(Context ctx) {
        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = ctx.getContentResolver().query(calendars, null, null, null, null);
        } catch (Exception e) {
            // e.printStackTrace();
        }

        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = ctx.getContentResolver().query(calendars, null, null, null, null);
            } catch (Exception e) {
                // e.printStackTrace();
            }

            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }

        }

        Log.d("fsga", "URI [" + calendarUriBase + "]");
        return calendarUriBase;
    }

    public void addEvent(CalendarEvent evt,Context context) {

        ContentResolver cr = context.getContentResolver();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, CalendarEvent.toICSContentValues(evt));
        System.out.println("Event URI ["+uri+"]");

    }

    public void addEvent(CalendarEvent evt,Context ctx) {
        //Log.d(Params.LOG_APP, "Insert event ["+evt+"]");

        try {
            Uri evtUri = ctx.getContentResolver().insert(getCalendarUri("events"), CalendarEvent.toContentValues(evt));
            Log.d(Params.LOG_APP, "" + evtUri);
        }
        catch(Throwable t) {
            //Log.e(Params.LOG_APP, "", t);
        }
    }

    public static ContentValues toContentValues(CalendarEvent evt) {
        ContentValues cv = new ContentValues();
        cv.put("calendar_id", evt.getIdCalendar());
        cv.put("title", evt.getTitle());
        cv.put("description", evt.getDescr());
        cv.put("eventLocation", evt.getLocation());
        cv.put("dtstart", evt.getStartTime());
        cv.put("dtend", evt.getEndTime());
        cv.put("eventStatus", 1);
        cv.put("visibility", 0);
        cv.put("transparency", 0);

        return cv;

    }

    public static ContentValues toICSContentValues(CalendarEvent evt) {

        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.CALENDAR_ID, evt.getIdCalendar());
        cv.put(CalendarContract.Events.TITLE, evt.getTitle());
        cv.put(CalendarContract.Events.DESCRIPTION, evt.getDescr());
        cv.put(CalendarContract.Events.EVENT_LOCATION, evt.getLocation());
        cv.put(CalendarContract.Events.DTSTART, evt.getStartTime());
        cv.put(CalendarContract.Events.DTEND, evt.getEndTime());

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        cv.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getDisplayName());
    *//*
    cv.put(Events.STATUS, 1);
    cv.put(Events.VISIBLE, 0);
    cv.put("transparency", 0);

    return cv;
    *//*

        return cv;
    }*/
}
