package id.telkom.elvaz.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import id.telkom.elvaz.util.BluetoothService;

/**
 * Created by VlovaverA on 16/08/2016.
 */
public class BluetoothWrapperConnector implements Parcelable
{
    private Context context;
    private BluetoothService.BluetoothSocketWrapper btService;

    public BluetoothWrapperConnector() {
    }


    public BluetoothWrapperConnector(Context context, BluetoothService.BluetoothSocketWrapper btService) {
        this.context = context;
        this.btService = btService;
    }

    protected BluetoothWrapperConnector(Parcel in) {
    }

    public static final Creator<BluetoothWrapperConnector> CREATOR = new Creator<BluetoothWrapperConnector>() {
        @Override
        public BluetoothWrapperConnector createFromParcel(Parcel in) {
            return new BluetoothWrapperConnector(in);
        }

        @Override
        public BluetoothWrapperConnector[] newArray(int size) {
            return new BluetoothWrapperConnector[size];
        }
    };

    public BluetoothService.BluetoothSocketWrapper getBtService() {
        return btService;
    }

    public void setBtService(BluetoothService.BluetoothSocketWrapper btService) {
        this.btService = btService;
    }

    public Context getContext() {

        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
