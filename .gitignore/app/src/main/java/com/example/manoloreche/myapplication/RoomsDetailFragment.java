package com.example.manoloreche.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manoloreche.myapplication.models.Rooms;
import com.example.manoloreche.myapplication.sql.DBHelper;


public class RoomsDetailFragment extends Fragment {

    private static final String ARG_ROOMS_ID = "arg_rooms_id";
    private String mRoomsID;
    private CollapsingToolbarLayout collapsingView;
    private TextView nameR, nameB, material ;
    private DBHelper database;

    public RoomsDetailFragment() {
        // Required empty public constructor
    }

    public static RoomsDetailFragment newInstance(String roomID) {
        RoomsDetailFragment fragment = new RoomsDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROOMS_ID, roomID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null){
            mRoomsID = getArguments().getString(ARG_ROOMS_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rooms_detail, container, false);
        collapsingView = (CollapsingToolbarLayout)getActivity().findViewById(R.id.toolbar_layout);
        nameR = (TextView)root.findViewById(R.id.tv_room_name);
        nameB = (TextView)root.findViewById(R.id.tv_pertenece_edificio);
        material = (TextView)root.findViewById(R.id.tv_material);
        database = new DBHelper(getActivity());
        loadRooms();
        return root;
    }
    private void loadRooms() {
        new GetRoomByIdTask().execute();
    }

    private void showRooms (Rooms rooms){
        collapsingView.setTitle(rooms.getNameRoom());
        nameR.setText(rooms.getIsAccessible());
        nameB.setText(rooms.getEdifice());
        material.setText(rooms.getMaterialName());
    }
    private void showLoadError() {
        Toast.makeText(getActivity(),
                R.string.err_loading, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteRoomTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditRoomActivity.class);
        intent.putExtra(RoomsActivity.EXTRA_ROOMS_ID, mRoomsID);
        startActivityForResult(intent, RoomsFragment.REQUEST_UPDATE_DELETE_ROOM);
    }

    private class GetRoomByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return database.getRoomByID(mRoomsID);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showRooms(new Rooms(cursor));
            } else {
                showLoadError();
            }
        }

    }

    private class DeleteRoomTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return database.deleteRoom(mRoomsID);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showUsersScreen(integer > 0);
        }

        private void showUsersScreen(boolean requery) {
            if (!requery) {
                showDeleteError();
            }
            getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
            getActivity().finish();
        }

        private void showDeleteError() {
            Toast.makeText(getActivity(),
                    R.string.err_deleting, Toast.LENGTH_SHORT).show();
        }

    }

}
