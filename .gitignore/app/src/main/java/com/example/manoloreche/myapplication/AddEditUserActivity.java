package com.example.manoloreche.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class AddEditUserActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_USER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String userID = getIntent().getStringExtra(AdminView.EXTRA_USERS_ID);
        setTitle(userID == null ? R.string.text_add_user : R.string.text_modify_user);
        AddEditUserFragment addEditUserFragment = (AddEditUserFragment)getSupportFragmentManager().findFragmentById(R.id.content_add_edit_user);
        if (addEditUserFragment == null){
            addEditUserFragment = AddEditUserFragment.newInstance(userID);
            getSupportFragmentManager().beginTransaction().add(R.id.content_add_edit_user,addEditUserFragment).commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
