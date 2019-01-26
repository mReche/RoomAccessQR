package com.example.manoloreche.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class HistoricalActivity extends AppCompatActivity {

    private String mailFromLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        Get login mail from other activity
         */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mailFromLogin = null;
            } else {
                mailFromLogin = extras.getString("neededMail");
            }
        } else {
            mailFromLogin = (String) savedInstanceState.getSerializable("neededMail");
        }

        HistoricalFragment fragment = (HistoricalFragment) getSupportFragmentManager().findFragmentById(R.id.content_historical);
        if(fragment == null){
            fragment = HistoricalFragment.newInstance(mailFromLogin);
            getSupportFragmentManager().beginTransaction().add(R.id.content_historical,fragment).commit();
        }
    }

}
