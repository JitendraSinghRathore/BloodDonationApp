package com.example.jitu.blooddonationapp.Models;

public class AllRequestItems {
    private String request_id,name,mobile,email,latitude,longitude,street,city,state,country,blood_group,units,image;

    public String getImage(int position) {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRequest_id(int position) {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getName(int position) {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile(int position) {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBlood_group(int position) {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getUnits(int position) {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

}
