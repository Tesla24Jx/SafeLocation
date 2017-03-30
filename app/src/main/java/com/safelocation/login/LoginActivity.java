package com.safelocation.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.safelocation.R;


public class LoginActivity extends AppCompatActivity {
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout);

        loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout,loginFragment).commit();

        new LoginPresenter(new LoginModel(),loginFragment);

    }
}
