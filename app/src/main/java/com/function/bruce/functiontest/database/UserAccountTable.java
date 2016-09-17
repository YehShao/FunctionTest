package com.function.bruce.functiontest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class UserAccountTable {
    public static final String DATABASE_NAME = "function_test.db";
    public static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    public static final int ACCOUNT_COLUMN = 2;
    public static final int PASSWORD_COLUMN = 3;
    public static final String TABLE_NAME = "USER_ACCOUNT";
    public static final String NAME_COLUMNS = "USERNAME";
    public static final String ACCOUNT_COLUMNS = "ACCOUNT";
    public static final String PASSWORD_COLUMNS = "PASSWORD";

    // SQL Statement to create a new database.
    public static final String DATABASE_CREATE = "create table " + TABLE_NAME +
            " (ID integer primary key autoincrement, " +
            NAME_COLUMNS + " text, " +
            ACCOUNT_COLUMNS + " text, " +
            PASSWORD_COLUMNS + " text);";

    // Variable to hold the database instance
    public SQLiteDatabase db;

    // Context of the application using the database.
    private final Context mContext;

    // Database open/upgrade helper
    private DataBaseHelper dataBaseHelper;

    public UserAccountTable(Context context) {
        mContext = context;
        dataBaseHelper = new DataBaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public UserAccountTable open() throws SQLException {
        db = dataBaseHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void insertEntry(String userName, String account, String password) {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("ACCOUNT", account);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("USER_ACCOUNT", null, newValues);
        // Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public int deleteEntry(String userName) {
        String where = "USERNAME = ?";
        int numberOfEntriesDeleted = db.delete("USER_ACCOUNT", where, new String[]{userName}) ;
        Toast.makeText(mContext, "Number fo Entry Deleted Successfully : " +
                numberOfEntriesDeleted, Toast.LENGTH_LONG).show();

        return numberOfEntriesDeleted;
    }

    public String getSingleEntry(String userName) {
        Cursor cursor = db.query("USER_ACCOUNT", null, " USERNAME = ?", new String[]{userName}, null, null, null);
        // UserName Not Exist
        if(cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();

        return password;
    }

    public String getSingleEntry(String text, String columnName) {
        Cursor cursor = db.query("USER_ACCOUNT", null, columnName + "= ?", new String[]{text}, null, null, null);
        // UserName Not Exist
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
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("ACCOUNT", account);
        updatedValues.put("PASSWORD", password);

        String where="USERNAME = ?";
        db.update("USER_ACCOUNT", updatedValues, where, new String[]{userName});
    }
}
