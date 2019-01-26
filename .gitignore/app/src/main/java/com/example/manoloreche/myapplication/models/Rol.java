package com.example.manoloreche.myapplication.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.manoloreche.myapplication.sql.DatabaseContract;

import java.util.UUID;

/**
 * Created by ManoloReche on 15/10/2017.
 */

public class Rol {
    private String rol;
    private String id;

    public Rol(String id, String rol) {
        this.rol = rol;
        this.id = id;
    }
    public Rol(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(DatabaseContract.Rols.ID));
        rol = cursor.getString(cursor.getColumnIndex(DatabaseContract.Rols.ROL));
    }

    public ContentValues contentValues(){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Rols.ID,id);
        values.put(DatabaseContract.Rols.ROL,rol);
        return values;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
