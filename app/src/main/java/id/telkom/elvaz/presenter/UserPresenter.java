package id.telkom.elvaz.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;

import id.telkom.elvaz.adapter.EarthListAdapter;
import id.telkom.elvaz.adapter.SateliteListAdapter;
import id.telkom.elvaz.database.TownsDatabase;
import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.model.LookAngle;
import id.telkom.elvaz.model.SateliteInformation;
import id.telkom.elvaz.ui.ui_interface.IUserView;
import id.telkom.elvaz.util.Calculate;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class UserPresenter
{
    private IUserView iUser;
    private TownsDatabase earthData;
    private EarthListAdapter earthListAdapter;
    private SateliteListAdapter sateliteListAdapter;

    ArrayList<EarthStationInformation> dataEarthLocation;

    public UserPresenter(IUserView iUser)
    {
        this.iUser = iUser;
        this.earthData = new TownsDatabase(iUser.getRelatedContext());
        this.dataEarthLocation = earthData.getTowns();
        this.earthListAdapter = new EarthListAdapter(iUser.getRelatedContext(), dataEarthLocation);
        this.sateliteListAdapter = new SateliteListAdapter(iUser.getRelatedContext(),getSateliteData());
    }

    private ArrayList<SateliteInformation> getSateliteData()
    {
        ArrayList<SateliteInformation> satelites = new ArrayList<>();
        satelites.add(new SateliteInformation("Telkom 1",108.0));
        satelites.add(new SateliteInformation("Telkom 2",118.0));
        return satelites;
    }

    public LookAngle getLookAngle(EarthStationInformation earth,SateliteInformation satelite)
    {
        LookAngle lookAngle = Calculate.getLookAngle(earth, satelite);
        iUser.setAzimuth("Azimuth : " + String.valueOf(lookAngle.getAzimuth()));
        iUser.setElevation("Elevation : " + String.valueOf(lookAngle.getElevation()));
        iUser.setPolarization("Polarization : " + String.valueOf(lookAngle.getPolarization()));
        return lookAngle;
    }

    public void getDialogEarthList()
    {
        AlertDialog dialog = new AlertDialog.Builder(iUser.getRelatedContext())
                .setTitle("Location : ")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setAdapter(earthListAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        iUser.setSiteName(dataEarthLocation.get(which).getSiteName());
                        iUser.setEarthLatitude(String.valueOf(dataEarthLocation.get(which).getLatitude()));
                        iUser.setEarthLongitude(String.valueOf(dataEarthLocation.get(which).getLongitude()));
                        iUser.reValidateEarth();
                    }
                })
                .show();

    }

    public void getDialogSatelitList()
    {
        AlertDialog dialog = new AlertDialog.Builder(iUser.getRelatedContext())
                .setTitle("Location : ")
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setAdapter(sateliteListAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        iUser.setSateliteSite(getSateliteData().get(which).getSateliteName());
                        iUser.setSateliteLong(String.valueOf(getSateliteData().get(which).getLongitude()));
                        iUser.reValidateSatelite();
                    }
                })
                .show();
    }
}
