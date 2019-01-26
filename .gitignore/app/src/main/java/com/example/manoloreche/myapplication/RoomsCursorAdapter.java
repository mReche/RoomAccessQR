package com.example.manoloreche.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.manoloreche.myapplication.sql.DatabaseContract;

/**
 * Created by ManoloReche on 02/01/2018.
 */

class RoomsCursorAdapter extends CursorAdapter {
    public RoomsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_room,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameText = (TextView) view.findViewById(R.id.tv_name_room);
        TextView nameBuildingText = (TextView) view.findViewById(R.id.tv_pertany);

        String nameR = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.NAME_ROOM));
        String nameB = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.EDIFICE));
        nameText.setText(nameR);
        nameBuildingText.setText(nameB);
    }
}
