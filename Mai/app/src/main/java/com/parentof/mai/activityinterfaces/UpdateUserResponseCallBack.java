package com.parentof.mai.activityinterfaces;

import com.parentof.mai.model.getuserdetail.GetUserDetailRespModel;
import com.parentof.mai.model.updateuser.UpdateUserGeneralBean;

/**
 * Created by mahiti on 16/2/17.
 */
public interface UpdateUserResponseCallBack {
    void responseBackToActivity(GetUserDetailRespModel getUserDetailRespModel);
    void responseUserGeneralTOActivity(UpdateUserGeneralBean updateUserGeneralBean);
}
