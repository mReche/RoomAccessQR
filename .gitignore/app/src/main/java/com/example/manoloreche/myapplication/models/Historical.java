package com.example.manoloreche.myapplication.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.manoloreche.myapplication.sql.DatabaseContract;

/**
 * Created by ManoloReche on 15/11/2017.
 */

public class Historical {

    private String id;
    private String userName;
    private String userLastName;
    private String room;
    private String edifice;
    private String accessHour;
    private String email;
    private String color;

    public Historical(String id,String userName, String userLastName, String room, String edifice, String accessHour, String email, String color){
        this.id = id;
        this.userName = userName;
        this.userLastName = userLastName;
        this.room = room;
        this.edifice = edifice;
        this.accessHour = accessHour;
        this.email = email;
        this.color = color;
    }

    public Historical(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.ID));
        userName = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.USER_NAME));
        userLastName = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.USER_LAST_NAME));
        room = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.ROOM));
        edifice = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.EDIFICE));
        accessHour = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.ACCESS_HOUR));
        email = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.EMAIL));
        color = cursor.getString(cursor.getColumnIndex(DatabaseContract.Historical.COLOR));
    }

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(String.valueOf(DatabaseContract.Historical.ID), id);
        values.put(DatabaseContract.Historical.USER_NAME,userName);
        values.put(DatabaseContract.Historical.USER_LAST_NAME,userLastName);
        values.put(DatabaseContract.Historical.ROOM,room);
        values.put(DatabaseContract.Historical.EDIFICE,edifice);
        values.put(DatabaseContract.Historical.ACCESS_HOUR,accessHour);
        values.put(DatabaseContract.Historical.EMAIL,email);
        values.put(DatabaseContract.Historical.COLOR,color);
        return values;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getEdifice() {
        return edifice;
    }

    public void setEdifice(String edifice) {
        this.edifice = edifice;
    }

    public String getAccessHour() {
        return accessHour;
    }

    public void setAccessHour(String accessHour) {
        this.accessHour = accessHour;
    }
}
