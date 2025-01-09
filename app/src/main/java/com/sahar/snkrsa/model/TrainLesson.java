package com.sahar.snkrsa.model;

import java.util.ArrayList;

public class TrainLesson {

    String id;
    User admin;
    ArrayList<TrainDay> days;


    String details;

    public TrainLesson(String id, User admin, ArrayList<TrainDay> days, String details) {
        this.id = id;
        this.admin = admin;
        this.days = days;
        this.details = details;
    }

    public TrainLesson() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public ArrayList<TrainDay> getDays() {
        return days;
    }

    public void setDays(ArrayList<TrainDay> days) {
        this.days = days;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "TrainLesson{" +
                "id='" + id + '\'' +
                ", admin=" + admin +
                ", days=" + days +
                ", details='" + details + '\'' +
                '}';
    }
}
