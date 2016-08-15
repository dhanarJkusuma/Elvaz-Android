package id.telkom.elvaz.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


import java.util.ArrayList;

import id.telkom.elvaz.R;
import id.telkom.elvaz.adapter.TownAdapter;
import id.telkom.elvaz.database.TownsDatabase;
import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.ui.ui_interface.IUserLocation;
import id.telkom.elvaz.ui.ui_interface.IUserLogin;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VlovaverA on 06/03/2016.
 */
public class LocationPresenter
{
    private IUserLocation iLocation;
    private TownsDatabase dbTown;
    TownAdapter adapter;

    public LocationPresenter(IUserLocation locationi)
    {
        iLocation = locationi;
        dbTown = new TownsDatabase(iLocation.getRelatedContext());

    }

    public void saveData(String sitename,String latitude,String longitude, final DialogInterface dialogInterface)
    {
        EarthStationInformation data = new EarthStationInformation();
        data.setSiteName(sitename);
        data.setLatitude(Double.parseDouble(latitude));
        data.setLongitude(Double.parseDouble(longitude));
        dbTown.saveRxTown(data).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                dialogInterface.cancel();
            }

            @Override
            public void onNext(Long aLong) {
                dialogInterface.cancel();
                adapter.notifyDataSetChanged();
                setListView();
            }
        });

    }

    public void updateData(int old_id,EarthStationInformation earthStationInformation, final DialogInterface dialogInterface)
    {
        dbTown.updateRxTown(old_id, earthStationInformation)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        dialogInterface.dismiss();
                        adapter.notifyDataSetChanged();
                        setListView();
                    }
                });
    }

    public void deleteData(int id, final DialogInterface dialogInterface)
    {
        dbTown.deleteRxTown(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        dialogInterface.dismiss();
                        adapter.notifyDataSetChanged();
                        adapter = null;
                        setListView();
                    }
                });
    }

    public void setListView()
    {
        dbTown.getRxTown()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<EarthStationInformation>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<EarthStationInformation> earthStationInformations) {
                        adapter = new TownAdapter(iLocation.getRelatedContext(), earthStationInformations,iLocation);
                        iLocation.setAdapter(adapter);
                        iLocation.getListView();
                    }
                });
    }


}
