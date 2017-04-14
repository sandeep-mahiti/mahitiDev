
package com.parentof.mai.model.getchildrenmodel;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChildrenRespModel implements Parcelable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;
    public final static Creator<GetChildrenRespModel> CREATOR = new Creator<GetChildrenRespModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetChildrenRespModel createFromParcel(Parcel in) {
            GetChildrenRespModel instance = new GetChildrenRespModel();
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.data = ((Data) in.readValue((Data.class.getClassLoader())));
            return instance;
        }

        public GetChildrenRespModel[] newArray(int size) {
            return (new GetChildrenRespModel[size]);
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(data);
    }

    public int describeContents() {
        return  0;
    }

}
