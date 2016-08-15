package id.telkom.elvaz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import id.telkom.elvaz.R;
import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.ui.ui_interface.IUserLocation;
import id.telkom.elvaz.ui.ui_interface.IUserLogin;

/**
 * Created by VlovaverA on 06/03/2016.
 */
public class TownAdapter extends BaseAdapter
{
    private ArrayList<EarthStationInformation> data;
    private Context context;
    private IUserLocation location;
    public TownAdapter(Context context,ArrayList<EarthStationInformation> data,IUserLocation location)
    {
        this.context = context;
        this.data = data;
        this.location = location;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_db,parent,false);
        holder = new Holder();
        holder.siteName = (TextView) convertView.findViewById(R.id.siteCard);
        holder.updatedAt = (TextView) convertView.findViewById(R.id.updatedAt);
        View overflow = convertView.findViewById(R.id.album_overflow);
        overflow.setOnClickListener(new OverflowLocation(context,data.get(position),location));
        convertView.setTag(holder);

        holder.siteName.setText(data.get(position).getSiteName());
        holder.updatedAt.setText(data.get(position).getUpdatedAt());
        return convertView;
    }

    private class Holder
    {
        TextView siteName;
        TextView updatedAt;
    }
}


