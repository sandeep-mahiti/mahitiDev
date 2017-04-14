package com.parentof.mai.model.userinfomodel;

/**
 * Created by sandeep HR on 19/01/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAdditionalInfo {


    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("officeDays")
    @Expose
    private String officeDays;
    @SerializedName("officeTiming")
    @Expose
    private String officeTiming;
    @SerializedName("averageIncome")
    @Expose
    private String averageIncome;
    @SerializedName("seperateChildRoom")
    @Expose
    private String seperateChildRoom;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("relation")
    @Expose
    private String relation;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getOfficeDays() {
        return officeDays;
    }

    public void setOfficeDays(String officeDays) {
        this.officeDays = officeDays;
    }

    public String getOfficeTiming() {
        return officeTiming;
    }

    public void setOfficeTiming(String officeTiming) {
        this.officeTiming = officeTiming;
    }

    public String getAverageIncome() {
        return averageIncome;
    }

    public void setAverageIncome(String averageIncome) {
        this.averageIncome = averageIncome;
    }

    public String getSeperateChildRoom() {
        return seperateChildRoom;
    }

    public void setSeperateChildRoom(String seperateChildRoom) {
        this.seperateChildRoom = seperateChildRoom;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }


}
