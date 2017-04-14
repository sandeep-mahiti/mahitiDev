package com.parentof.mai.activityinterfaces;

import com.parentof.mai.model.reminders.GetReminderResponse;
import com.parentof.mai.model.reminders.ReminderSavedResponse;

/**
 * Created by mahiti on 10/4/17.
 */
public interface SaveReminderCallBack {
    void saveReminderResponseCallBack(ReminderSavedResponse reminderSavedResponse);
    void getResponseCallBack(GetReminderResponse getReminderResponse);
}
