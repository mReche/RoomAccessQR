package com.example.manoloreche.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manoloreche.myapplication.models.Users;
import com.example.manoloreche.myapplication.sql.DBHelper;


public class userDetailFragment extends Fragment {

    private static final String ARG_USERS_ID = "arg_users_id";
    private String mUserID;
    private CollapsingToolbarLayout collapsingView;
    private ImageView avatar;
    private TextView phone;
    private TextView mail;
    private TextView rol;
    private TextView NID;
    private DBHelper database;

    public userDetailFragment() {
        // Required empty public constructor
    }

    public static userDetailFragment newInstance(String userID) {
        userDetailFragment fragment = new userDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERS_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null){
            mUserID = getArguments().getString(ARG_USERS_ID);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_detail, container, false);
        collapsingView = (CollapsingToolbarLayout)getActivity().findViewById(R.id.toolbar_layout);
        avatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        phone = (TextView) root.findViewById(R.id.tv_phone_number);
        mail = (TextView) root.findViewById(R.id.tv_mail);
        rol = (TextView) root.findViewById(R.id.roldetail);
        database = new DBHelper(getActivity());
        loadUser();
        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Acciones
    }

    private void loadUser() {
        new GetUserByIdTask().execute();
    }


    private void showUsers(Users users) {
        collapsingView.setTitle(users.getName()+" "+users.getSurename());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + users.getProfilePicture()))
                .centerCrop()
                .into(avatar);
        phone.setText(users.getPhone());
        mail.setText(users.getEmail());
        rol.setText(users.getIsAdmin());
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
                new DeleteUserTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditUserActivity.class);
        intent.putExtra(AdminView.EXTRA_USERS_ID, mUserID);
        startActivityForResult(intent, UsersFragment.REQUEST_UPDATE_DELETE_USER);
    }

    private class GetUserByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return database.getUserByID(mUserID);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showUsers(new Users(cursor));
            } else {
                showLoadError();
            }
        }

    }
    private class DeleteUserTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return database.deleteUser(mUserID);
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
