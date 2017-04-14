
package com.parentof.mai.model.verifyuser.succesModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyAPISuccessRespModel implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    protected VerifyAPISuccessRespModel(Parcel in) {
        status = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<VerifyAPISuccessRespModel> CREATOR = new Creator<VerifyAPISuccessRespModel>() {
        @Override
        public VerifyAPISuccessRespModel createFromParcel(Parcel in) {
            return new VerifyAPISuccessRespModel(in);
        }

        @Override
        public VerifyAPISuccessRespModel[] newArray(int size) {
            return new VerifyAPISuccessRespModel[size];
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
