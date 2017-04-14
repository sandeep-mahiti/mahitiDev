
package com.parentof.mai.model.reminders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable, Parcelable
{

    @SerializedName("reminders")
    @Expose
    private String reminders;
    @SerializedName("childId")
    @Expose
    private String childId;
    @SerializedName("userId")
    @Expose
    private String userId;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            Data instance = new Data();
            instance.reminders = ((String) in.readValue((String.class.getClassLoader())));
            instance.childId = ((String) in.readValue((String.class.getClassLoader())));
            instance.userId = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
    ;
    private final static long serialVersionUID = 4090443316240485157L;

    public String getReminders() {
        return reminders;
    }

    public void setReminders(String reminders) {
        this.reminders = reminders;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(reminders);
        dest.writeValue(childId);
        dest.writeValue(userId);
    }

    public int describeContents() {
        return  0;
    }

}
