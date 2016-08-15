package id.telkom.elvaz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.telkom.elvaz.R;
import id.telkom.elvaz.model.EarthStationInformation;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class EarthListAdapter extends ArrayAdapter<EarthStationInformation>
{

    public EarthListAdapter(Context context, ArrayList<EarthStationInformation> earth) {
        super(context,R.layout.dialog_location,earth);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EarthStationInformation posEarth = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_location, parent, false);
        }
        TextView siteName  = (TextView) convertView.findViewById(R.id.siteName);
        siteName.setText(posEarth.getSiteName());

        return convertView;
    }
}
