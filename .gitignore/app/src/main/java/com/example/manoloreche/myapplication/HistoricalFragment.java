package com.example.manoloreche.myapplication;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.manoloreche.myapplication.sql.DBHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricalFragment extends Fragment {

    private static final String ARG_EMAIL_ID = "arg_email_id";
    private DBHelper database;
    private ListView listView;
    private HistoricalCursorAdapter historicalAdapter;
    private String mMail;

    public HistoricalFragment() {
        // Required empty public constructor
    }

    public static HistoricalFragment newInstance(String mail) {
        HistoricalFragment fragment = new HistoricalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL_ID, mail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMail = getArguments().getString(ARG_EMAIL_ID);
            Log.e("mail rebut =", mMail);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_historical, container, false);
        listView = (ListView) root.findViewById(R.id.historical_list);
        historicalAdapter = new HistoricalCursorAdapter(getActivity(),null);
        listView.setAdapter(historicalAdapter);
        database = new DBHelper(getActivity());
        loadHistorical();
        return root;
    }

    private void loadHistorical() {
        new HistoricalLoadTask().execute();
    }

    private class HistoricalLoadTask extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... params) {
            Log.e("persona:", database.getUserData(mMail).getIsAdmin());
            if (database.getUserData(mMail).getIsAdmin().equals("admin"))
                return database.getAllHistoricalsCursor();
            else
                return database.getHistoricalsByMail(mMail);
        }
        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                historicalAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
