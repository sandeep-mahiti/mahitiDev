package com.parentof.mai.activityinterfaces;

import com.parentof.mai.model.BlockuserRespModel;
import com.parentof.mai.model.securityquestions.SecurityQuestionsModel;
import com.parentof.mai.model.verifyuser.VerifyUserModel;
import com.parentof.mai.model.verifyuser.existnochildmodel.ExistUserNoChildModel;
import com.parentof.mai.model.verifyuser.failmodel.anonymus.VerifyAPIAnonymusRespModel;
import com.parentof.mai.model.verifyuser.failmodel.locked.VerifyAPILockedRespModel;
import com.parentof.mai.model.verifyuser.failmodel.newuser.VerifyAPINewUserRespModel;
import com.parentof.mai.model.verifyuser.succesModel.VerifyAPISuccessRespModel;
import com.parentof.mai.model.verifyuser.succesModel.nochild.VerifyAPINoChildRespModel;

import org.json.JSONObject;

/**
 * Created by sandeep HR on 05/01/17.
 */

public interface SecurityQuestionsCallBack {
    void secQuestions(VerifyAPISuccessRespModel secQstns);
    void existNoChild(VerifyAPINoChildRespModel verifyAPINoChildRespModel);
    void newUser(VerifyAPIAnonymusRespModel newuserModel);
    void blockedUser(VerifyAPILockedRespModel verifyAPILockedRespModel);
}
