
package com.parentof.mai.model.activateskillmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActivateSkillRespModel implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    protected ActivateSkillRespModel(Parcel in) {
        status = in.readString();
        data = in.createTypedArrayList(Datum.CREATOR);
    }

    public static final Creator<ActivateSkillRespModel> CREATOR = new Creator<ActivateSkillRespModel>() {
        @Override
        public ActivateSkillRespModel createFromParcel(Parcel in) {
            return new ActivateSkillRespModel(in);
        }

        @Override
        public ActivateSkillRespModel[] newArray(int size) {
            return new ActivateSkillRespModel[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeTypedList(data);
    }
}
