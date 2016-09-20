package com.function.bruce.functiontest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
    private UserAccountTable userAccountTable;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        mContext = this;
        setContentView(R.layout.login);
        userAccountTable = new UserAccountTable(this);
        userAccountTable = userAccountTable.open();
        findViews();
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
                // get the account and password
                String account = mAccount.getText().toString();
                String password = mPassword.getText().toString();
                String storedPassword = userAccountTable.getSingleEntry(account, UserAccountTable.PASSWORD_COLUMN);

                if (account.equals("") || password.equals("")) {
                    Toast.makeText(mContext, getString(R.string.toast_msg_login_field_empty), Toast.LENGTH_LONG).show();
                    if (password.equals("")) {
                        mPassword.requestFocus();
                        editTextSetBackgroundMod1Null(mPassword);
                    }
                    if (account.equals("")) {
                        mAccount.requestFocus();
                        editTextSetBackgroundMod1Null(mAccount);
                    }

                    return;
                }

                if (password.equals(storedPassword)) {
                    String userName = userAccountTable.getSingleEntry(account, UserAccountTable.USERNAME_COLUMN);

                    mSharedPreferences = getSharedPreferences(FunctionTestUtils.CHECK_IS_LOGIN_SP_KEY,
                            Context.MODE_PRIVATE);
                    mSharedPreferences.edit()
                            .putBoolean(FunctionTestUtils.IS_LOGIN, true)
                            .apply();

                    mSharedPreferences = getSharedPreferences(FunctionTestUtils.LOGIN_USER_INFO_SP_KEY,
                            Context.MODE_PRIVATE);
                    mSharedPreferences.edit()
                            .putString(FunctionTestUtils.LOGIN_USER_INFO_USER_NAME, userName)
                            .putString(FunctionTestUtils.LOGIN_USER_INFO_ACCOUNT, account)
                            .putString(FunctionTestUtils.LOGIN_USER_INFO_PASSWORD, password)
                            .apply();

                    Intent intent = new Intent(mContext, Main.class);
                    intent.putExtra(FunctionTestUtils.LOGIN_SUCCESS, true);
                    intent.putExtra(FunctionTestUtils.USER_NAME, userName);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(mContext, getString(R.string.toast_msg_login_fail), Toast.LENGTH_LONG).show();
                    editTextSetBackgroundMod1Null(mAccount);
                    editTextSetBackgroundMod1Null(mPassword);
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Register.class);
                startActivity(intent);
                editTextSetBackgroundMod1(mAccount);
                editTextSetBackgroundMod1(mPassword);
                mAccount.setText("");
                mPassword.setText("");
                mAccount.clearFocus();
                mPassword.clearFocus();
            }
        });

        addTextChangedListener(mAccount);
        addTextChangedListener(mPassword);
    }

    private void addTextChangedListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > FunctionTestUtils.EDIT_TEXT_EMPTY_LENGTH) {
                    editTextSetBackgroundMod1(mAccount);
                    editTextSetBackgroundMod1(mPassword);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void editTextSetBackgroundMod1(EditText editText) {
        editText.setBackground(getResources().getDrawable(R.drawable.edit_text_mod_1));
    }

    private void editTextSetBackgroundMod1Null(EditText editText) {
        editText.setBackground(getResources().getDrawable(R.drawable.edit_text_mod_1_null));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

        Intent intent = getIntent();
        boolean signUpSuccess = intent.getBooleanExtra(FunctionTestUtils.SIGN_UP_SUCCESS, false);

        if (signUpSuccess) {
            mAccount.setText(intent.getStringExtra(FunctionTestUtils.ACCOUNT));
            mPassword.setText(intent.getStringExtra(FunctionTestUtils.PASSWORD));
            mLogin.callOnClick();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");

        userAccountTable.close();
    }
}
