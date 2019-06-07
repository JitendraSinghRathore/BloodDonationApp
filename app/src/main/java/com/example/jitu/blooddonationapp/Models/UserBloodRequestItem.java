package com.example.jitu.blooddonationapp.Models;

public class UserBloodRequestItem {

    private String userid;
    private  String units;
    private  String blood_group;
    private  String city;


    public String getId() {
        return userid;
    }

    public void setId(String userid) {
        this.userid = userid;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
