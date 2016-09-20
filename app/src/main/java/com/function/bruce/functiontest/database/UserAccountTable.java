package com.function.bruce.functiontest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.function.bruce.functiontest.utils.Log;

public class UserAccountTable {
    private static final String TAG = UserAccountTable.class.getSimpleName();

    public static final int USERNAME_COLUMN_INDEX = 1;
    public static final int ACCOUNT_COLUMN_INDEX = 2;
    public static final int PASSWORD_COLUMN_INDEX = 3;
    public static final String TABLE_NAME = "USER_ACCOUNT";
    public static final String USERNAME_COLUMN = "USERNAME";
    public static final String ACCOUNT_COLUMN = "ACCOUNT";
    public static final String PASSWORD_COLUMN = "PASSWORD";

    // SQL Statement to create a new database.
    public static final String DATABASE_CREATE = "create table " + TABLE_NAME +
            " (ID integer primary key autoincrement, " +
            USERNAME_COLUMN + " text, " +
            ACCOUNT_COLUMN + " text, " +
            PASSWORD_COLUMN + " text);";

    // Variable to hold the database instance
    public SQLiteDatabase db;

    // Context of the application using the database.
    private final Context mContext;

    // Database open/upgrade helper
    private DataBaseHelper dataBaseHelper;

    public UserAccountTable(Context context) {
        mContext = context;
        dataBaseHelper = new DataBaseHelper(mContext, DataBaseHelper.DATABASE_NAME, null,
                DataBaseHelper.DATABASE_VERSION);
    }

    public UserAccountTable open() throws SQLException {
        Log.i(TAG, "open");

        db = dataBaseHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        Log.i(TAG, "close");

        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String userName, String account, String password) {
        Log.i(TAG, "insertEntry");

        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(USERNAME_COLUMN, userName);
        newValues.put(ACCOUNT_COLUMN, account);
        newValues.put(PASSWORD_COLUMN,password);

        // Insert the row into your table
        db.insert(TABLE_NAME, null, newValues);
        // Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public int deleteEntry(String userName) {
        Log.i(TAG, "deleteEntry");

        String where = USERNAME_COLUMN + " = ?";
        int numberOfEntriesDeleted = db.delete(TABLE_NAME, where, new String[]{userName}) ;
        Log.i(TAG, "Number fo Entry Deleted Successfully : " + numberOfEntriesDeleted);

        return numberOfEntriesDeleted;
    }

    public String getSingleEntry(String account, String columnName) {
        Log.i(TAG, "getSingleEntry");

        Cursor cursor = db.query(TABLE_NAME, null, ACCOUNT_COLUMN + " = ?", new String[]{account}, null, null, null);
        // ACCOUNT Not Exist
        if(cursor.getCount() < 1) {
            cursor.close();

            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String columns = cursor.getString(cursor.getColumnIndex(columnName));
        cursor.close();

        return columns;
    }

    public void updateEntry(String userName, String account, String password) {
        Log.i(TAG, "updateEntry");

        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put(USERNAME_COLUMN, userName);
        updatedValues.put(ACCOUNT_COLUMN, account);
        updatedValues.put(PASSWORD_COLUMN, password);

        String where = USERNAME_COLUMN + " = ?";
        db.update(TABLE_NAME, updatedValues, where, new String[]{userName});
    }

    public boolean checkAccountIsExist(String account) {
        Log.i(TAG, "checkAccountIsExist");

        Cursor cursor = db.query(TABLE_NAME, null, ACCOUNT_COLUMN + " = ?", new String[]{account}, null, null, null);
        // ACCOUNT Not Exist
        if(cursor.getCount() < 1) {
            cursor.close();

            return false;
        } else {
            return true;
        }
    }
}
