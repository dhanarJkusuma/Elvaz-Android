package id.telkom.elvaz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;

import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.util.JsonParser;
import id.telkom.elvaz.util.SettingUpManager;
import id.telkom.elvaz.util.Validation;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String TAG = DatabaseHelper.class.getClass().getSimpleName();
    public static final String DB_NAME = "elvaz_db.db";
    public static final String DB_TABLE = "elvaz";
    public static final String COLUMN_ID = "town_id";
    public static final String COLUMN_TOWN = "town";
    public static final String COLUMN_LAT = "town_lat";
    public static final String COLUMN_LNG = "town_lng";
    public static final String COLUMN_UPDATE_AT = "updated_at";
    public static final int DB_VERSION = 1;
    private SettingUpManager manager;
    private Context context;

    public static final String QUERY_CREATE = "CREATE TABLE " + DB_TABLE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_TOWN + " TEXT NOT NULL, " +
            COLUMN_LAT + " DOUBLE NOT NULL, " +
            COLUMN_UPDATE_AT + " TEXT NOT NULL, " +
            COLUMN_LNG + " DOUBLE NOT NULL );";

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        manager = new SettingUpManager(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        manager.setUsername("Admin");
        manager.setPassword("Admin");
        db.execSQL(QUERY_CREATE);
        initData(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    private void initData(SQLiteDatabase db)
    {
        ArrayList<EarthStationInformation> town = new ArrayList<>();
        try
        {
            town = JsonParser.JSONParsing(JsonParser.getJson(context));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.d(TAG,e.getMessage());
        }

        if (town!=null)
        {
            for (int i=0;i<town.size();i++)
            {
                db.execSQL(addData(town.get(i).getSiteName(), String.valueOf(town.get(i).getLatitude()), String.valueOf(town.get(i).getLongitude())));

            }
        }
    }

    private String addData(String town,String lat,String lng)
    {
        return "INSERT INTO " + DB_TABLE + " (" + COLUMN_TOWN + "," + COLUMN_LAT + "," + COLUMN_LNG + "," + COLUMN_UPDATE_AT +  " ) VALUES ('" + town + "',"+ lat + ","+ lng  + ",'" + Validation.generateDateTime() + "')";
    }


}
