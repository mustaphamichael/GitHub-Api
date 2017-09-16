package mmustapha.g_hub.Models.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mmustapha.g_hub.Index.Adapters.Developer;

/**
 *  Created by mmustapha on 9/14/17.
 */

public class DBController {
    private DBHelper mDBHelper;
    private ContentValues values;

    private static String USER_NAME_KEY = DBContract.GHub.USERNAME;
    private static String IMAGE_URL_KEY = DBContract.GHub.IMAGE_URL;

    public DBController(Context context){
        mDBHelper = new DBHelper(context); // Create an Object of DBHelper
    }

    public void insertPosts(HashMap<String, String> postValues){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        values = new ContentValues();
        values.put(USER_NAME_KEY, postValues.get(USER_NAME_KEY));
        values.put(IMAGE_URL_KEY, postValues.get(IMAGE_URL_KEY));
        values.put(DBContract.GHub.IS_FAV, postValues.get("FAVOURITE"));

        db.insert(DBContract.GHub.TABLE_NAME, null, values); // Insert the values into the Table
        Log.e("TAG", "Row successfully inserted ...");
    }

    /**
     * Fetch Developer's List from Local SQLite DB
     * and add to an ArrayList
     * @return
     */
    public ArrayList<Developer> fetchList() {
        String selectQuery = "SELECT  * FROM "+ DBContract.GHub.TABLE_NAME;
        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<Developer> developers = new ArrayList<Developer>();
        while (cursor.moveToNext()){
            Developer dev = new Developer();
            String userName = cursor.getString(cursor.getColumnIndex(DBContract.GHub.USERNAME));
            String imageURL = cursor.getString(cursor.getColumnIndex(DBContract.GHub.IMAGE_URL));
            String isFav = cursor.getString(cursor.getColumnIndex(DBContract.GHub.IS_FAV));
            dev.setUserName(userName);
            dev.setImageURL(imageURL);
            dev.setIsFavourite(Boolean.valueOf(isFav));
            developers.add(dev);
        }
        cursor.close();
        return developers;
    }
}
