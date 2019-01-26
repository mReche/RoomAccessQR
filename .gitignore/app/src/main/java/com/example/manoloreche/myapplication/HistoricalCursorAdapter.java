package com.example.manoloreche.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.manoloreche.myapplication.sql.DatabaseContract;

/**
 * Created by ManoloReche on 17/01/2018.
 */

public class HistoricalCursorAdapter extends CursorAdapter{
    public HistoricalCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_historical,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameUserV, nameRoomV, nameBuildingV, hourV, mailV;
        nameUserV = (TextView) view.findViewById(R.id.name_surename_hist);
        nameRoomV = (TextView) view.findViewById(R.id.room_hist);
        nameBuildingV = (TextView) view.findViewById(R.id.edifice_hist);
        hourV = (TextView) view.findViewById(R.id.access_hour_hist);
        mailV = (TextView) view.findViewById(R.id.email_hist);

        String nameUser, nameRoom, nameBuilding, hour, mail, color;
        nameUser = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.USER_NAME)) + " " + cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.USER_LAST_NAME));
        nameRoom = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.ROOM));
        nameBuilding = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.EDIFICE));
        hour = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.ACCESS_HOUR));
        mail = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.EMAIL));
        color = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.COLOR));

        nameUserV.setText(nameUser);
        nameUserV.setTextColor(Color.parseColor(color));
        nameRoomV.setText(nameRoom);
        nameRoomV.setTextColor(Color.parseColor(color));
        nameBuildingV.setText(nameBuilding);
        nameBuildingV.setTextColor(Color.parseColor(color));
        hourV.setText(hour);
        hourV.setTextColor(Color.parseColor(color));
        mailV.setText(mail);
        mailV.setTextColor(Color.parseColor(color));

    }
}
