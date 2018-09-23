package com.example.enjoy.healthy02.Weight;

public class Weight {

    String weight;
    String date;
    String status;

    public Weight(String date, String status, String weight) {
        this.weight = weight;
        this.date = date;
        this.status = status;
    }

    public Weight(String date, String weight) {
        this.weight = weight;
        this.date = date;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
