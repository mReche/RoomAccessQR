package com.example.manoloreche.myapplication.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.manoloreche.myapplication.sql.DatabaseContract;

import java.util.UUID;

/**
 * Created by ManoloReche on 15/10/2017.
 */

public class Material  {
    private String name;
    private String quantity;
    private String  isFixed;
    private String id;

    public Material(String id, String name,String quantity, String isFixed) {
        this.name = name;
        this.quantity = quantity;
        this.isFixed = isFixed;
        this.id = id;
    }

    public Material(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(DatabaseContract.Materials.ID));
        name = cursor.getString(cursor.getColumnIndex(DatabaseContract.Materials.NAME));
        quantity = cursor.getString(cursor.getColumnIndex(DatabaseContract.Materials.QUANTITY));
        isFixed = cursor.getString(cursor.getColumnIndex(DatabaseContract.Materials.IS_FIXED));
    }

    public ContentValues toContentValues() {
        // Put values on table MAterial
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Materials.ID, id);
        values.put(DatabaseContract.Materials.NAME, name);
        values.put(DatabaseContract.Materials.QUANTITY, quantity);
        values.put(DatabaseContract.Materials.IS_FIXED, isFixed);
        return values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(String isFixed) {
        this.isFixed = isFixed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
