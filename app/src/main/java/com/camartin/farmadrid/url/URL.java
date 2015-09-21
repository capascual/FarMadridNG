package com.camartin.farmadrid.url;

/**
 * Created by carlos on 21/9/15.
 */

public class URL {

    private String address;
    private String city;
    private int number;
    private String date;
    private String time;
    private int radius;
    private boolean allNigth;

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public boolean isAllNigth() {
        return allNigth;
    }

    public int getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getRadius() {
        return radius;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAllNigth(boolean allNigth) {
        this.allNigth = allNigth;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public URL(String address, String city, int number, boolean allNigth, String date, String time, int radius) {
        this.address = address;
        this.city = city;
        this.number = number;
        this.allNigth = allNigth;
        this.date = date;
        this.time = time;
        this.radius = radius;
    }

    public String getURL(){
        return "http://www.cofm.es/index.asp?MP=Informacion-Corporativa&MS=" +
                "Buscador-Farmacias&MN=0&accion=si&lanzamiento=buscador&" +
                "buscar_direccion=1" +
                "&fDireccion=" + getAddress() +
                "&fNumero=" + getNumber() +
                "&fMunicipio=" + getCity() +
                "&fFecha=" + getDate() +
                "&fHora=" + getTime() +
                "&fRadio=" + getRadius() +
                "&fGuardia=" + (isAllNigth() ? 1 : 0);
    }
}
