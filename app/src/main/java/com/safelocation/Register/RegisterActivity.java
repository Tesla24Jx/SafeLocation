package com.safelocation.Register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.safelocation.R;


public class RegisterActivity extends AppCompatActivity implements Verification_Fragment.GotoNewxtPage{
    private RegisterFragment registerFragment;
    private Verification_Fragment verificationFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout);

        verificationFragment = new Verification_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, verificationFragment).commit();

    }

    @Override
    public void gotoCompleteInfo(String str) {
        Log.d("###phone=",str);
        Bundle bundle = new Bundle();
        bundle.putString("phone", str);
        registerFragment = new RegisterFragment();
        registerFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, registerFragment).commit();
        new RegisterPresenter(new RegisterModel(), registerFragment);
    }

}
