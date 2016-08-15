package id.telkom.elvaz.ui.ui_interface;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import id.telkom.elvaz.adapter.TownAdapter;
import id.telkom.elvaz.model.EarthStationInformation;

/**
 * Created by VlovaverA on 06/03/2016.
 */
public interface IUserLocation
{
    void setAdapter(TownAdapter adapter);
    ListView getListView();
    Context getRelatedContext();
    void getUpdateView(int old_id,EarthStationInformation earthStationInformation);
    void getDeleteView(int id);
}
