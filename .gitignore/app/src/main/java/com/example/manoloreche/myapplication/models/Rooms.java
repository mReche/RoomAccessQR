package com.example.manoloreche.myapplication.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.example.manoloreche.myapplication.sql.DatabaseContract;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ManoloReche on 15/10/2017.
 */

public class Rooms {
    private String nameRoom;
    private String isAccessible;
    private String capacity;
    private String QR;
    private String id;
    private Material[] mat;
    private String materialName;
    private String materialQty;
    private String edifice;

    public Rooms(String id,String nameRoom, String isAccessible, String capacity, String QR, String mater, String materQ, String edifice) {
        this.nameRoom = nameRoom;
        this.isAccessible = isAccessible;
        this.capacity = capacity;
        this.QR = QR;
        this.id = id;
        this.materialName = mater;
        this.materialQty = materQ;
        this.edifice = edifice;
    }

    public Rooms(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.ID));
        nameRoom = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.NAME_ROOM));
        isAccessible = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.IS_ACCESSIBLE));
        capacity = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.CAPACITY));
        QR = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.QR));
        materialName = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.MATERIAL_NAME));
        materialQty = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.MATERIAL_QTY));
        edifice = cursor.getString(cursor.getColumnIndex(DatabaseContract.Room.EDIFICE));
    }

    public ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Room.ID,id);
        values.put(DatabaseContract.Room.NAME_ROOM,nameRoom);
        values.put(DatabaseContract.Room.IS_ACCESSIBLE,isAccessible);
        values.put(DatabaseContract.Room.CAPACITY,capacity);
        values.put(DatabaseContract.Room.QR,QR);
        values.put(DatabaseContract.Room.MATERIAL_NAME,materialName);
        values.put(DatabaseContract.Room.MATERIAL_QTY,materialQty);
        values.put(DatabaseContract.Room.EDIFICE,edifice);
        return values;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public String getIsAccessible() {
        return isAccessible;
    }

    public void setIsAccessible(String isAccessible) {
        this.isAccessible = isAccessible;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getQR() {
        return QR;
    }

    public String getEdifice() {
        return edifice;
    }

    public void setEdifice(String edifice) {
        this.edifice = edifice;
    }

    public void setQR(String QR) {
        this.QR = QR;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Material[] getMat() {
        return mat;
    }

    public void setMat(Material[] mat) {
        this.mat = mat;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialQty() {
        return materialQty;
    }

    public void setMaterialQty(String materialQty) {
        this.materialQty = materialQty;
    }
}
