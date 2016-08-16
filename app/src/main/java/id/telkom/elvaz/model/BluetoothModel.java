package id.telkom.elvaz.model;

import android.bluetooth.BluetoothDevice;

/**
 * Created by VlovaverA on 15/08/2016.
 */
public class BluetoothModel
{
    private boolean isPaired = false;
    private BluetoothDevice device;

    public BluetoothModel() {
    }

    public boolean isPaired() {
        return isPaired;
    }

    public void setIsPaired(boolean isPaired) {
        this.isPaired = isPaired;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }
}
