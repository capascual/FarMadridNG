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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.camartin.farmadrid.R;
import com.camartin.farmadrid.pharmacy.Pharmacy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by camartin on 14/09/15.
 */

public class DistanceFragment extends Fragment {

    private static final String TAG = "DistanceFragment";
    View mMainView;
    ArrayList<Pharmacy> mResult;
    URLFetchTask mTask;

    View rootView;
    RecyclerView rv;
    RVAdapter adapter;

    String url = "http://www.cofm.es/index.asp?MP=Informacion-Corporativa&MS=Buscador-Farmacias&MN=0&accion=si&lanzamiento=buscador&buscar_direccion=1&fDireccion=calle+valdemorillo&fNumero=1&fMunicipio=GETAFE&fFecha=14/09/2015&fHora=20:34&fRadio=5&fGuardia=0";

    public DistanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_distance, container, false);

        rv = (RecyclerView)rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        // HTTP Get Request
        startURLFetch(url);

        // on configuration changes (screen rotation) we want fragment member variables to preserved
        setRetainInstance(true);

        // Inflate the layout for this fragment
        return rootView;
    }

    protected void startURLFetch(String urlS) {
        mTask = new URLFetchTask(this);
        mTask.execute(urlS);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Sync UI state to current fragment and task state
        if(isTaskRunning(mTask)) {
            showProgressBar();
        }else {
            hideProgressBar();
        }
        if(mResult!=null) {
            populateResult(mResult);
        }
        super.onActivityCreated(savedInstanceState);
    }

    public void showProgressBar() {
        rv = (RecyclerView)rootView.findViewById(R.id.rv);
        rv.setVisibility(View.GONE);
        //ListView listView = (ListView)rootView.findViewById(R.id.list);
        //listView.setVisibility(View.VISIBLE);
        ProgressBar progress = (ProgressBar)rootView.findViewById(R.id.progressBarFetch);
        progress.setVisibility(View.VISIBLE);
        progress.setIndeterminate(true);
    }

    public void hideProgressBar() {
        rv = (RecyclerView)getActivity().findViewById(R.id.rv);
        rv.setVisibility(View.VISIBLE);
        //ListView listVIew = (ListView)getActivity().findViewById(R.id.list);
        //listVIew.setVisibility(View.VISIBLE);
        ProgressBar progress = (ProgressBar)getActivity().findViewById(R.id.progressBarFetch);
        progress.setVisibility(View.GONE);
    }

    public void populateResult(ArrayList<Pharmacy> listPharmacy) {
        //String[] values = new String[10];

        //for(int i = 0; i < listPharmacy.size(); i++)
        //    values[i] = listPharmacy.get(i).getAddress();

        adapter = new RVAdapter(listPharmacy);
        rv.setAdapter(adapter);

        //ListView listView = (ListView)getActivity().findViewById(R.id.list);
        //MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this.getActivity(), values);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
        //        android.R.layout.simple_list_item_1, values);

        //listView.setAdapter(adapter);
    }

    protected boolean isTaskRunning(URLFetchTask task) {
        if(task == null ) {
            return false;
        } else if(task.getStatus() == URLFetchTask.Status.FINISHED){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }
}