package id.telkom.elvaz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.util.Validation;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class TownsDatabase
{
    private static final String TAG = TownsDatabase.class.getSimpleName();
    private DatabaseHelper helper;
    public TownsDatabase(Context context)
    {
        helper = new DatabaseHelper(context);
    }

    public SQLiteDatabase getDB(){
        SQLiteDatabase db = helper.getWritableDatabase();
        return db;
    }

    public ArrayList<EarthStationInformation> getTowns()
    {
        ArrayList<EarthStationInformation> towns = new ArrayList<>();
        SQLiteDatabase db = getDB();
        String columns[] = {helper.COLUMN_ID,helper.COLUMN_TOWN,helper.COLUMN_LAT,helper.COLUMN_LNG,helper.COLUMN_UPDATE_AT};
        Cursor cursor = db.query(helper.DB_TABLE, columns, null, null, null, null, null);
        while (cursor.moveToNext())
        {
            EarthStationInformation es = new EarthStationInformation();
            es.setId(cursor.getInt(cursor.getColumnIndex(helper.COLUMN_ID)));
            es.setSiteName(cursor.getString(cursor.getColumnIndex(helper.COLUMN_TOWN)));
            es.setLatitude(cursor.getDouble(cursor.getColumnIndex(helper.COLUMN_LAT)));
            es.setLongitude(cursor.getDouble(cursor.getColumnIndex(helper.COLUMN_LNG)));
            es.setUpdatedAt(cursor.getString(cursor.getColumnIndex(helper.COLUMN_UPDATE_AT)));
            towns.add(es);
        }
        return towns;
    }

    public long addTown(EarthStationInformation town)
    {
        SQLiteDatabase db = getDB();
        ContentValues iTown = new ContentValues();
        iTown.put(helper.COLUMN_TOWN,town.getSiteName());
        iTown.put(helper.COLUMN_LAT,town.getLatitude());
        iTown.put(helper.COLUMN_LNG,town.getLongitude());
        iTown.put(helper.COLUMN_UPDATE_AT, Validation.generateDateTime());
        long result = db.insert(helper.DB_TABLE, null, iTown);
        return result;
    }

    public EarthStationInformation getSelectedTown(int id)
    {
        SQLiteDatabase db= getDB();
        EarthStationInformation earthStationInformation = new EarthStationInformation();
        String columns[] = {helper.COLUMN_ID,helper.COLUMN_TOWN,helper.COLUMN_LAT,helper.COLUMN_LNG,helper.COLUMN_UPDATE_AT};
        String args[] = { String.valueOf(id) };
        Cursor cursor = db.query(helper.DB_TABLE, columns, helper.COLUMN_ID + " =? ", args, null, null, null);
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            earthStationInformation.setId(cursor.getInt(cursor.getColumnIndex(helper.COLUMN_ID)));
            earthStationInformation.setSiteName(cursor.getString(cursor.getColumnIndex(helper.COLUMN_TOWN)));
            earthStationInformation.setLatitude(cursor.getDouble(cursor.getColumnIndex(helper.COLUMN_LAT)));
            earthStationInformation.setLongitude(cursor.getDouble(cursor.getColumnIndex(helper.COLUMN_LNG)));
            earthStationInformation.setUpdatedAt(cursor.getString(cursor.getColumnIndex(helper.COLUMN_UPDATE_AT)));
        }
        return earthStationInformation;

    }

    public long updateTown(int oldId,EarthStationInformation town)
    {
        SQLiteDatabase db = getDB();
        ContentValues iTown = new ContentValues();
        iTown.put(helper.COLUMN_TOWN,town.getSiteName());
        iTown.put(helper.COLUMN_LAT,town.getLatitude());
        iTown.put(helper.COLUMN_LNG,town.getLongitude());
        iTown.put(helper.COLUMN_UPDATE_AT,Validation.generateDateTime());
        String[] whereArgs = {String.valueOf(oldId)};
        long result = db.update(helper.DB_TABLE, iTown, helper.COLUMN_ID + " =? ", whereArgs);
        return result;
    }

    public long deleteTown(int id)
    {
        SQLiteDatabase db = getDB();
        String[] whereArgs = {String.valueOf(id)};
        long result = db.delete(helper.DB_TABLE, helper.COLUMN_ID + " =?", whereArgs);
        return result;
    }

    Callable<ArrayList<EarthStationInformation>> getCallableTown() {
        return new Callable<ArrayList<EarthStationInformation>>() {
            @Override
            public ArrayList<EarthStationInformation> call() throws Exception {
                return getTowns();
            }
        };
    }

    public Observable<ArrayList<EarthStationInformation>> getRxTown() {
        return makeObservable(getCallableTown())
                .subscribeOn(Schedulers.computation());// note: do not use Schedulers.io()
    }


    Callable<Long> saveCallableTown(final EarthStationInformation data)
    {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return addTown(data);
            }
        };
    }

    public Observable<Long> saveRxTown(EarthStationInformation data)
    {
        return makeObservable(saveCallableTown(data))
                .subscribeOn(Schedulers.computation());
    }

    Callable<Long> updateCallableTown(final int old, final EarthStationInformation data)
    {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return updateTown(old,data);
            }
        };
    }

    public Observable<Long> updateRxTown(int old_id,final EarthStationInformation data)
    {
        return makeObservable(updateCallableTown(old_id,data))
                .subscribeOn(Schedulers.computation());
    }

    Callable<Long> deleteCallableTown(final int id)
    {
        return new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return deleteTown(id);
            }
        };
    }

    public Observable<Long> deleteRxTown(int id)
    {
        return makeObservable(deleteCallableTown(id))
                .subscribeOn(Schedulers.computation());
    }

    Callable<EarthStationInformation> getSingleDataCallableTown(final int id)
    {
        return new Callable<EarthStationInformation>() {
            @Override
            public EarthStationInformation call() throws Exception {
                return getSelectedTown(id);
            }
        };
    }

    public Observable<EarthStationInformation> getSingleRxTown(int id)
    {
        return makeObservable(getSingleDataCallableTown(id))
                .subscribeOn(Schedulers.computation());
    }

    private static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch (Exception ex) {
                            Log.e(TAG, "Error reading from the database", ex);
                        }
                    }
                });
    }
}

