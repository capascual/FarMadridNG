package com.camartin.farmadrid.activity;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.camartin.farmadrid.R;
import com.camartin.farmadrid.pharmacy.Pharmacy;

import java.util.ArrayList;

/**
 * Created by carlos on 20/9/15.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
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
        }
    }

    ArrayList<Pharmacy> listPharmacy;

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
        //personViewHolder.icon.setImageResource(listPharmacy.get(i).photoId);
        personViewHolder.icon.setImageResource(R.mipmap.ic_launcher);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

