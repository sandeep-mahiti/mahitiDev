
package com.parentof.mai.model.getuserdetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserDetailRespModel implements Parcelable {

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseDesc")
    @Expose
    private String responseDesc;
    @SerializedName("data")
    @Expose
    private Data data;

    public GetUserDetailRespModel() {

    }

    public GetUserDetailRespModel(Parcel in) {
        responseCode = in.readInt();
        responseDesc = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<GetUserDetailRespModel> CREATOR = new Creator<GetUserDetailRespModel>() {
        @Override
        public GetUserDetailRespModel createFromParcel(Parcel in) {
            return new GetUserDetailRespModel(in);
        }

        @Override
        public GetUserDetailRespModel[] newArray(int size) {
            return new GetUserDetailRespModel[size];
        }
    };

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
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
        dest.writeInt(responseCode);
        dest.writeString(responseDesc);
        dest.writeParcelable(data, flags);
    }
}
