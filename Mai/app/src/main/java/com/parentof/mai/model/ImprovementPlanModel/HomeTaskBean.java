package com.parentof.mai.model.ImprovementPlanModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mahiti on 1/3/17.
 */
public class HomeTaskBean implements Parcelable{
    private Data data;

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public static Creator<HomeTaskBean> getCREATOR() {
        return CREATOR;
    }

    @SerializedName("skillId")
    @Expose
    private String skillId;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("childId")
    @Expose
    private String childId;

    @SerializedName("dpId")
    @Expose
    private String dpId;
    @SerializedName("dp_name")
    @Expose
    private String dpName;

    @SerializedName("skill_name")
    @Expose
    private String skillName;

    @SerializedName("activation_date")
    @Expose
    private String activationDate;

    @SerializedName("dos")
    @Expose
    private String dos;

    @SerializedName("dots")
    @Expose
    private String dots;



    public HomeTaskBean() {
    }

    protected HomeTaskBean(Parcel in) {
        data = in.readParcelable(Data.class.getClassLoader());
        skillId = in.readString();
        userId = in.readString();
        childId = in.readString();
        dpId = in.readString();

        dpName = in.readString();
        skillName = in.readString();
        activationDate = in.readString();
    }

    public static final Creator<HomeTaskBean> CREATOR = new Creator<HomeTaskBean>() {
        @Override
        public HomeTaskBean createFromParcel(Parcel in) {
            return new HomeTaskBean(in);
        }

        @Override
        public HomeTaskBean[] newArray(int size) {
            return new HomeTaskBean[size];
        }
    };

    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getDots() {
        return dots;
    }

    public void setDots(String dots) {
        this.dots = dots;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
        dest.writeString(skillId);
        dest.writeString(userId);
        dest.writeString(childId);
        dest.writeString(dpId);
        dest.writeString(dpName);
        dest.writeString(skillName);
        dest.writeString(activationDate);
    }
}
