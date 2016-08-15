package id.telkom.elvaz.adapter;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import id.telkom.elvaz.R;
import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.ui.ui_interface.IUserLocation;

/**
 * Created by VlovaverA on 06/03/2016.
 */
public class OverflowLocation implements View.OnClickListener
{
        private EarthStationInformation info;
        private Context mContext;
        private IUserLocation location;
        public OverflowLocation(Context context, EarthStationInformation album,IUserLocation location) {
            mContext = context;
            info = album;
            this.location = location;
        }

        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(mContext, v) {
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.updateMenu:
                            location.getUpdateView(info.getId(),info);
                            break;
                        case R.id.deleteMenu:
                            location.getDeleteView(info.getId());
                            break;

                    }
                    return super.onMenuItemSelected(menu, item);
                }
            };

            popupMenu.inflate(R.menu.pop_up);

            popupMenu.show();
        }
    }

