package id.telkom.elvaz.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.telkom.elvaz.R;
import id.telkom.elvaz.adapter.BluetoothBaseAdapter;
import id.telkom.elvaz.model.BluetoothModel;
import id.telkom.elvaz.model.BluetoothWrapperConnector;
import id.telkom.elvaz.model.LookAngle;
import id.telkom.elvaz.util.ArduinoConnection;
import id.telkom.elvaz.util.ArduinoService;
import id.telkom.elvaz.util.BluetoothService;


public class BluetoothListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private LookAngle lookAngle;
    private Boolean isManual=false;

    @Bind(R.id.listDevice)
    ListView listDevice;
    @Bind(R.id.searchFab)
    FloatingActionButton searchFab;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mmDevice;
    private ArrayList<BluetoothModel> devices;
    private ArrayList<BluetoothModel> pairedDeviceAr;
    private BluetoothBaseAdapter adapter;
    private BluetoothService btService;
    BluetoothService.BluetoothSocketWrapper bluetoothSocketWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        setTitle("Bluetooth devices");

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            lookAngle = bundle.getParcelable("lookAngle");
            isManual = bundle.getBoolean("isManual");
        }

        searchFab.setOnClickListener(this);
        devices = new ArrayList<>();
        pairedDeviceAr = new ArrayList<>();
        adapter = new BluetoothBaseAdapter(this,devices);

        listDevice.setAdapter(adapter);
        listDevice.setOnItemClickListener(this);
        findBT();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null) {
            //myLabel.setText("No bluetooth adapter available");
            Toast.makeText(this,"No bluetooth adapter available",Toast.LENGTH_SHORT).show();
        }

        if(!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(BluetoothDevice device : pairedDevices) {
                BluetoothModel btModel = new BluetoothModel();
                btModel.setDevice(device);
                btModel.setIsPaired(true);
                devices.add(btModel);
                pairedDeviceAr.add(btModel);
                adapter.notifyDataSetChanged();
            }
        }
        //myLabel.setText("Bluetooth Device Found");
        Toast.makeText(BluetoothListActivity.this, "Bluetooth Device Found", Toast.LENGTH_SHORT).show();
    }


    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Message msg = Message.obtain();
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


                    boolean flag = true;    // flag to indicate that particular device is already in the arlist or not
                    for(int i = 0; i<pairedDeviceAr.size();i++)
                    {
                        if (device.getAddress().equals(pairedDeviceAr.get(i).getDevice().getAddress()))
                        {
                            flag = false;
                            mmDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
                        }
                    }
                    if(flag == true)
                    {
                        BluetoothModel btModel = new BluetoothModel();
                        btModel.setDevice(device);
                        btModel.setIsPaired(false);
                        adapter.add(btModel);
                    }

            }
        }
    };

    private void startSearching() {
        Log.i("Log", "in the start searching method");
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        BluetoothListActivity.this.registerReceiver(myReceiver, intentFilter);
        mBluetoothAdapter.startDiscovery();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.searchFab:
                startSearching();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.send:
                if(isManual)
                {
                    Intent manualPage = new Intent(this,RotateActivity.class);
                    manualPage.putExtra("BluetoothDevice",mmDevice);
                    startActivity(manualPage);
                }
                else
                {
                    if(bluetoothSocketWrapper!=null)
                    {
                        if(bluetoothSocketWrapper.getUnderlyingSocket().isConnected())
                        {
                            if(lookAngle!=null)
                            {
                                try
                                {
                                    String message = "A";
                                    message += lookAngle.getAzimuth();
                                    message += "|";
                                    message += lookAngle.getElevation();
                                    bluetoothSocketWrapper.getOutputStream().write(message.getBytes());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(this,"ERROR : " + e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(BluetoothListActivity.this,"Bluetooth not Connected.",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(BluetoothListActivity.this,"Bluetooth socket wrapper not available.",Toast.LENGTH_LONG).show();
                    }
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getPairedDevices() {
        devices.clear();
        Set<BluetoothDevice> pairedDevice = mBluetoothAdapter.getBondedDevices();
        if(pairedDevice.size()>0)
        {
            for(BluetoothDevice device : pairedDevice)
            {
                BluetoothModel btModel = new BluetoothModel();
                btModel.setDevice(device);
                btModel.setIsPaired(true);
                adapter.add(btModel);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if(bluetoothSocketWrapper!=null)
            {
                bluetoothSocketWrapper.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.listDevice:

                BluetoothModel btModel = devices.get(position);
                if(isManual)
                {
                    mmDevice = btModel.getDevice();
                    Toast.makeText(BluetoothListActivity.this,"Device selected.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    btService = new BluetoothService(btModel.getDevice(),true,mBluetoothAdapter,new ArrayList<UUID>());
                    try {
                        bluetoothSocketWrapper = btService.connect();
                        Toast.makeText(BluetoothListActivity.this,"Bluetooth Connected.",Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

                break;
        }
    }
}








