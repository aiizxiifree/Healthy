package com.example.enjoy.healthy02.Sleep;

import java.sql.Timestamp;

public class Sleep {
    private String date;
    private String bed;
    private String wake;
    private String total;


    public Sleep() {

    }

    public Sleep(String date, String bed, String wake) {
        this.date = date;
        this.bed = bed;
        this.wake = wake;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getWake() {
        return wake;
    }

    public void setWake(String wake) {
        this.wake = wake;
    }
}
