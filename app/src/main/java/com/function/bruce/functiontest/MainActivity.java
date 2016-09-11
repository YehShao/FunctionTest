package com.function.bruce.functiontest;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText mName;
    private EditText mPhone;
    private EditText mSex;
    private Button mSave;
    private Button mRead;
    private Button mClear;

    private SharedPreferences mRecordData;
    private static final String SP_FILE_NAME = MainActivity.class.getName();
    private static final String SP_NAME = "NAME";
    private static final String SP_PHONE = "PHONE";
    private static final String SP_SEX = "SEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        initComponent();
        setEventListener();
    }

    public void initComponent() {
        mName = (EditText) findViewById(R.id.name);
        mPhone = (EditText) findViewById(R.id.phone);
        mSex = (EditText) findViewById(R.id.sex);
        mSave = (Button) findViewById(R.id.save);
        mRead = (Button) findViewById(R.id.read);
        mClear = (Button) findViewById(R.id.clear);
    }

    public void setEventListener(){
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                saveData();
            }
        });

        mRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                readData();
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mName.setText("");
                mPhone.setText("");
                mSex.setText("");
                Toast.makeText(getApplicationContext(), getString(R.string.clear_notify), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void readData() {
        mRecordData = getSharedPreferences(SP_FILE_NAME, 0);
        mName.setText(mRecordData.getString(SP_NAME, ""));
        mPhone.setText(mRecordData.getString(SP_PHONE, ""));
        mSex.setText(mRecordData.getString(SP_SEX, ""));
        if(mRecordData != null && getApplication() != null) {
            Toast.makeText(getApplicationContext(), getString(R.string.read_notify), Toast.LENGTH_LONG).show();
        }

        Log.i(TAG, "readAllData = " + mRecordData.getAll());
    }

    public void saveData() {
        mRecordData = getSharedPreferences(SP_FILE_NAME, 0);
        if(mRecordData != null && getApplication() != null) {
            mRecordData.edit()
                    .putString(SP_NAME, mName.getText().toString())
                    .putString(SP_PHONE, mPhone.getText().toString())
                    .putString(SP_SEX, mSex.getText().toString())
                    .apply();
            Toast.makeText(getApplicationContext(), getString(R.string.save_notify), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        saveData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
