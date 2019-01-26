package com.example.manoloreche.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.manoloreche.myapplication.sql.DBHelper;
import com.example.manoloreche.myapplication.sql.DatabaseContract;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RoomsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoomsFragment extends Fragment {

    private DBHelper database;
    private ListView listView;
    private RoomsCursorAdapter roomsAdapter;
    private FloatingActionButton addButton;
    public static final int REQUEST_UPDATE_DELETE_ROOM = 2;

    public RoomsFragment() {
        // Required empty public constructor
    }

    public static RoomsFragment newInstance() {
        RoomsFragment fragment = new RoomsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_rooms, container, false);
        listView = (ListView) root.findViewById(R.id.rooms_list);
        roomsAdapter = new RoomsCursorAdapter(getActivity(),null);
        addButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScreen();
            }
        });
        listView.setAdapter(roomsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor) roomsAdapter.getItem(position);
                String currentRoomID = currentItem.getString(currentItem.getColumnIndex(DatabaseContract.Room.ID));
                showDetailScreen(currentRoomID);
            }
        });
        database = new DBHelper(getActivity());
        loadRooms();
        return root;
    }
    private void loadRooms(){
        new RoomsLoadTask().execute();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditRoomActivity.class);
        startActivityForResult(intent, AddEditRoomActivity.REQUEST_ADD_ROOM);
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                R.string.correct_save, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_ROOM:
                    loadRooms();
                    break;
                case AddEditRoomActivity.REQUEST_ADD_ROOM:
                    showSuccessfullSavedMessage();
                    loadRooms();
                    break;
            }
        }
    }
    private void showDetailScreen(String roomId) {
        Intent intent = new Intent(getActivity(), RoomsDetailActivity.class);
        intent.putExtra(RoomsActivity.EXTRA_ROOMS_ID, roomId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_ROOM);
    }
    private class RoomsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return database.getAllRoomsCursor();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                roomsAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }



}
