
package com.parentof.mai.model.skillrespmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.parentof.mai.utils.Logger;

public class SkillData implements Parcelable, Comparable<SkillData>{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("completed")
    @Expose
    private Integer completed;
    @SerializedName("isLocked")
    @Expose
    private String isLocked="false";
    @SerializedName("questionsLeft")
    @Expose
    private Integer questionsLeft;


    public SkillData() {
    }

    protected SkillData(Parcel in) {
        id = in.readString();
        description=in.readString();
        name = in.readString();
        rank = in.readString();
        thumb=in.readString();
        cover=in.readString();
        completed=in.readInt();
        isLocked=in.readString();
        questionsLeft=in.readInt();

    }

    public static final Creator<SkillData> CREATOR = new Creator<SkillData>() {
        @Override
        public SkillData createFromParcel(Parcel in) {
            return new SkillData(in);
        }

        @Override
        public SkillData[] newArray(int size) {
            return new SkillData[size];
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(String isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getQuestionsLeft() {
        return questionsLeft;
    }

    public void setQuestionsLeft(Integer questionsLeft) {
        this.questionsLeft = questionsLeft;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(rank);
        dest.writeString(thumb);
        dest.writeString(cover);
        dest.writeInt(completed);
        dest.writeString(isLocked);
        dest.writeInt(questionsLeft);

    }


    @Override
    public int compareTo(SkillData another) {
        try {
            if (Integer.parseInt(this.getRank()) > Integer.parseInt(another.getRank())){
                return 1;
            } else if (Integer.parseInt(this.getRank()) < Integer.parseInt(another.getRank())){
                return -1;
            }
            else {
                return 0;
            }
        }catch (Exception e){
            Logger.logE("SkillData", "compareTo : ", e);
        }
        return 0;
    }
}
