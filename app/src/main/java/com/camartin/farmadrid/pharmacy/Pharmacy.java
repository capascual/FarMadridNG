package com.camartin.farmadrid.pharmacy;

/**
 * Created by carlos on 19/9/15.
 */

public class Pharmacy {

    private String address;
    private int zip;
    private String city;
    private float latitude;
    private float longitude;

    public Pharmacy(String address, int zip, String city, float latitude, float longitude) {
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
    get and set methods
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
