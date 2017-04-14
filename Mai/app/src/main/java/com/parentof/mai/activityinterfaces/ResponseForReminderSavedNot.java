package com.parentof.mai.activityinterfaces;

import com.parentof.mai.model.reminders.ReminderModel;

import java.util.List;

/**
 * Created by mahiti on 10/4/17.
 */
public interface ResponseForReminderSavedNot {
    void reminderSaveResponse(String rId, List<ReminderModel> reminderModels);
}
