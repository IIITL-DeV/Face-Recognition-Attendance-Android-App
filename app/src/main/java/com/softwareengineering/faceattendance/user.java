package com.softwareengineering.faceattendance;

public class user {

    String subject, percentage;

    public user() {

    }

    public user(String subject, String percentage) {
        this.subject = subject;
        this.percentage = percentage;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
