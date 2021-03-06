
package com.parentof.mai.model.emailotprespmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailOTPRespModel implements Parcelable{

    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("responseDesc")
    @Expose
    private String responseDesc;
    @SerializedName("data")
    @Expose
    private Data data;

    protected EmailOTPRespModel(Parcel in) {
        responseDesc = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<EmailOTPRespModel> CREATOR = new Creator<EmailOTPRespModel>() {
        @Override
        public EmailOTPRespModel createFromParcel(Parcel in) {
            return new EmailOTPRespModel(in);
        }

        @Override
        public EmailOTPRespModel[] newArray(int size) {
            return new EmailOTPRespModel[size];
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
        dest.writeString(responseDesc);
        dest.writeParcelable(data, flags);
    }
}
