package com.safelocation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.safelocation.Register.RegisterFragment;
import com.safelocation.Register.RegisterModel;
import com.safelocation.Register.RegisterPresenter;


public class RegisterActivity extends AppCompatActivity {

    RegisterFragment registerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout);

        registerFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, registerFragment).commit();
        new RegisterPresenter(new RegisterModel(), registerFragment);
    }


}
