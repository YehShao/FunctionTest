package com.function.bruce.functiontest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.function.bruce.functiontest.database.UserAccountTable;
import com.function.bruce.functiontest.utils.FunctionTestUtils;
import com.function.bruce.functiontest.utils.Log;

public class Login extends AppCompatActivity {
    private static final String TAG = Login.class.getSimpleName();

    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;
    private Button mRegister;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private UserAccountTable userAccountTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        setContentView(R.layout.login);
        mContext = getApplication().getBaseContext();
        findViews();
        userAccountTable = new UserAccountTable(this);
        userAccountTable = userAccountTable.open();
    }

    private void findViews() {
        Log.i(TAG, "findViews");
        mAccount = (EditText) findViewById(R.id.edit_text_account);
        mPassword = (EditText) findViewById(R.id.edit_text_password);
        mLogin = (Button) findViewById(R.id.button_login);
        mRegister = (Button) findViewById(R.id.button_register);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get The User name and Password
                String account = mAccount.getText().toString();
                String password = mPassword.getText().toString();

                // fetch the Password form database for respective user name
                String storedAccount = userAccountTable.getSingleEntry(account, UserAccountTable.ACCOUNT_COLUMNS);
                String storedPassword = userAccountTable.getSingleEntry(password, UserAccountTable.PASSWORD_COLUMNS);

                // check if the Stored password matches with  Password entered by user
                if(account.equals(storedAccount) && password.equals(storedPassword)) {
                    Toast.makeText(Login.this, "Congrats: Login Successful", Toast.LENGTH_LONG).show();
                    mSharedPreferences = getSharedPreferences(FunctionTestUtils.CHECK_IS_LOGIN_SP_KEY,
                            Context.MODE_PRIVATE);
                    mSharedPreferences.edit().putBoolean(FunctionTestUtils.IS_LOGIN, true)
                            .apply();

                    Intent intent = new Intent(Login.this, Main.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        userAccountTable.close();
    }
}
