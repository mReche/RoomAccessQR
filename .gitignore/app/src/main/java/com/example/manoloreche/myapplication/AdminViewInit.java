package com.example.manoloreche.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminViewInit extends AppCompatActivity {

    Button manageUsers, manageRooms, viewHistorical;
    private String mailFromLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_init);

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

        manageUsers = (Button) findViewById(R.id.manage_users);
        manageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminIntent = new Intent(getApplicationContext(),AdminView.class);
                //Toast.makeText(MainActivity.this,"funciono como admin",Toast.LENGTH_LONG).show();
                startActivity(adminIntent);
            }
        });
        manageRooms = (Button) findViewById(R.id.manage_rooms);
        manageRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminIntent = new Intent(getApplicationContext(),RoomsActivity.class);
                //Toast.makeText(AdminViewInit.this,"Manage Rooms",Toast.LENGTH_LONG).show();
                startActivity(adminIntent);
            }
        });

        viewHistorical = (Button) findViewById(R.id.button_historical);
        viewHistorical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historical = new Intent(getApplicationContext(),HistoricalActivity.class);
                historical.putExtra("neededMail",mailFromLogin);
                //Toast.makeText(AdminViewInit.this,"Manage Rooms",Toast.LENGTH_LONG).show();
                startActivity(historical);
            }
        });
    }
}
