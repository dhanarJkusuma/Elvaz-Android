package id.telkom.elvaz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import id.telkom.elvaz.R;
import id.telkom.elvaz.model.BluetoothModel;

/**
 * Created by VlovaverA on 15/08/2016.
 */
public class BluetoothBaseAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<BluetoothModel> devices;

    public BluetoothBaseAdapter(Context context, ArrayList<BluetoothModel> devices) {
        this.context = context;
        this.devices = devices;
    }

    public void add(BluetoothModel model)
    {
        devices.add(model);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_bt,parent,false);
        HolderBT holder = new HolderBT();
        holder.deviceName = (TextView) convertView.findViewById(R.id.deviceName);
        holder.status = (TextView) convertView.findViewById(R.id.status);
        convertView.setTag(holder);

        holder.deviceName.setText(devices.get(position).getDevice().getName());
        holder.status.setText((devices.get(position).isPaired()) ? "Paired" : "Not Paired");
        return convertView;
    }

    private class HolderBT
    {
        TextView deviceName;
        TextView status;
    }
}
