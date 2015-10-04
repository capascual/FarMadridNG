package com.camartin.farmadrid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.camartin.farmadrid.R;
import com.camartin.farmadrid.pharmacy.Pharmacy;
import com.camartin.farmadrid.url.URL;

import java.util.ArrayList;

/**
 * Created by Carlos Martín-Engeños on 29/07/15.
 */
public class CityFragment extends Fragment {

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

    // Title district
    private TextView tv;

    // URL
    private URL url;

    // Spinner
    private Spinner spinner;

    // Button
    private Button button;

    // TAG
    private static String TAG = CityFragment.class.getSimpleName();


    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_city, container, false);

        rv = (RecyclerView)rootView.findViewById(R.id.rv_cities);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        // Spinner
        spinner = (Spinner) rootView.findViewById(R.id.list_cities);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cities_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button = (Button) rootView.findViewById(R.id.search_city);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Get the selected district
                String district = spinner.getSelectedItem().toString();

                // Construct URL object
                url = new URL(district, FragmentDrawer.spinner.getSelectedItem().toString());

                // HTTP Get Request
                startURLFetch(url.getURLDistrict());
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Sync UI state to current fragment and task state
        if(isTaskRunning(mTask))
            showProgressBar();
        else
            hideProgressBar();

        if (mResult != null)
            populateResult(mResult);

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
        mTask = new URLFetchTask(this, URLFetchTask.FragmentType.CITY);
        mTask.execute(urlS);
    }

    public void showProgressBar() {
        TextView tv = (TextView)getActivity().findViewById(R.id.titleCity);
        tv.setVisibility(View.GONE);
        Spinner spinner = (Spinner)getActivity().findViewById(R.id.list_cities);
        spinner.setVisibility(View.GONE);
        Button button = (Button)getActivity().findViewById(R.id.search_city);
        button.setVisibility(View.GONE);
        RecyclerView rv = (RecyclerView)getActivity().findViewById(R.id.rv_cities);
        rv.setVisibility(View.GONE);
        ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.progressBarCity);
        progress.setVisibility(View.VISIBLE);
        progress.setIndeterminate(true);
    }

    public void hideProgressBar() {
        RecyclerView rv = (RecyclerView)getActivity().findViewById(R.id.rv_cities);
        rv.setVisibility(View.VISIBLE);
        ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.progressBarCity);
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
