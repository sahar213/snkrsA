package com.sahar.snkrsa.model;

import java.util.ArrayList;

public class TrainDay {


    String date;
    String day;
    String hour;

    int maxUsers;
    ArrayList<User>users;

    public TrainDay(String date, String day, String hour, int maxUsers, ArrayList<User> users) {
        this.date = date;
        this.day = day;
        this.hour = hour;
        this.maxUsers = maxUsers;
        this.users = users;
    }



    public TrainDay() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TrainDay{" +
                "date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", maxUsers=" + maxUsers +
                ", users=" + users +
                '}';
    }
}
