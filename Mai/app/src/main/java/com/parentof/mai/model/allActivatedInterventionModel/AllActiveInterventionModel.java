
package com.parentof.mai.model.allActivatedInterventionModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllActiveInterventionModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    protected AllActiveInterventionModel(Parcel in) {
        status = in.readString();
    }

    public static final Creator<AllActiveInterventionModel> CREATOR = new Creator<AllActiveInterventionModel>() {
        @Override
        public AllActiveInterventionModel createFromParcel(Parcel in) {
            return new AllActiveInterventionModel(in);
        }

        @Override
        public AllActiveInterventionModel[] newArray(int size) {
            return new AllActiveInterventionModel[size];
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
    }
}
