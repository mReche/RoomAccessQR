package com.example.manoloreche.myapplication.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.example.manoloreche.myapplication.sql.DatabaseContract;

import java.util.UUID;

/**
 * Created by ManoloReche on 15/10/2017.
 */

public class Users {
    private String Name;
    private String Surename;
    private String phone;
    private String DNI;
    private String email;
    private String password;
    private String isAdmin;
    private String profilePicture;
    private String id;

    public Users(String id, String name, String surename, String phone, String DNI, String email, String password, String isAdmin, String profilePicture) {
        Name = name;
        Surename = surename;
        this.phone = phone;
        this.DNI = DNI;
        this.email = email;
        this.password = password;
        //isAdmim es el ROL
        this.isAdmin = isAdmin;
        this.profilePicture = profilePicture;
        this.id = id;
    }

    public Users(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.ID));
        Name = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.NAME));
        Surename = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.SURENAME));
        phone = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.PHONE));
        DNI = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.DNI));
        email = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.EMAIL));
        password = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.PASSWORD));
        isAdmin = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.IS_ADMIN));
        profilePicture = cursor.getString(cursor.getColumnIndex(DatabaseContract.User.PROFILE_PICTURE));
    }

    public ContentValues contentValues(){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.User.ID,id);
        values.put(DatabaseContract.User.NAME,Name);
        values.put(DatabaseContract.User.SURENAME,Surename);
        values.put(DatabaseContract.User.PHONE,phone);
        values.put(DatabaseContract.User.DNI,DNI);
        values.put(DatabaseContract.User.EMAIL,email);
        values.put(DatabaseContract.User.PASSWORD,password);
        values.put(DatabaseContract.User.IS_ADMIN,isAdmin);
        values.put(DatabaseContract.User.PROFILE_PICTURE,profilePicture);
        return values;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurename() {
        return Surename;
    }

    public void setSurename(String surename) {
        Surename = surename;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
