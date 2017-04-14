package com.parentof.mai.activityinterfaces;

import com.parentof.mai.model.getchildrenmodel.AdditionalInfo_;
import com.parentof.mai.model.getchildrenmodel.Child_;
import com.parentof.mai.model.getchildrenmodel.GetChildrenRespModel;
import com.parentof.mai.model.getchildrenmodel.HealthDetails;

/**
 * Created by sandeep HR on 23/01/17.
 */

public interface ChildUpdateInterfaceCallback {
    void getUpdatedGenData(Child_ updatedChild);
    void getUpdatedHealthData(HealthDetails healthDetails);
    void getUpdatedAddiData(AdditionalInfo_ additionalInfo_);
}
