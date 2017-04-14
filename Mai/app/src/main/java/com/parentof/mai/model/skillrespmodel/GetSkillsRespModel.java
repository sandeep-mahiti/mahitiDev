
package com.parentof.mai.model.skillrespmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSkillsRespModel implements Parcelable{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<SkillData> data = new ArrayList<>();

    public GetSkillsRespModel() {
    }

    protected GetSkillsRespModel(Parcel in) {
        status = in.readString();
        data = in.createTypedArrayList(SkillData.CREATOR);
    }

    public static final Creator<GetSkillsRespModel> CREATOR = new Creator<GetSkillsRespModel>() {
        @Override
        public GetSkillsRespModel createFromParcel(Parcel in) {
            return new GetSkillsRespModel(in);
        }

        @Override
        public GetSkillsRespModel[] newArray(int size) {
            return new GetSkillsRespModel[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SkillData> getData() {
        return data;
    }

    public void setData(List<SkillData> data) {
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
