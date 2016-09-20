package com.function.bruce.functiontest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.function.bruce.functiontest.utils.FunctionTestUtils;
import com.function.bruce.functiontest.utils.Log;

public class Main extends AppCompatActivity{
    private static final String TAG = Main.class.getSimpleName();

    private Button mLogout;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        mContext = this;
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
                mSharedPreferences.edit()
                        .putBoolean(FunctionTestUtils.IS_LOGIN, false)
                        .apply();

                Intent intent = new Intent(mContext, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

        Intent intent = getIntent();
        boolean loginSuccess = intent.getBooleanExtra(FunctionTestUtils.LOGIN_SUCCESS, false);
        if (loginSuccess) {
            String userName = intent.getStringExtra(FunctionTestUtils.USER_NAME);
            Toast.makeText(mContext, getString(R.string.toast_msg_login_success, userName),
                    Toast.LENGTH_LONG).show();
        }
    }
}
