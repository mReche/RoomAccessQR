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
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {

    private DBHelper database;
    private ListView listView;
    private UsersCusrsorAdapter usersAdapter;
    private FloatingActionButton addButton;
    public static final int REQUEST_UPDATE_DELETE_USER = 2;

    public UsersFragment() {
        // Required empty public constructor
    }
    public static UsersFragment newInstance() {
        UsersFragment fragment = new UsersFragment();
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
        View root = inflater.inflate(R.layout.fragment_users, container, false);
        listView = (ListView) root.findViewById(R.id.users_list);
        usersAdapter = new UsersCusrsorAdapter(getActivity(),null);
        addButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddScreen();
            }
        });
        listView.setAdapter(usersAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor currentItem = (Cursor) usersAdapter.getItem(position);
                String currentUserID = currentItem.getString(currentItem.getColumnIndex(DatabaseContract.User.ID));
                showDetailScreen(currentUserID);
            }
        });
        database = new DBHelper(getActivity());
        loadUseres();

        return root;
    }
    private void loadUseres(){
        new UsersLoadTask().execute();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditUserActivity.class);
        startActivityForResult(intent, AddEditUserActivity.REQUEST_ADD_USER);
    }
    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                R.string.correct_save, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_USER:
                    loadUseres();
                    break;
                case AddEditUserActivity.REQUEST_ADD_USER:
                    showSuccessfullSavedMessage();
                    loadUseres();
                    break;
            }
        }
    }
    private void showDetailScreen(String userID) {
        Intent intent = new Intent(getActivity(), UserDetail.class);
        intent.putExtra(AdminView.EXTRA_USERS_ID, userID);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_USER);
    }

    private class UsersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return database.getAllUsersCursor();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                usersAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
