
package com.parentof.mai.model.reminders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetReminderResponse implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<GetReminderResponse> CREATOR = new Creator<GetReminderResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetReminderResponse createFromParcel(Parcel in) {
            GetReminderResponse instance = new GetReminderResponse();
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.data = ((Data) in.readValue((Data.class.getClassLoader())));
            return instance;
        }

        public GetReminderResponse[] newArray(int size) {
            return (new GetReminderResponse[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3311431668957569283L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
