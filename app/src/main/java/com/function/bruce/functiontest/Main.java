package com.function.bruce.functiontest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.function.bruce.functiontest.utils.FunctionTestUtils;
import com.function.bruce.functiontest.utils.Log;

public class Main extends AppCompatActivity{
    private static final String TAG = Main.class.getSimpleName();

    private Button mLogout;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        findView();
    }

    private void findView() {
        Log.i(TAG, "findViews");
        mLogout = (Button) findViewById(R.id.button_logout);
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedPreferences = getSharedPreferences(FunctionTestUtils.CHECK_IS_LOGIN_SP_KEY,
                        Context.MODE_PRIVATE);
                mSharedPreferences.edit().putBoolean(FunctionTestUtils.IS_LOGIN, false)
                        .apply();

                Intent intent = new Intent(Main.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
