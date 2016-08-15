package id.telkom.elvaz.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import id.telkom.elvaz.model.EarthStationInformation;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class JsonParser
{
    public static String getJson(Context context)
    {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
        br = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(
                "town.json")));
        String temp;
        while ((temp = br.readLine()) != null)
            sb.append(temp);

        } catch (IOException e) {
        Log.d("json", e.getMessage());
        e.printStackTrace();
        }
        return sb.toString();

    }



    public static ArrayList<EarthStationInformation> JSONParsing(String json) throws JSONException {
        ArrayList<EarthStationInformation> towns = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject town = jsonArray.getJSONObject(i);
            EarthStationInformation newTown = new EarthStationInformation();
            newTown.setSiteName(town.getString("Town"));
            newTown.setLatitude(Double.parseDouble(Validation.parseComa(town.getString("latitude"))));
            newTown.setLongitude(Double.parseDouble(Validation.parseComa(town.getString("longitude"))));
            towns.add(newTown);
        }
        return towns;
    }
}
