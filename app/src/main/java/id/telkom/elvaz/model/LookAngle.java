package id.telkom.elvaz.model;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class LookAngle
{
    private double azimuth;
    private double elevation;
    private double polarization;
    public LookAngle()
    {

    }

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
}
