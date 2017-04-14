package com.parentof.mai.activityinterfaces;

import com.parentof.mai.model.decisionpointsmodel.AllDP;

/**
 * Created by sandeep HR on 21/02/17.
 */
public interface SelectedDpTOActivtyCallback {
    void selectedDPtoCallSkillsAPI(AllDP selectedDP, boolean gotoChat);
    void addReminder(int pos);
    void responseDone(int reminderId);
/*    void selectedDPtoCallSkillsAPI(ActiveDP selectedDP, boolean gotoChat);
    void selectedDPtoCallSkillsAPI(CompletedDP selectedDP, boolean gotoChat);*/

}
