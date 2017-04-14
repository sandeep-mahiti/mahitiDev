
package com.parentof.mai.model.allActivatedInterventionModel;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Serializable, Parcelable
{

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
    @SerializedName("skillId")
    @Expose
    private String skillId;
    @SerializedName("completed")
    @Expose
    private Boolean completed;
    @SerializedName("logs")
    @Expose
    private List<Object> logs = null;
    @SerializedName("playPauseLogs")
    @Expose
    private List<Object> playPauseLogs = null;
    @SerializedName("skillName")
    @Expose
    private String skillName;
    @SerializedName("skillCover")
    @Expose
    private String skillCover;
    @SerializedName("dpName")
    @Expose
    private String dpName;
    @SerializedName("dpId")
    @Expose
    private String dpId;
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            Datum instance = new Datum();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.duration = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.canBeMarkedAsDone = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.taskName = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.taskSteps, (String.class.getClassLoader()));
            instance.taskObjectives = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.dos, (String.class.getClassLoader()));
            in.readList(instance.donts, (String.class.getClassLoader()));
            instance.reminder = ((String) in.readValue((String.class.getClassLoader())));
            instance.reminderQn = ((String) in.readValue((String.class.getClassLoader())));
            instance.qualifyingQn = ((String) in.readValue((String.class.getClassLoader())));
            instance.level = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.skillId = ((String) in.readValue((String.class.getClassLoader())));
            instance.completed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            in.readList(instance.logs, (Object.class.getClassLoader()));
            in.readList(instance.playPauseLogs, (Object.class.getClassLoader()));
            instance.skillName = ((String) in.readValue((String.class.getClassLoader())));
            instance.skillCover = ((String) in.readValue((String.class.getClassLoader())));
            instance.dpName = ((String) in.readValue((String.class.getClassLoader())));
            instance.dpId = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;
    private final static long serialVersionUID = 3605151714346618308L;

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

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public List<Object> getLogs() {
        return logs;
    }

    public void setLogs(List<Object> logs) {
        this.logs = logs;
    }

    public List<Object> getPlayPauseLogs() {
        return playPauseLogs;
    }

    public void setPlayPauseLogs(List<Object> playPauseLogs) {
        this.playPauseLogs = playPauseLogs;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillCover() {
        return skillCover;
    }

    public void setSkillCover(String skillCover) {
        this.skillCover = skillCover;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(duration);
        dest.writeValue(canBeMarkedAsDone);
        dest.writeValue(taskName);
        dest.writeList(taskSteps);
        dest.writeValue(taskObjectives);
        dest.writeList(dos);
        dest.writeList(donts);
        dest.writeValue(reminder);
        dest.writeValue(reminderQn);
        dest.writeValue(qualifyingQn);
        dest.writeValue(level);
        dest.writeValue(skillId);
        dest.writeValue(completed);
        dest.writeList(logs);
        dest.writeList(playPauseLogs);
        dest.writeValue(skillName);
        dest.writeValue(skillCover);
        dest.writeValue(dpName);
        dest.writeValue(dpId);
    }

    public int describeContents() {
        return  0;
    }

}
