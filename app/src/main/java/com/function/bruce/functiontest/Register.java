package com.function.bruce.functiontest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.function.bruce.functiontest.database.UserAccountTable;
import com.function.bruce.functiontest.utils.Log;

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();

    private EditText mName;
    private EditText mAccount;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mEnter;
    private Button mBack;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private UserAccountTable userAccountTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.register);
        mContext = getApplication().getBaseContext();
        findViews();
        userAccountTable = new UserAccountTable(this);
        userAccountTable = userAccountTable.open();
    }

    private void findViews() {
        Log.i(TAG, "findViews");
        mName = (EditText) findViewById(R.id.edit_text_name);
        mAccount = (EditText) findViewById(R.id.edit_text_account);
        mPassword = (EditText) findViewById(R.id.edit_text_password);
        mConfirmPassword = (EditText) findViewById(R.id.edit_text_confirm_password);
        mEnter = (Button) findViewById(R.id.button_enter);
        mBack = (Button) findViewById(R.id.button_back);

        mEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mName.getText().toString();
                String account = mAccount.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                // check if any of the fields are vacant
                if(userName.equals("") || account.equals("") ||
                        password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(mContext, "Field Vacant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword)) {
                    Toast.makeText(mContext, "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    // Save the Data in Database
                    userAccountTable.insertEntry(userName, account, password);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, Main.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
