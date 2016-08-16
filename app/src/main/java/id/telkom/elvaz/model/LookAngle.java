package id.telkom.elvaz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class LookAngle implements Parcelable
{
    private double azimuth;
    private double elevation;
    private double polarization;
    public LookAngle()
    {

    }

    protected LookAngle(Parcel in) {
        azimuth = in.readDouble();
        elevation = in.readDouble();
        polarization = in.readDouble();
    }

    public static final Creator<LookAngle> CREATOR = new Creator<LookAngle>() {
        @Override
        public LookAngle createFromParcel(Parcel in) {
            return new LookAngle(in);
        }

        @Override
        public LookAngle[] newArray(int size) {
            return new LookAngle[size];
        }
    };

    public double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public double getPolarization() {
        return polarization;
    }

    public void setPolarization(double polarization) {
        this.polarization = polarization;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(azimuth);
        dest.writeDouble(elevation);
        dest.writeDouble(polarization);
    }
}
