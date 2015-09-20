package com.camartin.farmadrid.activity;

import android.os.AsyncTask;
import android.util.Log;

import com.camartin.farmadrid.pharmacy.Pharmacy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by carlos on 17/9/15.
 */

// Runs a long running task in a thread
// Note that all the UI activities are only executed in Pre/Post methods
// Also all UI activities are delegated to the fragment passed in constructor
public class URLFetchTask extends AsyncTask<String, Void, ArrayList<Pharmacy>>{

    private static final String TAG = "URLFecthTask";
    private static final int SIZE = 10;

    DistanceFragment container;
    public URLFetchTask(DistanceFragment f) {
        this.container = f;
    }

    @Override
    protected ArrayList<Pharmacy> doInBackground(String... params) {

        String body = "";

        try {
            Log.v(TAG, "Requesting...");
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == 200){//Vemos si es 200 OK y leemos el cuerpo del mensaje.
                body = readStream(urlConnection.getInputStream());
            }
            urlConnection.disconnect();
            return parseResponse(body);
        } catch (MalformedURLException e) {
            body = e.toString(); //Error URL incorrecta
        } catch (SocketTimeoutException e){
            body = e.toString(); //Error: Finalizado el timeout esperando la respuesta del servidor.
        } catch (Exception e) {
            body = e.toString();//Error diferente a los anteriores.
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        container.showProgressBar();
    }

    @Override
    protected void onPostExecute(ArrayList<Pharmacy> listPharmacy) {
        super.onPostExecute(listPharmacy);
        // The activity can be null if it is thrown out by Android while task is running!
        if(container!=null && container.getActivity()!=null) {
            for(int i = 0; i < listPharmacy.size(); i++) {
                Log.v(TAG, "address(" + i + "): " + listPharmacy.get(i).getAddress());
            }
            container.populateResult(listPharmacy);
            container.hideProgressBar();
            this.container = null;
        }
    }

    private String readStream(InputStream in) throws IOException{

        BufferedReader r = null;
        r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        if(r != null){
            r.close();
        }
        in.close();
        return total.toString();
    }

    private ArrayList<Pharmacy> parseResponse(String rsp){

        String[] addresses = new String[SIZE];
        int[] zips = new int[SIZE];
        String[] cities = new String[SIZE];
        float[] latitudes = new float[SIZE];
        float[] longitudes = new float[SIZE];
        String coord = "";
        int count = 0;
        int count2 = 0;
        ArrayList<Pharmacy> listPharmacy = new ArrayList <> ();

        Log.v(TAG, "Parsing cities...");
        // Parsing cities
        Pattern p0 = Pattern.compile("<strong>Poblaci&oacute;n:</strong> (.*?)</li>");
        Matcher m0 = p0.matcher(rsp);
        while(m0.find()) {
            cities[count] = m0.group(1);
            Log.v(TAG, "New city: " + cities[count]);
            count++;
        }
        count = 0;
        Log.v(TAG, "Done!");


        Log.v(TAG, "Parsing zips...");
        // Parsing zips
        Pattern p1 = Pattern.compile("<li><strong>C&oacute;digo Postal:</strong> (.*?)</li>");
        Matcher m1 = p1.matcher(rsp);
        while(m1.find()) {
            zips[count] = Integer.parseInt(m1.group(1));
            Log.v(TAG, "New zip: " + zips[count]);
            count++;
        }
        count = 0;
        Log.v(TAG, "Done!");

        Log.v(TAG, "Parsing coordinates...");
        // Parsing coordinates
        Pattern p3 = Pattern.compile("Array((.*?));");
        Matcher m3 = p3.matcher(rsp);
        while(m3.find()){
            if (!m3.group(1).equals("()")){
                coord = m3.group(1);
                coord = coord.substring(1, coord.length());
                coord = coord.substring(0, coord.length()-1);
                String[] coordArray = coord.split(",");
                latitudes[count] = Float.parseFloat(coordArray[0].replaceAll("\\s+",""));
                longitudes[count] = Float.parseFloat(coordArray[1].replaceAll("\\s+",""));
                Log.v(TAG, "New latitude: " + latitudes[count]);
                Log.v(TAG, "New longitude: " + longitudes[count]);
                count++;
            }
        }
        Log.v(TAG, "Done!");

        Log.v(TAG, "Parsing addresses...");
        // Parsing address
        for(int i=0; i < count; i++){
            String patron = "aTextos." + i + ". = '<p><strong>Direcci&oacute;n: </strong>(.*?)</p>';";
            Pattern p2 = Pattern.compile(patron);
            Matcher m2 = p2.matcher(rsp);
            while(m2.find()){
                addresses[count2] = m2.group(1);
                Log.v(TAG, "New address: " + addresses[count2]);
                count2++;
            }
        }
        Log.v(TAG, "Done!");

        for(int i = 0; i < SIZE; i++){
            Log.v(TAG, "i: " + i);
            Log.v(TAG, "address: " + addresses[i]);
            Log.v(TAG, "city: " + cities[i]);
            Log.v(TAG, "zip: " + zips[i]);
            Log.v(TAG, "latitude: " + latitudes[i]);
            Log.v(TAG, "longitude: " + longitudes[i]);
        }

        for(int i = 0; i < SIZE; i++)
            listPharmacy.add(new Pharmacy(addresses[i], zips[i], cities[i], latitudes[i], longitudes[i]));

        return listPharmacy;
    }
}
