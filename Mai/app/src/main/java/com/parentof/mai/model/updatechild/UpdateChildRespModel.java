package com.parentof.mai.model.updatechild;

/**
 * Created by sandeep HR on 20/01/17.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateChildRespModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Parcelable.Creator<UpdateChildRespModel> CREATOR = new Creator<UpdateChildRespModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UpdateChildRespModel createFromParcel(Parcel in) {
            UpdateChildRespModel instance = new UpdateChildRespModel();
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public UpdateChildRespModel[] newArray(int size) {
            return (new UpdateChildRespModel[size]);
        }

    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return 0;
    }

}