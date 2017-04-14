package com.parentof.mai.activityinterfaces;

import com.parentof.mai.api.apicalls.AddChildAPI;
import com.parentof.mai.model.addchildmodel.AddChildRespModel;

/**
 * Created by sandeep HR on 16/01/17.
 */

public interface AddChildRespCallBack {
    void addChildResp(AddChildRespModel addChildRespModel);
}
