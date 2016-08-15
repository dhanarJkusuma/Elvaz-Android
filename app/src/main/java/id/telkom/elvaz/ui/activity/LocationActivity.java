package id.telkom.elvaz.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.telkom.elvaz.R;
import id.telkom.elvaz.adapter.TownAdapter;
import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.presenter.LocationPresenter;
import id.telkom.elvaz.ui.ui_interface.IUserLocation;

public class LocationActivity extends AppCompatActivity implements IUserLocation{

    @Bind(R.id.listDb)
    ListView listDb;



    private LocationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("Location Managament");
        presenter = new LocationPresenter(this);
        presenter.setListView();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddView();
            }
        });

    }

    @Override
    public void setAdapter(TownAdapter adapter)
    {
        listDb.setAdapter(adapter);
    }

    @Override
    public ListView getListView() {
        return listDb;
    }


    @Override
    public Context getRelatedContext() {
        return this;
    }

    @Override
    public void getUpdateView(final int old_id,EarthStationInformation information)
    {
        View view = null;
        AlertDialog.Builder abuilder = new AlertDialog.Builder(this,R.style.myDialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.dialog_add_location,null);
        final EditText siteName = (EditText) view.findViewById(R.id.aSiteName);
        final EditText latitude = (EditText) view.findViewById(R.id.aLatitude);
        final EditText longitude = (EditText) view.findViewById(R.id.aLongitude);
        siteName.setText(information.getSiteName());
        latitude.setText(String.valueOf(information.getLatitude()));
        longitude.setText(String.valueOf(information.getLongitude()));
        final EarthStationInformation earthStationInformation = new EarthStationInformation();

        abuilder
                .setTitle("Update Location")
                .setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        earthStationInformation.setId(old_id);
                        earthStationInformation.setSiteName(siteName.getText().toString());
                        earthStationInformation.setLatitude(Double.parseDouble(latitude.getText().toString()));
                        earthStationInformation.setLongitude(Double.parseDouble(longitude.getText().toString()));
                        presenter.updateData(old_id,earthStationInformation,dialog);
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void getDeleteView(final int id)
    {
        AlertDialog.Builder abuilder = new AlertDialog.Builder(this);
        abuilder
                .setTitle("Delete Location")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteData(id,dialog);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.setting:
                Intent i = new Intent(this,SettingActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_menu,menu);
        return true;
    }

    public void getAddView()
    {
        View view = null;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.myDialog);
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.dialog_add_location,null);
        final EditText siteName = (EditText) view.findViewById(R.id.aSiteName);
        final EditText latitude = (EditText) view.findViewById(R.id.aLatitude);
        final EditText longitude = (EditText) view.findViewById(R.id.aLongitude);

        dialog
                    .setTitle("Add new location")
                    .setView(view)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                presenter.saveData(siteName.getText().toString(),latitude.getText().toString(),longitude.getText().toString(),dialog);
                            }
                        })
                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
    }



}
