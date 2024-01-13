package com.example.tueankinya.model;

import android.os.Parcel;

public class DrugTime {
    private int id;
    private String drug_name;
    private String start_time;
    private String end_time;
    private String morning;
    private String daytime;
    private String evening;
    private String nighttime;
    private Boolean before_time;
    private Boolean after_time;

    public DrugTime(int id, String drug_name, String start_time, String end_time, String morning, String daytime, String evening, String nighttime, Boolean before_time, Boolean after_time) {
        this.id = id;
        this.drug_name = drug_name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.morning = morning;
        this.daytime = daytime;
        this.evening = evening;
        this.nighttime = nighttime;
        this.before_time = before_time;
        this.after_time = after_time;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDrugName() {
        return drug_name;
    }

    public void setDrugName(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getStartTime() {
        return start_time;
    }

    public void setStartTime(String start_time) {
        this.start_time = start_time;
    }

    public String getEndTime() {
        return end_time;
    }

    public void setEndTime(String end_time) {
        this.end_time = end_time;
    }

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getDay() {
        return daytime;
    }

    public void setDay(String day) {
        this.daytime = day;
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening;
    }

    public String getNight() {
        return nighttime;
    }

    public void setNight(String night) {
        this.nighttime = night;
    }

    public Boolean getBeforeTime() {
        return before_time;
    }

    public void setBeforeTime(Boolean before_time) {
        this.before_time = before_time;
    }

    public Boolean getAfterTime() {
        return after_time;
    }

    public void setAfterTime(Boolean after_time) {
        this.after_time = after_time;
    }


}
