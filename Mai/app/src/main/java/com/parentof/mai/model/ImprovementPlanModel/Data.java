
package com.parentof.mai.model.ImprovementPlanModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data implements Parcelable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("canBeMarkedAsDone")
    @Expose
    private String canBeMarkedAsDone;
    @SerializedName("taskName")
    @Expose
    private String taskName;
    @SerializedName("taskSteps")
    @Expose
    private List<String> taskSteps = new ArrayList<>();
    @SerializedName("taskObjectives")
    @Expose
    private String taskObjectives;
    @SerializedName("dos")
    @Expose
    private List<String> dos = new ArrayList<>();
    @SerializedName("donts")
    @Expose
    private List<String> donts = new ArrayList<>();
    @SerializedName("reminder")
    @Expose
    private String reminder;
    @SerializedName("reminderQn")
    @Expose
    private String reminderQn;
    @SerializedName("qualifyingQn")
    @Expose
    private String qualifyingQn;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("logs")
    @Expose
    private List<Log> logs = null;
    @SerializedName("isPaused")
    @Expose
    private String isPaused;

    public  Data(){

    }

    public Data(Parcel in) {
        id = in.readString();
        taskName = in.readString();
        taskSteps = in.createStringArrayList();
        taskObjectives = in.readString();
        dos = in.createStringArrayList();
        donts = in.createStringArrayList();
        reminder = in.readString();
        reminderQn = in.readString();
        qualifyingQn = in.readString();
        logs = in.createTypedArrayList(Log.CREATOR);
        duration = in.readInt();
        level=in.readInt();
        isPaused=in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getCanBeMarkedAsDone() {
        return canBeMarkedAsDone;
    }

    public void setCanBeMarkedAsDone(String canBeMarkedAsDone) {
        this.canBeMarkedAsDone = canBeMarkedAsDone;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public List<String> getTaskSteps() {
        return taskSteps;
    }

    public void setTaskSteps(List<String> taskSteps) {
        this.taskSteps = taskSteps;
    }

    public String getTaskObjectives() {
        return taskObjectives;
    }

    public void setTaskObjectives(String taskObjectives) {
        this.taskObjectives = taskObjectives;
    }

    public List<String> getDos() {
        return dos;
    }

    public void setDos(List<String> dos) {
        this.dos = dos;
    }

    public List<String> getDonts() {
        return donts;
    }

    public void setDonts(List<String> donts) {
        this.donts = donts;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getReminderQn() {
        return reminderQn;
    }

    public void setReminderQn(String reminderQn) {
        this.reminderQn = reminderQn;
    }

    public String getQualifyingQn() {
        return qualifyingQn;
    }

    public void setQualifyingQn(String qualifyingQn) {
        this.qualifyingQn = qualifyingQn;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public String getIsPaused() {
        return isPaused;
    }

    public void setIsPaused(String isPaused) {
        this.isPaused = isPaused;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(taskName);
        dest.writeStringList(taskSteps);
        dest.writeString(taskObjectives);
        dest.writeStringList(dos);
        dest.writeStringList(donts);
        dest.writeString(reminder);
        dest.writeString(reminderQn);
        dest.writeString(qualifyingQn);
        dest.writeTypedList(logs);
        dest.writeInt(duration);
        dest.writeInt(level);
        dest.writeString(isPaused);
    }
}
