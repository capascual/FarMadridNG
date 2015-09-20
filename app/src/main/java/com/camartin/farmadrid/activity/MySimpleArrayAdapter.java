package com.camartin.farmadrid.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.camartin.farmadrid.R;


/**
 * Created by carlos on 19/9/15.
 */

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView addrTV = (TextView) rowView.findViewById(R.id.address);
        TextView cityTV = (TextView) rowView.findViewById(R.id.city);
        //TextView zipTV = (TextView) rowView.findViewById(R.id.zip);
        //TextView distanceTV = (TextView) rowView.findViewById(R.id.distance);

        //ImageView imageView = (ImageView) rowView.findViewById(R.id.iconPharmacy);
        addrTV.setText(values[position]);
        cityTV.setText("GETAFE");
        //zipTV.setText(values[position]);
        //distanceTV.setText("50 Km");

        // Change the icon for Windows and iPhone
        String s = values[position];
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {
            //imageView.setImageResource(R.drawable.ko);
        } else {
            //imageView.setImageResource(R.drawable.ok);
        }

        return rowView;
    }
}