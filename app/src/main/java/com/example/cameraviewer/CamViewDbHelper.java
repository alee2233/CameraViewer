package com.example.cameraviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CamViewDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CamView.db";
    private static final String CAMERA_TABLE_NAME = "camera";
    private static final String CAMERA_COLUMN_ID = "camera_id";
    private static final String CAMERA_COLUMN_NAME = "name";
    private static final String CAMERA_COLUMN_ADDR = "address";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CAMERA_TABLE_NAME + " (" +
                    CAMERA_COLUMN_ID + " INTEGER PRIMARY KEY," +
                    CAMERA_COLUMN_NAME + " TEXT," +
                    CAMERA_COLUMN_ADDR + " TEXT)";

    private static final String SQL_READ_TABLE =
            "SELECT * FROM " + CAMERA_TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CAMERA_TABLE_NAME;

    public CamViewDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertNewCamera (String name, String addr) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CAMERA_COLUMN_NAME,name);
        cv.put(CAMERA_COLUMN_ADDR,addr);
        db.insert(CAMERA_TABLE_NAME,null,cv);
    }

    public ArrayList<RemoteCamera> readAllCameras() {

        ArrayList<RemoteCamera> currCamList = new ArrayList<RemoteCamera>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(SQL_READ_TABLE,null);

        int curr_id;
        String curr_name;
        String curr_addr;

        while(c.moveToNext()) {
            curr_id = c.getInt(c.getColumnIndex(CAMERA_COLUMN_ID));
            curr_name = c.getString(c.getColumnIndex(CAMERA_COLUMN_NAME));
            curr_addr = c.getString(c.getColumnIndex(CAMERA_COLUMN_ADDR));

            RemoteCamera rc = new RemoteCamera(curr_id,curr_name,curr_addr);

            currCamList.add(rc);
        }

        c.close();

        return currCamList;
    }

    public void deleteCamera(RemoteCamera rc) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + CAMERA_TABLE_NAME + " WHERE " + CAMERA_COLUMN_ID + "= " + rc.getId());

        db.close();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
