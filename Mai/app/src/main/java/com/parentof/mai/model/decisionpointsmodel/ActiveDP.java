
package com.parentof.mai.model.decisionpointsmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parentof.mai.utils.StringUtils;

public class ActiveDP implements Parcelable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("compPercent")
    @Expose
    private Integer compPercent;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cover")
    @Expose
    private String cover;

    public ActiveDP() {
    }

    protected ActiveDP(Parcel in) {
        id = in.readString();
        name = in.readString();
        active=in.readString();
        image = in.readString();
        createdDate = in.readString();
        compPercent=in.readInt();
        description=in.readString();
        cover=in.readString();
    }

    public static final Creator<ActiveDP> CREATOR = new Creator<ActiveDP>() {
        @Override
        public ActiveDP createFromParcel(Parcel in) {
            return new ActiveDP(in);
        }

        @Override
        public ActiveDP[] newArray(int size) {
            return new ActiveDP[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCompPercent() {
        return compPercent;
    }

    public void setCompPercent(Integer compPercent) {
        this.compPercent = compPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(active);
        dest.writeString(image);
        dest.writeString(createdDate);
        dest.writeInt(compPercent);
        dest.writeString(description);
        dest.writeString(cover);
    }
}
