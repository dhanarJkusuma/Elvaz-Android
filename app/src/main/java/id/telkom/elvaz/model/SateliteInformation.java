package id.telkom.elvaz.model;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class SateliteInformation
{
    private String sateliteName;
    private double longitude;
    public SateliteInformation()
    {

    }

    public SateliteInformation(String sateliteName, double longitude) {
        this.sateliteName = sateliteName;
        this.longitude = longitude;
    }

    public String getSateliteName() {
        return sateliteName;
    }

    public void setSateliteName(String sateliteName) {
        this.sateliteName = sateliteName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
