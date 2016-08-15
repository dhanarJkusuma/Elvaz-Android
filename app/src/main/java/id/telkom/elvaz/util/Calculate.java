package id.telkom.elvaz.util;

import android.util.Log;

import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.model.LookAngle;
import id.telkom.elvaz.model.SateliteInformation;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class Calculate
{
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;

    public static final int DIRECTION_NOT_FOUND = 404;
    public static final int NORTH_WEST_COND = 10;
    public static final int NORTH_EAST_COND = 11;
    public static final int SOUTH_WEST_COND = 12;
    public static final int SOUTH_EAST_COND = 13;

    //Class untuk melihat arah
    public static class Direction
    {
       public static int getDirection(double latitude, double longitude, double limit)
       {
           int direction = DIRECTION_NOT_FOUND;
           int dirLat = getDirectionNorthSouth(latitude);
           int dirLong = getDirectionWestEast(longitude,limit);
           if(dirLat==NORTH)
           {
               if(dirLong == WEST)
               {
                   direction = NORTH_WEST_COND;
               }
               else if(dirLong == EAST)
               {
                   direction = NORTH_EAST_COND;
               }
           }
           else if(dirLat == SOUTH)
           {
               if(dirLong == WEST)
               {
                   direction = SOUTH_WEST_COND;
               }
               else if(dirLong == EAST)
               {
                   direction = SOUTH_EAST_COND;
               }
           }
           return direction;
       }

       public static int getDirectionNorthSouth(double latitude)
       {
           return (latitude>0) ? NORTH : SOUTH;
       }

       public static int getDirectionWestEast(double longitude, double limitSatelite)
       {
           return (longitude<limitSatelite) ? WEST : EAST;
       }

       public static String getDirectionString(int condition)
       {
           String log = "Direction Not Found";
           switch (condition)
           {
               case NORTH_WEST_COND :
                   log = "NORTH WEST CONDITION";
                   break;
               case NORTH_EAST_COND:
                   log = "NORTH EAST CONDITION";
                   break;
               case SOUTH_WEST_COND:
                   log = "SOUTH WEST CONDITION";
                   break;
               case SOUTH_EAST_COND:
                   log = "SOUTH EAST CONDITION";
                   break;
           }
           return log;
       }
    }


    public static double getCorrection(int condition, double azimuth)
    {
        Log.d("ELVAZ(LOG): Direction",String.valueOf(Calculate.Direction.getDirectionString(condition)));
        double result = 0;
        switch (condition)
        {
            case NORTH_EAST_COND:
                result =  180 + azimuth;
                break;
            case NORTH_WEST_COND:
                result = 180 - azimuth;
                break;
            case SOUTH_EAST_COND:
                result = 360 - azimuth;
                break;
            case SOUTH_WEST_COND:
                result = azimuth;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

    public static LookAngle getLookAngle(EarthStationInformation earth,SateliteInformation satelite)
    {
        // 1/tan(tan(|longitudeB-longitudeS|)/sin(latitudeBumi)) = Azimuth
        // 1/tan(cos(latitudeB)*cos(longitudeB-longtudeS)-0.151/sqrt(1-(cos(latitudeB)*cos(longitudeB-longitudeS))2)

        double longitudeBS = earth.getLongitude()-satelite.getLongitude();

        LookAngle lookAngle = new LookAngle();
        double azimuth = Math.toDegrees(Math.atan(Math.tan(Math.abs(Math.toRadians(longitudeBS)))/Math.sin(Math.toRadians(earth.getLatitude()))));
        double elevation = Math.toDegrees(Math.atan((Math.cos(Math.toRadians(earth.getLatitude()))*Math.cos(Math.toRadians(longitudeBS))-0.151)/Math.sqrt(1 - (Math.pow(Math.cos(Math.toRadians(earth.getLatitude())) * Math.cos(Math.toRadians(longitudeBS)), 2)))));
        Log.d("ELVAZ(LOG): Cal(L)",String.valueOf(longitudeBS));
        Log.d("ELVAZ(LOG): Cal(SinL)", String.valueOf(Math.sin(Math.toRadians(longitudeBS))));
        Log.d("ELVAZ(LOG): Cal(TanLat)", String.valueOf(Math.tan(Math.toRadians(earth.getLatitude()))));
        double polarization = Math.toDegrees(Math.atan(Math.sin(Math.toRadians(longitudeBS))/Math.tan(Math.toRadians(earth.getLatitude()))));

        int direction = Calculate.Direction.getDirection(earth.getLatitude(), earth.getLongitude(), satelite.getLongitude());

        double finalAzimuth = getCorrection(direction, Math.abs(azimuth));

        lookAngle.setAzimuth(finalAzimuth);
        lookAngle.setElevation(elevation);
        lookAngle.setPolarization(polarization);

        return lookAngle;
    }
}
