package org.techtown.androidmysql;

public class dailyweight {
    int year;

    String date;
    float weight;


    public dailyweight(int year, String date, float weight) {
        this.year = year;
        this.date = date;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
