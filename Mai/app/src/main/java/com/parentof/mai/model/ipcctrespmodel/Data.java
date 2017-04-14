
package com.parentof.mai.model.ipcctrespmodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("canBeMarkedAsDone")
    @Expose
    private Boolean canBeMarkedAsDone;
    @SerializedName("taskName")
    @Expose
    private String taskName;
    @SerializedName("taskSteps")
    @Expose
    private List<String> taskSteps = null;
    @SerializedName("taskObjectives")
    @Expose
    private String taskObjectives;
    @SerializedName("dos")
    @Expose
    private List<String> dos = null;
    @SerializedName("donts")
    @Expose
    private List<String> donts = null;
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

    public Boolean getCanBeMarkedAsDone() {
        return canBeMarkedAsDone;
    }

    public void setCanBeMarkedAsDone(Boolean canBeMarkedAsDone) {
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

}
