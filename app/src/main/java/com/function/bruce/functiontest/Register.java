package com.function.bruce.functiontest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.function.bruce.functiontest.database.UserAccountTable;
import com.function.bruce.functiontest.utils.FunctionTestUtils;
import com.function.bruce.functiontest.utils.Log;

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();

    private EditText mUserName;
    private EditText mAccount;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mSignUp;
    private Button mBack;
    private SharedPreferences mSharedPreferences;
    private Context mContext;

    private UserAccountTable userAccountTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        mContext = this;
        setContentView(R.layout.register);
        findViews();
        userAccountTable = new UserAccountTable(this);
        userAccountTable = userAccountTable.open();
    }

    private void findViews() {
        Log.i(TAG, "findViews");

        mUserName = (EditText) findViewById(R.id.edit_text_user_name);
        mAccount = (EditText) findViewById(R.id.edit_text_account);
        mPassword = (EditText) findViewById(R.id.edit_text_password);
        mConfirmPassword = (EditText) findViewById(R.id.edit_text_confirm_password);
        mSignUp = (Button) findViewById(R.id.button_enter);
        mBack = (Button) findViewById(R.id.button_back);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mUserName.getText().toString();
                String account = mAccount.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();

                // Check if any of the fields are vacant
                if (userName.equals("") || account.equals("") ||
                        password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(mContext, getString(R.string.toast_msg_register_field_empty), Toast.LENGTH_LONG).show();
                    if (confirmPassword.equals("")) {
                        mConfirmPassword.requestFocus();
                        editTextSetBackgroundMod1Null(mConfirmPassword);
                    }
                    if (password.equals("")) {
                        mPassword.requestFocus();
                        editTextSetBackgroundMod1Null(mPassword);
                    }
                    if (account.equals("")) {
                        mAccount.requestFocus();
                        editTextSetBackgroundMod1Null(mAccount);
                    }
                    if (userName.equals("")) {
                        mUserName.requestFocus();
                        editTextSetBackgroundMod1Null(mUserName);
                    }

                    return;
                }

                // Check Account and Password at least need 5 character
                if (mAccount.getText().length() < FunctionTestUtils.EDIT_TEXT_MIN_LENGTH ||
                        mPassword.getText().length() < FunctionTestUtils.EDIT_TEXT_MIN_LENGTH) {
                    Toast.makeText(mContext, getString(R.string.toast_msg_field_min_limit ,
                            FunctionTestUtils.EDIT_TEXT_MIN_LENGTH), Toast.LENGTH_LONG).show();

                    return;
                }

                // Check Account is exist or not
                if (userAccountTable.checkAccountIsExist(account)) {
                    Toast.makeText(mContext, getString(R.string.toast_msg_field_account_exist ,
                            account), Toast.LENGTH_LONG).show();

                    return;
                }

                // Check both password is same
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(mContext, getString(R.string.toast_msg_password_not_match), Toast.LENGTH_LONG).show();

                    return;
                }

                // Save the Data in Database
                userAccountTable.insertEntry(userName, account, password);
                Toast.makeText(mContext, getString(R.string.toast_msg_account_created), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(mContext, Login.class);
                intent.putExtra(FunctionTestUtils.SIGN_UP_SUCCESS, true);
                intent.putExtra(FunctionTestUtils.ACCOUNT, account);
                intent.putExtra(FunctionTestUtils.PASSWORD, password);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addTextChangedListener(mUserName);
        addTextChangedListener(mAccount);
        addTextChangedListener(mPassword);
        addTextChangedListener(mConfirmPassword);
    }

    private void addTextChangedListener(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > FunctionTestUtils.EDIT_TEXT_EMPTY_LENGTH) {
                    editTextSetBackgroundMod1(editText);
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
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");

        userAccountTable.close();
    }
}
