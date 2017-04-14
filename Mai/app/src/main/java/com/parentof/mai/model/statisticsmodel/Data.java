
package com.parentof.mai.model.statisticsmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable{




    private String dpName;
    private String dpId;
    private String dpImage;
    @SerializedName("totalQuestion")
    @Expose
    private Integer totalQuestion;
    @SerializedName("answeredQuestion")
    @Expose
    private Integer answeredQuestion;
    @SerializedName("ctTotal")
    @Expose
    private Integer ctTotal;
    @SerializedName("ctAchieved")
    @Expose
    private Integer ctAchieved;
    @SerializedName("totalIndicator")
    @Expose
    private Integer totalIndicator;
    @SerializedName("achievedIndicator")
    @Expose
    private Integer achievedIndicator;


    public Data() {
    }

    protected Data(Parcel in) {
        dpId=in.readString();
        dpName=in.readString();
        dpImage=in.readString();
        totalQuestion=in.readInt();
        answeredQuestion=in.readInt();
        ctTotal=in.readInt();
        ctAchieved=in.readInt();
        totalIndicator=in.readInt();
        achievedIndicator=in.readInt();

    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };


    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }

    public String getDpImage() {
        return dpImage;
    }

    public void setDpImage(String dpImage) {
        this.dpImage = dpImage;
    }

    public Integer getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public Integer getAnsweredQuestion() {
        return answeredQuestion;
    }

    public void setAnsweredQuestion(Integer answeredQuestion) {
        this.answeredQuestion = answeredQuestion;
    }

    public Integer getCtTotal() {
        return ctTotal;
    }

    public void setCtTotal(Integer ctTotal) {
        this.ctTotal = ctTotal;
    }

    public Integer getCtAchieved() {
        return ctAchieved;
    }

    public void setCtAchieved(Integer ctAchieved) {
        this.ctAchieved = ctAchieved;
    }

    public Integer getTotalIndicator() {
        return totalIndicator;
    }

    public void setTotalIndicator(Integer totalIndicator) {
        this.totalIndicator = totalIndicator;
    }

    public Integer getAchievedIndicator() {
        return achievedIndicator;
    }

    public void setAchievedIndicator(Integer achievedIndicator) {
        this.achievedIndicator = achievedIndicator;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dpId);
        dest.writeString(dpName);
        dest.writeString(dpImage);
        dest.writeInt(totalQuestion);
        dest.writeInt(answeredQuestion);
        dest.writeInt(ctTotal);
        dest.writeInt(ctAchieved);
        dest.writeInt(totalIndicator);
        dest.writeInt(achievedIndicator);


    }
}
