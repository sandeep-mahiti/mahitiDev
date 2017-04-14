
package com.parentof.mai.model.decisionpointsmodel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable{

    @SerializedName("imageBaseUrl")
    @Expose
    private String imageBaseUrl;
    @SerializedName("activeDP")
    @Expose
    private List<ActiveDP> activeDP = null;
    @SerializedName("completedDP")
    @Expose
    private List<CompletedDP> completedDP = null;
    @SerializedName("allDP")
    @Expose
    private List<AllDP> allDP = null;

    public Data() {
    }

    protected Data(Parcel in) {
        imageBaseUrl = in.readString();
        activeDP = in.createTypedArrayList(ActiveDP.CREATOR);
        completedDP = in.createTypedArrayList(CompletedDP.CREATOR);
        allDP = in.createTypedArrayList(AllDP.CREATOR);
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public void setImageBaseUrl(String imageBaseUrl) {
        this.imageBaseUrl = imageBaseUrl;
    }

    public List<ActiveDP> getActiveDP() {
        return activeDP;
    }

    public void setActiveDP(List<ActiveDP> activeDP) {
        this.activeDP = activeDP;
    }

    public List<CompletedDP> getCompletedDP() {
        return completedDP;
    }

    public void setCompletedDP(List<CompletedDP> completedDP) {
        this.completedDP = completedDP;
    }

    public List<AllDP> getAllDP() {
        return allDP;
    }

    public void setAllDP(List<AllDP> allDP) {
        this.allDP = allDP;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageBaseUrl);
        dest.writeTypedList(activeDP);
        dest.writeTypedList(completedDP);
        dest.writeTypedList(allDP);
    }
}
