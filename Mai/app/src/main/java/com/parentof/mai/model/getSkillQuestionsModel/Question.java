
package com.parentof.mai.model.getSkillQuestionsModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question implements Parcelable{

    @SerializedName("childId")
    @Expose
    private String childId;
    @SerializedName("dpId")
    @Expose
    private String dpId;
    @SerializedName("skillId")
    @Expose
    private String skillId;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("indicator")
    @Expose
    private String indicator;
    @SerializedName("answer")
    @Expose
    private String answer;

    public Question() {
    }

    protected Question(Parcel in) {
        childId=in.readString();
        dpId=in.readString();
        skillId=in.readString();
        id = in.readString();
        question = in.readString();
        indicator = in.readString();
        answer = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };


    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(childId);
        dest.writeString(dpId);
        dest.writeString(skillId);
        dest.writeString(id);
        dest.writeString(question);
        dest.writeString(indicator);
        dest.writeString(answer);
    }
}
