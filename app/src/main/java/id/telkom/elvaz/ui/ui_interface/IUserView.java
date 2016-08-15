package id.telkom.elvaz.ui.ui_interface;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public interface IUserView
{
    void setAzimuth(String azimuth);
    void setElevation(String elevation);
    void setPolarization(String polarization);
    void setSiteName(String siteName);
    void setEarthLatitude(String latitude);
    void setEarthLongitude(String longitude);

    void setSateliteSite(String siteName);
    void setSateliteLong(String longitude);
    void reValidateEarth();
    void reValidateSatelite();
    Context getRelatedContext();
}
