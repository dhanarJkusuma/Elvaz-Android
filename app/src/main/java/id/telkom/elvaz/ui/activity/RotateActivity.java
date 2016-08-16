package id.telkom.elvaz.ui.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.telkom.elvaz.R;
import id.telkom.elvaz.model.BluetoothWrapperConnector;
import id.telkom.elvaz.util.BluetoothService;

public class RotateActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.bLeft)
    Button bLeft;
    @Bind(R.id.bRight)
    Button bRight;
    @Bind(R.id.bUp)
    Button bUp;
    @Bind(R.id.bDown)
    Button bDown;

    private BluetoothAdapter btAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothService.BluetoothSocketWrapper bluetoothSocketWrapper;
    private BluetoothService btService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Manual Rotate");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            bluetoothDevice = bundle.getParcelable("BluetoothDevice");

        }
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        btService = new BluetoothService(bluetoothDevice,true,btAdapter,new ArrayList<UUID>());
        try {

            bluetoothSocketWrapper= btService.connect();
            bluetoothSocketWrapper.getOutputStream().write("MANUAL GAN".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        setEvent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent()
    {
        bLeft.setOnClickListener(this);
        bRight.setOnClickListener(this);
        bUp.setOnClickListener(this);
        bDown.setOnClickListener(this);

    }

    private void rotate(String message)
    {
        try {
            bluetoothSocketWrapper.getOutputStream().write(message.getBytes());
        } catch (IOException e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(bluetoothSocketWrapper!=null)
        {
            try
            {
                bluetoothSocketWrapper.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.bLeft:
                rotate("L");
                break;
            case R.id.bRight:
                rotate("R");
                break;
            case R.id.bUp:
                rotate("U");
                break;
            case R.id.bDown:
                rotate("D");
                break;
        }
    }
}
