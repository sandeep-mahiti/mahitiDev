
package com.parentof.mai.model.reminders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReminderSavedResponse implements Serializable, Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    @Expose
    private String message;

    public final static Creator<ReminderSavedResponse> CREATOR = new Creator<ReminderSavedResponse>() {
        @SuppressWarnings({
                "unchecked"
        })
        public ReminderSavedResponse createFromParcel(Parcel in) {
            ReminderSavedResponse instance = new ReminderSavedResponse();
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.data = ((String) in.readValue((String.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public ReminderSavedResponse[] newArray(int size) {
            return (new ReminderSavedResponse[size]);
        }

    };
    private final static long serialVersionUID = -1466277417047420320L;

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(data);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}
