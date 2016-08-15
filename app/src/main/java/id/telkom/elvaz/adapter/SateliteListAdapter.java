package id.telkom.elvaz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.telkom.elvaz.R;
import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.model.SateliteInformation;

/**
 * Created by VlovaverA on 30/03/2016.
 */
public class SateliteListAdapter extends ArrayAdapter<SateliteInformation>
{

    public SateliteListAdapter(Context context, ArrayList<SateliteInformation> satelite) {
        super(context,R.layout.dialog_location,satelite);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SateliteInformation sateliteInformation = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_location, parent, false);
        }
        TextView siteName  = (TextView) convertView.findViewById(R.id.siteName);
        siteName.setText(sateliteInformation.getSateliteName());

        return convertView;
    }
}
