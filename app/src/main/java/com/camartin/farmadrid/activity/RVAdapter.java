package com.camartin.farmadrid.activity;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.camartin.farmadrid.R;
import com.camartin.farmadrid.pharmacy.Pharmacy;

import java.util.ArrayList;

/**
 * Created by Carlos Martín-Engeños on 20/9/15.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    // Pharmacy list
    ArrayList<Pharmacy> listPharmacy;

    // TAG
    private static final String TAG = RVAdapter.class.getSimpleName();

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView address;
        TextView city;
        ImageView icon;

        PersonViewHolder(View rootView) {
            super(rootView);
            cv = (CardView)rootView.findViewById(R.id.cv);
            address = (TextView)rootView.findViewById(R.id.address);
            city = (TextView)rootView.findViewById(R.id.city);
            icon = (ImageView)rootView.findViewById(R.id.icon);
            rootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e(TAG, "onClick " + getPosition());

        }
    }

    RVAdapter(ArrayList<Pharmacy> listPharmacy){
        this.listPharmacy = listPharmacy;
    }

    @Override
    public int getItemCount() {
        return listPharmacy.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowlayout, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.address.setText(listPharmacy.get(i).getAddress());
        personViewHolder.city.setText(listPharmacy.get(i).getCity());
        personViewHolder.icon.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

