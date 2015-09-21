package com.camartin.farmadrid.activity;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.camartin.farmadrid.url.URL;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.camartin.farmadrid.R;
import com.camartin.farmadrid.pharmacy.Pharmacy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by camartin on 14/09/15.
 */

public class DistanceFragment extends Fragment implements ConnectionCallbacks,
        OnConnectionFailedListener {

    // TAG
    private static final String TAG = MainActivity.class.getSimpleName();
    // Constant
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    // List pharmacies
    private ArrayList<Pharmacy> mResult;

    // Task
    private URLFetchTask mTask;

    // View
    private View rootView;

    // Recycler View
    private RecyclerView rv;

    // My Adapter
    private RVAdapter adapter;

    // Location
    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // URL
    private URL url;

    // Required empty public constructor
    public DistanceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_distance, container, false);

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        // Get current position
        // First we need to check availability of play services
        if (checkPlayServices())
            // Building the GoogleApi client
            buildGoogleApiClient();

        // display location
        displayLocation();

        // on configuration changes (screen rotation) we want fragment member variables to preserved
        setRetainInstance(true);

        // Inflate the layout for this fragment
        return rootView;
    }


    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {

        // Address
        List<Address> addresses = null;
        String address;
        String tokens[];
        String number;

        // Geocoder
        Geocoder geocoder = new Geocoder(getActivity());

        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            try {
                addresses = geocoder.getFromLocation(
                        mLastLocation.getLatitude(),
                        mLastLocation.getLongitude(),
                        1);

                address = addresses.get(0).getAddressLine(0).replaceAll("Lugar ", "");
                tokens = address.split(",");
                address = tokens[0];
                number = tokens[1];
                address.replaceAll("\\s+", "+");

                Log.v(TAG, "full: " + addresses.get(0));
                Log.v(TAG, "address: " + address);
                Log.v(TAG, "number: " + number);
                Log.v(TAG, "city: " + addresses.get(0).getAddressLine(2));

                Toast.makeText(getActivity(), "Address: " + address +", number: " + number, Toast.LENGTH_LONG).show();

                // Construct URL object
                url = new URL("Calle+Valdemorillo", "Getafe", 1, false, "14/09/2015", "20:35", 5);

                // HTTP Get Request
                startURLFetch(url.getURL());

            } catch (  IOException e) {
                e.printStackTrace();
            }

        } else
            Log.v(TAG, "Couldn't get the location. Make sure location is enabled on the device");
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                //finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        // Once connected with google api, get the location
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Sync UI state to current fragment and task state
        if(isTaskRunning(mTask)) {
            showProgressBar();
        }else {
            hideProgressBar();
        }
        if (mResult != null) {
            populateResult(mResult);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Task methods
     */
    protected void startURLFetch(String urlS) {
        mTask = new URLFetchTask(this);
        mTask.execute(urlS);
    }

    public void showProgressBar() {
        rv = (RecyclerView)rootView.findViewById(R.id.rv);
        rv.setVisibility(View.GONE);
        ProgressBar progress = (ProgressBar)rootView.findViewById(R.id.progressBarFetch);
        progress.setVisibility(View.VISIBLE);
        progress.setIndeterminate(true);
    }

    public void hideProgressBar() {
        rv = (RecyclerView)getActivity().findViewById(R.id.rv);
        rv.setVisibility(View.VISIBLE);
        ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.progressBarFetch);
        progress.setVisibility(View.GONE);
    }

    public void populateResult(ArrayList<Pharmacy> listPharmacy) {
        adapter = new RVAdapter(listPharmacy);
        rv.setAdapter(adapter);
    }

    protected boolean isTaskRunning(URLFetchTask task) {
        if(task == null )
            return false;
        else if(task.getStatus() == URLFetchTask.Status.FINISHED)
            return false;
        else
            return true;
    }
}