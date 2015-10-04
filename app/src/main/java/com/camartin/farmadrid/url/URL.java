package com.camartin.farmadrid.url;

import com.camartin.farmadrid.R;

/**
 * Created by Carlos Martín-Engeños on 21/9/15.
 */

public class URL {

    private String address;
    private String city;
    private int number;
    private String date;
    private String time;
    private int radius;
    private boolean allNigth;
    private int districtId;


    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }


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

    public URL(String address, String city, int number, String searchMode, String date, String time, int radius) {
        this.address = address;
        this.city = city;
        this.number = number;
        this.allNigth = convertSearchMode(searchMode);
        this.date = date;
        this.time = time;
        this.radius = radius;
    }

    public URL(String district, String searchMode) {
        this.districtId = convertDistrictToId(district);
        this.allNigth = convertSearchMode(searchMode);
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

    public String getURLDistrict(){

        return "http://www.cofm.es/index.asp?MP=Informacion-Corporativa&MS=Buscador-Farmacias&MN=&accion=si&lanzamiento=buscador" +
                "&distrito=" + getDistrictId() +
                "&guardia=" + (isAllNigth() ? 1 : 0);
    }

    private boolean convertSearchMode(String searchMode){
        if(searchMode.equals("De guardia"))
            return true;
        else
            return false;
    }


    private int convertDistrictToId(String district){
        int id;

        if(district.equals("ARAVACA"))
            id = 13;
        else if(district.equals("BARAJAS-ALAMEDA DE OSUNA"))
            id = 14;
        else if(district.equals("CARABANCHEL-LATINA"))
            id = 4;
        else if(district.equals("CENTRO"))
            id = 8;
        else if(district.equals("CHAMARTIN-HORTALEZA"))
            id = 6;
        else if(district.equals("CHAMBERI-MONCLOA"))
            id = 3;
        else if(district.equals("CIUDAD LINEAL-SAN BLAS"))
            id = 7;
        else if(district.equals("FUENCARRAL-BARRIO DEL PILAR"))
            id = 1;
        else if(district.equals("MORATALAZ"))
            id = 12;
        else if(district.equals("PUEBLO DE VALLECAS"))
            id = 15;
        else if(district.equals("PUENTE DE VALLECAS-ENTREVIAS"))
            id = 9;
        else if(district.equals("RETIRO-ARGANZUELA"))
            id = 10;
        else if(district.equals("SALAMANCA-VENTAS"))
            id = 11;
        else if(district.equals("TETUAN-CUATRO CAMINOS"))
            id = 2;
        else if(district.equals("USERA-VILLAVERDE"))
            id = 5;
        else if(district.equals("VICALVARO"))
            id = 10;
        else
            id = 0;

        return id;
    }
}
