
package com.parentof.mai.model.statisticsmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticsRespModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public StatisticsRespModel() {

    }

    protected StatisticsRespModel(Parcel in) {
        status = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<StatisticsRespModel> CREATOR = new Creator<StatisticsRespModel>() {
        @Override
        public StatisticsRespModel createFromParcel(Parcel in) {
            return new StatisticsRespModel(in);
        }

        @Override
        public StatisticsRespModel[] newArray(int size) {
            return new StatisticsRespModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeParcelable(data, flags);
    }
}
