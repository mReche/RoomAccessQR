package com.example.manoloreche.myapplication.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.example.manoloreche.myapplication.sql.DatabaseContract;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by ManoloReche on 15/10/2017.
 */

public class Edifice {

    private String nameEdifice;
    private String numEdifice;
    private String numRooms;
    private String openDays;
    private String openingTime;
    private String closingTIme;
    private String isAccessible;
    private String QR;
    private String ID;

    public Edifice(String id, String nameEdifice, String numEdifice, String numRooms, String openDays, String openingTime, String closingTIme, String isAccessible, String QR) {
        this.nameEdifice = nameEdifice;
        this.numEdifice = numEdifice;
        this.numRooms = numRooms;
        this.openDays = openDays;
        this.openingTime = openingTime;
        this.closingTIme = closingTIme;
        this.isAccessible = isAccessible;
        this.QR = QR;
        this.ID = id;
    }
    public Edifice(Cursor cursor){
        nameEdifice = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.NAME_EDIFICE));
        numEdifice = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.NUM_EDIFICE));
        numRooms = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.NUM_ROOMS));
        openDays = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.OPEN_DAYS));
        openingTime = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.OPENING_TIME));
        closingTIme = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.CLOSING_TIME));
        isAccessible = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.IS_ACCESSIBLE));
        QR = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.QR));
        ID = cursor.getString(cursor.getColumnIndex(DatabaseContract.Edifices.ID));
    }


    public ContentValues toContentValues() {
        // Put values on table Edificies
        ContentValues values = new ContentValues();
        values.put(String.valueOf(DatabaseContract.Edifices.ID), ID);
        values.put(DatabaseContract.Edifices.NAME_EDIFICE,nameEdifice);
        values.put(DatabaseContract.Edifices.NUM_EDIFICE,numEdifice);
        values.put(DatabaseContract.Edifices.NUM_ROOMS,numRooms);
        values.put(DatabaseContract.Edifices.OPEN_DAYS,openDays);
        values.put(DatabaseContract.Edifices.OPENING_TIME,openingTime);
        values.put(DatabaseContract.Edifices.CLOSING_TIME,closingTIme);
        values.put(DatabaseContract.Edifices.IS_ACCESSIBLE,isAccessible);
        values.put(DatabaseContract.Edifices.QR,QR);
        return values;
    }


    public String getNameEdifice() {
        return nameEdifice;
    }

    public void setNameEdifice(String nameEdifice) {
        this.nameEdifice = nameEdifice;
    }

    public String getNumEdifice() {
        return numEdifice;
    }

    public void setNumEdifice(String numEdifice) {
        this.numEdifice = numEdifice;
    }

    public String getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(String numRooms) {
        this.numRooms = numRooms;
    }

    public String getOpenDays() {
        return openDays;
    }

    public void setOpenDays(String openDays) {
        this.openDays = openDays;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTIme() {
        return closingTIme;
    }

    public void setClosingTIme(String closingTIme) {
        this.closingTIme = closingTIme;
    }

    public String getIsAccessible() {
        return isAccessible;
    }

    public void setIsAccessible(String isAccessible) {
        this.isAccessible = isAccessible;
    }

    public String getQR() {
        return QR;
    }

    public void setQR(String QR) {
        this.QR = QR;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


}
