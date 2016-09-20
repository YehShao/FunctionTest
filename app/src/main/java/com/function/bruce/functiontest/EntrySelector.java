package com.function.bruce.functiontest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.function.bruce.functiontest.utils.FunctionTestUtils;
import com.function.bruce.functiontest.utils.Log;

public class EntrySelector extends Activity {
    private static final String TAG = EntrySelector.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

        if (checkIsLogin()) {
            goToActivity(Main.class);
        } else {
            goToActivity(Login.class);
        }
    }

    private boolean checkIsLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences(FunctionTestUtils.CHECK_IS_LOGIN_SP_KEY,
                Context.MODE_PRIVATE);
        Boolean isLogin = sharedPreferences.getBoolean(FunctionTestUtils.IS_LOGIN , false);
        Log.i(TAG, "isLogin = " + isLogin);

        return isLogin;
    }

    private void goToActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        finish();
    }
}
