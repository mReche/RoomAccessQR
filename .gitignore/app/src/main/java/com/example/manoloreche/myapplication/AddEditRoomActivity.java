package com.example.manoloreche.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class AddEditRoomActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_ROOM = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String roomID = getIntent().getStringExtra(RoomsActivity.EXTRA_ROOMS_ID);
        setTitle(roomID == null ? R.string.text_add_room : R.string.text_edit_room);

        AddEditRoomFragment addEditRoomFragment = (AddEditRoomFragment)getSupportFragmentManager().findFragmentById(R.id.content_add_edit_room);
        if(addEditRoomFragment == null){
            addEditRoomFragment = AddEditRoomFragment.newInstance(roomID);
            getSupportFragmentManager().beginTransaction().add(R.id.content_add_edit_room,addEditRoomFragment).commit();
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
