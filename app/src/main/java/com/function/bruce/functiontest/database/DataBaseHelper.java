package com.function.bruce.functiontest.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.function.bruce.functiontest.utils.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DataBaseHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "function_test.db";
    public static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Called when no database exists in disk and the helper class needs to create a new one.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");

        db.execSQL(UserAccountTable.DATABASE_CREATE);
    }

    // Called when there is a database version mismatch meaning that the version of the database on disk
    // needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log the version upgrade.
        Log.w(TAG, "Upgrading from version " + oldVersion + " to " + newVersion +
                ", which will destroy all old data");

        // Upgrade the existing database to conform to the new version. Multiple previous versions can be
        // handled by comparing _oldVersion and _newVersion values.
        // The simplest case is to drop the old table and create a new one.
        db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        // Create a new one.
        onCreate(db);
    }
}
