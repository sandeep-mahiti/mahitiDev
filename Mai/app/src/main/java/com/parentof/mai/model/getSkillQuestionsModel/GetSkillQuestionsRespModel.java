
package com.parentof.mai.model.getSkillQuestionsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSkillQuestionsRespModel implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public GetSkillQuestionsRespModel() {
    }

    protected GetSkillQuestionsRespModel(Parcel in) {
        status = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<GetSkillQuestionsRespModel> CREATOR = new Creator<GetSkillQuestionsRespModel>() {
        @Override
        public GetSkillQuestionsRespModel createFromParcel(Parcel in) {
            return new GetSkillQuestionsRespModel(in);
        }

        @Override
        public GetSkillQuestionsRespModel[] newArray(int size) {
            return new GetSkillQuestionsRespModel[size];
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
