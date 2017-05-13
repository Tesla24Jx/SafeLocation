package com.safelocation.FindPassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.safelocation.R;


public class FindPwdActivity extends AppCompatActivity {

    private Verification_Fragment verificationFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout);

        verificationFragment = new Verification_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, verificationFragment).commit();

    }
}
