
package com.parentof.mai.model.dayLoggedModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DayLoggedModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private String data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("skillId")
    @Expose
    private String skillId;


    @SerializedName("childId")
    @Expose
    private String childId;

    @SerializedName("typeRem")
    @Expose
    private String typeRem;

    @SerializedName("ans")
    @Expose
    private String ans;

    @SerializedName("dpId")
    @Expose
    private String dpId;
    @SerializedName("currentDate")
    @Expose
    private String currentDate;

    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getTypeRem() {
        return typeRem;
    }

    public void setTypeRem(String typeRem) {
        this.typeRem = typeRem;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("iId")
    @Expose
    private String iId;

}
