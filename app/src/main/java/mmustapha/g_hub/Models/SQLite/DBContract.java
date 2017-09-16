package mmustapha.g_hub.Models.SQLite;

/**
 *  Created by mmustapha on 9/14/17.
 */

import android.provider.BaseColumns;

/**
 * Contract Class for creating all SQLite Tables
 */
public final class DBContract {

    //Constructor
    public DBContract(){}

    /**
     * Creating a table for storing all Developers' Details
     * Columns are id, username, image_url, favourite
     */
    public static class GHub implements BaseColumns{
        public static final String TABLE_NAME = "ghub";
        public static final String USERNAME = "username";
        public static final String IMAGE_URL = "image_url";
        public static final String IS_FAV = "favourite";
    }
}
