package mmustapha.g_hub.Models.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *  Created by mmustapha on 9/14/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    //DB Version and Name
    //The DB Version is increased if the DB Schema is changed
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "GHub.db";

    //DB Query to create a Table
    private static final String SQL_CREATE_QUERY =
            "CREATE TABLE " + DBContract.GHub.TABLE_NAME + "(" +
                    DBContract.GHub._ID + " INTEGER PRIMARY KEY," +
                    DBContract.GHub.USERNAME + " TEXT UNIQUE," +
                    DBContract.GHub.IMAGE_URL + " TEXT,"+
                    DBContract.GHub.IS_FAV + " TEXT)";

    //DB Query to delete a Table
    private static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + DBContract.GHub.TABLE_NAME;


    //Constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QUERY);
        Log.e("TAG", "SQLiteDB created successfully...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //It simply discards the data and starts over
        db.execSQL(SQL_DELETE_QUERY);
        onCreate(db);
    }

    //Optional
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}