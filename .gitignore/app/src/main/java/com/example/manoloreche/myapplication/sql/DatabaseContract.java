package com.example.manoloreche.myapplication.sql;

import android.provider.BaseColumns;


import java.util.UUID;

/**
 * Created by ManoloReche on 15/10/2017.
 */

public class DatabaseContract {
    interface EdificeColumn extends BaseColumns {
        String ID = "id";
        String NAME_EDIFICE = "name_edifice";
        String NUM_EDIFICE = "num_edifice";
        String NUM_ROOMS = "num_rooms";
        String OPEN_DAYS  = "opend_days";
        String OPENING_TIME  = "opening_time";
        String CLOSING_TIME = "closing_time";
        String IS_ACCESSIBLE = "is_accessible";
        String QR = "qr";
    }

    interface MaterialColumn{
        String ID = "id";
        String NAME = "name";
        String QUANTITY = "quantity";
        String IS_FIXED = "is_fixed";
    }

    interface RolColumn{
        String ID = "id";
        String ROL = "rol";
    }

    interface  RoomsColumn{
        String ID = "id";
        String NAME_ROOM = "name_room";
        String IS_ACCESSIBLE = "is_accessible";
        String CAPACITY = "capacity";
        String QR = "qr";
        String MATERIAL_NAME = "material_name";
        String MATERIAL_QTY = "material_qty";
        String EDIFICE = "edifice";
    }

    interface  UserColumn{
        String ID = "id";
        String NAME = "name";
        String SURENAME = "surename";
        String PHONE = "phone";
        String DNI = "DNI";
        String EMAIL = "email";
        String PASSWORD = "password";
        String IS_ADMIN = "is_admin";
        String PROFILE_PICTURE = "profile_picture";
    }

    interface HistoricalColumn{
        String ID = "id";
        String USER_NAME = "user_name";
        String USER_LAST_NAME = "user_last_name";
        String ROOM = "room";
        String EDIFICE = "edifice";
        String ACCESS_HOUR = "access_hour";
        String EMAIL = "email";
        String COLOR = "color";

    }

    public static class Edifices implements EdificeColumn {
        public static String generateIdEdifices() {
            return "E-" + UUID.randomUUID().toString();
        }
    }
    public static class Materials implements MaterialColumn {
        public static String generateIdMaterials() {
            return "M-" + UUID.randomUUID().toString();
        }
    }

    public static class Rols implements RolColumn {
        public static String generateIdRol() {
            return "R-" + UUID.randomUUID().toString();
        }
    }
    public static class Room implements RoomsColumn {
        public static String generateIdRoom() {
            return "Rm-" + UUID.randomUUID().toString();
        }
    }
    public static class User implements UserColumn {
        public static String generateIdUser() {
            return "U-" + UUID.randomUUID().toString();
        }
    }
    public static class Historical implements HistoricalColumn{
        public static String generateIdHistorical() {
            return "H-" + UUID.randomUUID().toString();
        }
    }

    private DatabaseContract(){}
}
