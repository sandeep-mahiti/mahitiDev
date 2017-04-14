
package com.parentof.mai.model.ImprovementPlanModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Log implements Parcelable{

    @SerializedName("dayName")
    @Expose
    private String dayName;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("logDate")
    @Expose
    private String logDate;

    protected Log(Parcel in) {
        dayName = in.readString();
        id = in.readString();
        logDate = in.readString();
    }

    public static final Creator<Log> CREATOR = new Creator<Log>() {
        @Override
        public Log createFromParcel(Parcel in) {
            return new Log(in);
        }

        @Override
        public Log[] newArray(int size) {
            return new Log[size];
        }
    };

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dayName);
        dest.writeString(id);
        dest.writeString(logDate);
    }
}
