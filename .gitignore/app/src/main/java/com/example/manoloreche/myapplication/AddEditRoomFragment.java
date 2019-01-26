package com.example.manoloreche.myapplication;


import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.manoloreche.myapplication.models.Rooms;
import com.example.manoloreche.myapplication.sql.DBHelper;

import java.util.Random;


public class AddEditRoomFragment extends Fragment {

    private static final String ARG_ROOM_ID = "arg_room_id";
    private String mRoomID;
    private DBHelper database;
    private FloatingActionButton saveButton;
    private TextInputEditText nameField,rolField,capcityField, materialField, buildingField;

    public AddEditRoomFragment() {
        // Required empty public constructor
    }

    public static AddEditRoomFragment newInstance(String roomID) {
        AddEditRoomFragment fragment = new AddEditRoomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROOM_ID, roomID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRoomID = getArguments().getString(ARG_ROOM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_room, container, false);
        saveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        nameField = (TextInputEditText) root.findViewById(R.id.et_name);
        rolField = (TextInputEditText) root.findViewById(R.id.et_rol);
        capcityField =  (TextInputEditText) root.findViewById(R.id.et_capacity);
        materialField = (TextInputEditText) root.findViewById(R.id.et_material);
        buildingField = (TextInputEditText) root.findViewById(R.id.et_edifice);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditRoom();
            }
        });
        database = new DBHelper(getActivity());
        if(mRoomID != null){
            loadRoom();
        }
        return root;
    }

    public void loadRoom(){
        new GetRoomByIdTask().execute();
    }

    private void addEditRoom(){
        boolean error = false;
        String name = nameField.getText().toString();
        String rol = rolField.getText().toString();
        String capacity = capcityField.getText().toString();
        String material = materialField.getText().toString();
        String building = buildingField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            nameField.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(rol)) {
            rolField.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(capacity)) {
            capcityField.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(material)) {
            materialField.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(building)) {
            buildingField.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }
        Random rand = new Random(System.currentTimeMillis());
        rand.setSeed(System.currentTimeMillis());
        int intrand = rand.nextInt(100000000);

        String id = String.valueOf(intrand);
        Rooms rooms = new Rooms(id ,name,rol,capacity,"",material,"",building);
        new AddEditRoomsTask().execute(rooms);
    }

    private void showRoomsScreen(Boolean requery){
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }
    private void showAddEditError() {
        Toast.makeText(getActivity(),
                R.string.error_new_info, Toast.LENGTH_SHORT).show();
    }
    private void showLoadError() {
        Toast.makeText(getActivity(),
                R.string.err_editing, Toast.LENGTH_SHORT).show();
    }

    private void showRooms(Rooms rooms){
        nameField.setText(rooms.getNameRoom());
        rolField.setText(rooms.getIsAccessible());
        capcityField.setText(rooms.getCapacity());
        materialField.setText(rooms.getMaterialName());
        buildingField.setText(rooms.getEdifice());
    }

    private class GetRoomByIdTask extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... params) {
            return database.getRoomByID(mRoomID);
        }
        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showRooms(new Rooms(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }
    }

    private class AddEditRoomsTask extends AsyncTask<Rooms, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Rooms... params) {
            if (mRoomID != null) {
                return database.updateRooms(params[0], mRoomID) > 0;

            } else {
                return database.saveRoom(params[0]) > 0;
            }
        }

        protected void onPostExecute(Boolean result) {
            showRoomsScreen(result);
        }

    }

}
