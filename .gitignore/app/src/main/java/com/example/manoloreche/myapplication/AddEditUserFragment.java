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
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.manoloreche.myapplication.models.Users;
import com.example.manoloreche.myapplication.sql.DBHelper;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditUserFragment extends Fragment {
    private static final String ARG_USER_ID = "arg_user_id";
    private String mUserID;
    private DBHelper database;

    private FloatingActionButton saveButton;
    private TextInputEditText nameField;
    private TextInputEditText surenameField;
    private TextInputEditText phoneField;
    private TextInputEditText emailField;
    private TextInputEditText DNIField;
    private TextInputEditText passwordField;
    private TextInputEditText rolField;

    public AddEditUserFragment() {
        // Required empty public constructor
    }

    public static AddEditUserFragment newInstance(String userID) {
        AddEditUserFragment fragment = new AddEditUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserID = getArguments().getString(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_edit_user, container, false);
        saveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        nameField = (TextInputEditText) root.findViewById(R.id.et_name);
        surenameField = (TextInputEditText) root.findViewById(R.id.et_surename);
        phoneField = (TextInputEditText) root.findViewById(R.id.et_phone);
        emailField = (TextInputEditText) root.findViewById(R.id.et_email);
        DNIField = (TextInputEditText) root.findViewById(R.id.et_dni);
        passwordField = (TextInputEditText) root.findViewById(R.id.et_pass);
        rolField = (TextInputEditText) root.findViewById(R.id.et_role);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditUser();
            }
        });
        database = new DBHelper(getActivity());
        if(mUserID != null){
            loadUser();
        }

        return root;
    }

    private void loadUser(){
        new GetUserByIdTask().execute();

    }

    private void addEditUser() {
        boolean error = false;

        String name = nameField.getText().toString();
        String surename = surenameField.getText().toString();
        String phoneNumber = phoneField.getText().toString();
        String email = emailField.getText().toString();
        String DNI = DNIField.getText().toString();;
        String password = passwordField.getText().toString();
        String rol = rolField.getText().toString();

        if (TextUtils.isEmpty(name)) {
            nameField.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(surename)) {
            surenameField.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(phoneNumber) || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            phoneField.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError(getString(R.string.field_error_mail));
            error = true;
        }
        if (TextUtils.isEmpty(DNI)) {
            DNIField.setError(getString(R.string.field_error));
            error = true;
        }
        if (TextUtils.isEmpty(password) || !(password.length()>8) || password.equals(password.toUpperCase()) ||
                password.matches("[A-Za-z0-9 ]*") || (password.contains("AND") || password.contains("NOT"))) {
            passwordField.setError(getString(R.string.field_error_pass1));
            error = true;
        }

        if (TextUtils.isEmpty(rol)) {
            rolField.setError(getString(R.string.field_error));
            error = true;
        }
        if (error) {
            return;
        }

        Random rand = new Random(System.currentTimeMillis());
        rand.setSeed(System.currentTimeMillis());
        int intrand = rand.nextInt(100000000);
        String id = String.valueOf(intrand);

        Users user = new Users(id, name, surename, phoneNumber,DNI, email, password, rol,"default.jpg");
        new AddEditUsersTask().execute(user);

    }
    private void showUsersScreen(Boolean requery) {
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
    private void showUsers(Users users){
        nameField.setText(users.getName());
        surenameField.setText(users.getSurename());
        phoneField.setText(users.getPhone());
        emailField.setText(users.getEmail());
        passwordField.setText(users.getPassword());
        DNIField.setText(users.getDNI());
        rolField.setText(users.getIsAdmin());
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
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditUsersTask extends AsyncTask<Users, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Users... users) {
            if (mUserID != null) {
                return database.updateUsers(users[0], mUserID) > 0;

            } else {
                return database.saveUser(users[0]) > 0;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {
            showUsersScreen(result);
        }
    }




}
