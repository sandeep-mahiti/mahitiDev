package com.parentof.mai.activityinterfaces;

import com.parentof.mai.model.skillrespmodel.SkillData;

/**
 * Created by sandeep HR on 24/02/17.
 */

public interface TalkToMaiCallback {
    void callChatActivity(SkillData skillData, int questionsLeft);
}
