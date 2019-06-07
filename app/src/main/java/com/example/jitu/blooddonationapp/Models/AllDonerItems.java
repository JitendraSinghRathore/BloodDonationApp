package com.example.jitu.blooddonationapp.Models;

public class AllDonerItems
{
   private String user_id,name,blood_group,mobile,distance;
    private double latitude,longitude;
    String image;

    public String getDistance() {
        return distance;
    }

    public String getImage(int position) {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }





    public String getMobile(int position) {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_id(int position) {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName(int position) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBlood_group(int position) {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }


    public boolean getUser_id() {
            return false;
    }
}

