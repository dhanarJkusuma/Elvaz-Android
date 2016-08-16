package id.telkom.elvaz.ui.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.telkom.elvaz.R;
import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.model.LookAngle;
import id.telkom.elvaz.model.SateliteInformation;
import id.telkom.elvaz.presenter.UserPresenter;
import id.telkom.elvaz.ui.activity.BluetoothListActivity;
import id.telkom.elvaz.ui.ui_interface.IUserView;
import id.telkom.elvaz.util.Validation;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements IUserView, View.OnClickListener, View.OnFocusChangeListener {
    @Bind(R.id.iSiteName)
    EditText iSiteName;
    @Bind(R.id.iLatitude)
    EditText iLatitude;
    @Bind(R.id.iLongitude)
    EditText iLongitude;
    @Bind(R.id.iSateliteName)
    EditText iSateliteName;
    @Bind(R.id.iSateliteLongitude)
    EditText iSateliteLongitude;

    @Bind(R.id.rAzimuth)
    TextView rAzimuth;
    @Bind(R.id.rElevation)
    TextView rElevation;
    @Bind(R.id.rPolar)
    TextView rPolar;


    @Bind(R.id.lSiteName)
    TextInputLayout lSiteName;
    @Bind(R.id.lLatitude)
    TextInputLayout lLatitude;
    @Bind(R.id.lLongitude)
    TextInputLayout lLongitude;
    @Bind(R.id.lSateliteName)
    TextInputLayout lSateliteName;
    @Bind(R.id.lSateliteLongitude)
    TextInputLayout lSateliteLongitude;

    @Bind(R.id.bHitung)
    Button bCalculate;
    @Bind(R.id.bSearchLocation)
    Button bSearchLocation;
    @Bind(R.id.bSelectSatelite)
    Button bSelectSatelite;
    @Bind(R.id.bLocate)
    Button bLocate;
    private LookAngle lookAngle;
    private UserPresenter presenter;

    public UserFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this,rootView);
        presenter = new UserPresenter(this);
        bCalculate.setOnClickListener(this);
        bSearchLocation.setOnClickListener(this);
        bSelectSatelite.setOnClickListener(this);
        bLocate.setOnClickListener(this);
        leaveFocusListener();
        return rootView;
    }

    public void leaveFocusListener()
    {
        iLatitude.setOnFocusChangeListener(this);
        iLongitude.setOnFocusChangeListener(this);
        iSateliteLongitude.setOnFocusChangeListener(this);
    }

    private void getData()
    {
        EarthStationInformation earth = new EarthStationInformation();
        earth.setSiteName(iSiteName.getText().toString());
        earth.setLatitude(Double.parseDouble(Validation.parseComa(iLatitude.getText().toString())));
        earth.setLongitude(Double.parseDouble(Validation.parseComa(iLongitude.getText().toString())));

        SateliteInformation satelite = new SateliteInformation();
        satelite.setSateliteName(iSateliteName.getText().toString());
        satelite.setLongitude(Double.parseDouble(Validation.parseComa(iSateliteLongitude.getText().toString())));

        lookAngle = presenter.getLookAngle(earth, satelite);


    }

    @Override
    public void onResume() {
        super.onResume();
        presenter = new UserPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bHitung:
                if(Validation.getValidNormalText(iLatitude) && Validation.getValidNormalText(iLongitude) && Validation.getValidNormalText(iSateliteLongitude))
                {
                    getData();
                }
                else
                {
                    Toast.makeText(getActivity(),"Valid Data cannot be null",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bSearchLocation:
                presenter.getDialogEarthList();
                break;
            case R.id.bSelectSatelite:
                presenter.getDialogSatelitList();
                break;
            case R.id.bLocate:
                AlertDialog.Builder alertChoice = new AlertDialog.Builder(getContext());
                alertChoice.setTitle("Pointing");
                if(lookAngle!=null)
                {
                    alertChoice.setMessage(
                            "Choose your pointing method : \n current site  \n(" + iSiteName.getText().toString() + ") "
                    );
                }
                else
                {
                    alertChoice.setMessage(
                            "Choose your pointing method"
                    );
                }

                alertChoice.setPositiveButton("Automatic", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Otomatis
                        if(lookAngle!=null)
                        {
                            Intent pointPage = new Intent(getContext(), BluetoothListActivity.class);
                            pointPage.putExtra("lookAngle",lookAngle);
                            pointPage.putExtra("isManual",false);
                            startActivity(pointPage);
                        }
                        else
                        {
                            showMessage("Azimuth and Elevation not defined. Please fill the form above and calculate.");
                        }

                    }
                });
                alertChoice.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Tidak
                        dialog.dismiss();
                    }
                });
                alertChoice.setNeutralButton("Manual", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Manual gan
                        Intent pointPage = new Intent(getContext(), BluetoothListActivity.class);
                        pointPage.putExtra("isManual",true);
                        startActivity(pointPage);
                    }
                });
                alertChoice.show();


        }
    }

    public void showMessage(String message)
    {
        AlertDialog.Builder messageAlert = new AlertDialog.Builder(getContext());
        messageAlert
                .setMessage(message)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }



    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        switch (v.getId())
        {
            case R.id.iLatitude :
                if(!hasFocus)Validation.NormalTextValidation(iLatitude,"Earth Latitude",lLatitude);
                break;
            case R.id.iLongitude :
                if(!hasFocus)Validation.NormalTextValidation(iLongitude,"Earth Longitude",lLongitude);
                break;
            case R.id.iSateliteLongitude :
                if(!hasFocus)Validation.NormalTextValidation(iSateliteLongitude,"Satelite Longitude",lSateliteLongitude);
                break;

        }
    }

    @Override
    public void setAzimuth(String azimuth) {
        rAzimuth.setText(azimuth);
    }

    @Override
    public void setElevation(String elevation) {
        rElevation.setText(elevation);
    }

    @Override
    public void setPolarization(String polarization) {
        rPolar.setText(polarization);
    }

    @Override
    public void setSiteName(String siteName) {
        iSiteName.setText(siteName);
    }

    @Override
    public void setEarthLatitude(String latitude) {
        iLatitude.setText(latitude);
    }

    @Override
    public void setEarthLongitude(String longitude) {
        iLongitude.setText(longitude);
    }

    @Override
    public void setSateliteSite(String siteName) {
        iSateliteName.setText(siteName);
    }

    @Override
    public void setSateliteLong(String longitude) {
        iSateliteLongitude.setText(longitude);
    }

    @Override
    public void reValidateEarth() {
        Validation.NormalTextValidation(iLatitude,"Earth Latitude",lLatitude);
        Validation.NormalTextValidation(iLongitude,"Earth Longitude",lLongitude);
    }

    @Override
    public void reValidateSatelite() {
        Validation.NormalTextValidation(iSateliteLongitude,"Satelite Longitude",lSateliteLongitude);
    }

    @Override
    public Context getRelatedContext() {
        return getContext();
    }


}
