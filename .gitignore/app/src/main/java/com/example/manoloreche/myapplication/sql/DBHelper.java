package com.example.manoloreche.myapplication.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import com.example.manoloreche.myapplication.R;
import com.example.manoloreche.myapplication.models.Edifice;
import com.example.manoloreche.myapplication.models.Material;
import com.example.manoloreche.myapplication.models.Rol;
import com.example.manoloreche.myapplication.models.Rooms;
import com.example.manoloreche.myapplication.models.Users;
import com.example.manoloreche.myapplication.sql.DatabaseContract.Edifices;
import com.example.manoloreche.myapplication.sql.DatabaseContract.Materials;
import com.example.manoloreche.myapplication.sql.DatabaseContract.Rols;
import com.example.manoloreche.myapplication.sql.DatabaseContract.Room;
import com.example.manoloreche.myapplication.sql.DatabaseContract.User;
import com.example.manoloreche.myapplication.sql.DatabaseContract.Historical;


/**
 * Created by ManoloReche on 15/10/2017.
 * This class is used to manage application DataBase
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATA_BASE_NAME = "accesControl.db";
    private static final int CURRENT_VERSION = 1;
    private final Context context;

    public DBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, CURRENT_VERSION);
        this.context = context;
    }

    interface Table{
        String EDIFICE = "edifice";
        String MATERIAL = "material";
        String ROL = "rol";
        String ROOMS = "rooms";
        String USER = "user";
        String HISTORICAL = "historical";
    }
    /*References is for if we need to insert elements between tables*/
    interface References{
        String ID_EDIFICE = String.format("REFERENCES %s(%s)", Table.EDIFICE, Edifices.ID);
        String ID_MATERRIAL = String.format("REFERENCES %s(%s)", Table.MATERIAL, Materials.ID);
        String ID_ROL = String.format("REFERENCES %s(%s)", Table.ROL, Rols.ID);
        String ID_ROOMS = String.format("REFERENCES %s(%s)", Table.ROOMS, Room.ID);
        String ID_USER = String.format("REFERENCES %s(%s)", Table.USER, User.ID);
        String ID_HISTORICAL = String.format("REFERENCES %s(%s)", Table.HISTORICAL, Historical.ID);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL("CREATE TABLE " + Table.EDIFICE + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Edifices.ID + " TEXT NOT NULL,"
                + Edifices.NAME_EDIFICE + " TEXT NOT NULL,"
                + Edifices.NUM_EDIFICE+ " TEXT NOT NULL,"
                + Edifices.NUM_ROOMS + " TEXT NOT NULL,"
                + Edifices.OPEN_DAYS + " TEXT NOT NULL,"
                + Edifices.OPENING_TIME + " TEXT NOT NULL,"
                + Edifices.CLOSING_TIME + " TEXT NOT NULL,"
                + Edifices.IS_ACCESSIBLE + " TEXT NOT NULL,"
                + Edifices.QR + " TEXT,"
                + "UNIQUE (" + Edifices.ID  + "))");

        db.execSQL("CREATE TABLE " + Table.MATERIAL + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Materials.ID + " TEXT NOT NULL,"
                + Materials.NAME + " TEXT NOT NULL,"
                + Materials.QUANTITY + " TEXT NOT NULL,"
                + Materials.IS_FIXED + " TEXT NOT NULL,"
                + "UNIQUE (" + Materials.ID  + "))");

       db.execSQL("CREATE TABLE " + Table.ROL + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Rols.ID + " TEXT NOT NULL,"
                + Rols.ROL + " TEXT NOT NULL,"
                + "UNIQUE (" + Rols.ID  + "))");

        db.execSQL("CREATE TABLE " + Table.ROOMS + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Room.ID + " TEXT NOT NULL,"
                + Room.NAME_ROOM + " TEXT NOT NULL,"
                + Room.IS_ACCESSIBLE + " TEXT NOT NULL,"
                + Room.CAPACITY + " TEXT NOT NULL,"
                + Room.QR + " TEXT,"
                + Room.MATERIAL_NAME + " TEXT NOT NULL,"
                + Room.MATERIAL_QTY + " TEXT NOT NULL,"
                + Room.EDIFICE + " TEXT NOT NULL,"
                + "UNIQUE (" + Room.ID  + "))");

        db.execSQL("CREATE TABLE " + Table.USER + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + User.ID + " TEXT NOT NULL,"
                + User.NAME + " TEXT NOT NULL,"
                + User.SURENAME + " TEXT NOT NULL,"
                + User.PHONE + " TEXT NOT NULL,"
                + User.DNI + " TEXT NOT NULL,"
                + User.EMAIL + " TEXT NOT NULL,"
                + User.PASSWORD + " TEXT NOT NULL,"
                + User.IS_ADMIN + " TEXT NOT NULL,"
                + User.PROFILE_PICTURE + " TEXT,"
                + "UNIQUE (" + User.ID  + "))");

        db.execSQL("CREATE TABLE " + Table.HISTORICAL + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Historical.ID + " TEXT NOT NULL,"
                + Historical.USER_NAME + " TEXT NOT NULL,"
                + Historical.USER_LAST_NAME + " TEXT NOT NULL,"
                + Historical.ROOM + " TEXT NOT NULL,"
                + Historical.EDIFICE + " TEXT NOT NULL,"
                + Historical.ACCESS_HOUR + " TEXT NOT NULL,"
                + Historical.EMAIL + " TEXT NOT NULL,"
                + Historical.COLOR + " TEXT NOT NULL,"
                + "UNIQUE (" + BaseColumns._ID  + "))");

        // Do insert
        defaultData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table.EDIFICE);
        db.execSQL("DROP TABLE IF EXISTS " + Table.MATERIAL);
        db.execSQL("DROP TABLE IF EXISTS " + Table.ROL);
        db.execSQL("DROP TABLE IF EXISTS " + Table.ROOMS);
        db.execSQL("DROP TABLE IF EXISTS " + Table.USER);
        db.execSQL("DROP TABLE IF EXISTS " + Table.HISTORICAL);
    }

    private void defaultData(SQLiteDatabase db){
        //Edificios Mockup
        edificeData(db, new Edifice("0","Empresa","123","10","234","06:00:00","22:00:00","True",""));
        edificeData(db, new Edifice("1","Empresa2","123","10","234","06:00:00","22:00:00","True",""));
        //Usuarios Mockup
        userData(db, new Users("0a","Maria","Sanchez Rodrigez","654987123","18288109G","MariaSanchez@admin.com","a","admin","natalie _portman.jpg"));
        userData(db, new Users("a1","Pepe","Mel","620310756","71072131S","PepeMel@empresa.com","PepeMel_1","Software","mark_ruffalo.jpg"));
        userData(db, new Users("2a","Juan","Munuera Cardenas","657279950","98439347Z","JuanMunuera@empresa.com","JuanMunuera_1","Software","jeremy_renner.jpg"));
        userData(db, new Users("3a","Sonia","Madariaga Cuarteto","620645745","52620563J","SoniaMadariaga@empresa.com","SoniaMadariaga_1","Jefe","emma_stone.jpg"));
        userData(db, new Users("4a","Margarita","Barreto Sole","636417433","20586609E","MargaritaBarreto@empresa.com","MargaritaBarreto_1","Hardware","ashley_johnson.jpg"));
        userData(db, new Users("5a","Jose Ramon","Cañas Rubiales","674896150","25728133B","JoseCañas@empresa.com","JoseCañas_1","ManagerDeProyecto","robert_downey.jpg"));
        userData(db, new Users("6a","Joaquin","Campo Larrñaga","667623465","03843850K","JoaquinCampo@empresa.com","JoaquinCampo_1","Software","chri_hemsworth.jpg"));
        userData(db, new Users("7a","Susana","Oceda Pombo","636172507","36466830P","SusanaOceda@empresa.com","SusanaOceda_1","Hardware","scarlett_johansson.jpg"));
        userData(db, new Users("8a","Marcos","Pages Elizalde","645525331","34265359M","MarcosPages@empresa.com","MarcosPages_1","Limpidor","chris_evans.jpg"));
        userData(db, new Users("9a","Luisa","Bas Quintela","630880917","19334339X","LuisaBas@empresa.com","LuisaBas_1","Limpidor","cobi_smulders.jpg"));
        //Material Mockup
        Material materialSomething = new Material("1","Proyector, ordenador personal","1","yes");
        Material materialSomething2 = new Material("1","Ordenadores personales, monitores extras, placas de desarrollo","1","yes");
        //Salas Mockup
        roomsData(db, new Rooms("a1","Sala 1","Software, Hardware","40","Sala1.jpg",materialSomething2.getName(),materialSomething2.getQuantity(),"Empresa" ));
        roomsData(db, new Rooms("a2","Sala 2", "Jefe","40","",materialSomething.getName(),materialSomething.getQuantity(), "Empresa"));
        roomsData(db, new Rooms("a3","Sala 3","ManagerDeProyecto","40","",materialSomething2.getName(),materialSomething2.getQuantity(),"Empresa" ));
        materialData(db, materialSomething);
        //Roles Mockup
        roleData(db, new Rol("0","admin"));
        roleData(db, new Rol("1","BussinesBoss"));
        roleData(db, new Rol("2","ProjectBoss"));
        roleData(db, new Rol("3","Worker"));
        roleData(db, new Rol("4","CleanupWorker"));
        //db.close();
    }


    /**
     * Gestion de los usuarios
     */
    public int updateUsers(Users user, String userID ){
        return getWritableDatabase().update(
                Table.USER,
                user.contentValues(),
                User.ID + " LIKE ?",
                new String[]{userID}
        );
    }
    public int deleteUser(String user) {
        return getWritableDatabase().delete(
                Table.USER,
                User.ID + " LIKE ?",
                new String[]{user});
    }
    public long saveUser(Users user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                Table.USER,
                null,
                user.contentValues());

    }

    /**
     * Gestion de las salas
     */
    public int updateRooms(Rooms room, String roomID ){
        return getWritableDatabase().update(
                Table.ROOMS,
                room.contentValues(),
                Room.ID + " LIKE ?",
                new String[]{roomID}
        );
    }
    public int deleteRoom(String roomID) {
        return getWritableDatabase().delete(
                Table.ROOMS,
                Room.ID + " LIKE ?",
                new String[]{roomID});
    }
    public long saveRoom(Rooms room) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                Table.ROOMS,
                null,
                room.contentValues());

    }


    public long saveHistorial(com.example.manoloreche.myapplication.models.Historical historical) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                Table.HISTORICAL,
                null,
                historical.toContentValues());
    }


    /**
     * Functions to insert data into database
     * @return
     */
    public long edificeData(SQLiteDatabase db, Edifice aux) {
        return db.insert( Table.EDIFICE, null, aux.toContentValues());
    }
    public long materialData(SQLiteDatabase db, Material aux) {
        return db.insert( Table.MATERIAL, null, aux.toContentValues());
    }
    public long roleData(SQLiteDatabase db, Rol aux) {
        return db.insert( Table.ROL, null, aux.contentValues());
    }
    public long roomsData(SQLiteDatabase db, Rooms aux) {
        return db.insert( Table.ROOMS, null, aux.contentValues());
    }
    public long userData(SQLiteDatabase db, Users aux) {
        return db.insert( Table.USER, null, aux.contentValues());
    }
    public long historicalData(SQLiteDatabase db, com.example.manoloreche.myapplication.models.Historical aux) {
        return db.insert( Table.HISTORICAL, null, aux.toContentValues());
    }

    public Edifice getEdificeData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + Table.EDIFICE, null);
        if(c != null){
            c.moveToFirst();
        }
        Edifice edifice = new Edifice(c.getString(1).toString(),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9));
        db.close();
        c.close();
        return edifice;
    }
    public Cursor getEdificeById(String edificeId) {
        Cursor c = getReadableDatabase().query(
                Table.EDIFICE,
                null,
                Edifices.ID + " LIKE ?",
                new String[]{edificeId},
                null,
                null,
                null);
        return c;
    }
    public Rooms getRoomsData(String nameRoom){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + Table.ROOMS +" WHERE "+ Room.NAME_ROOM + " =?", new String[] {nameRoom });
        if(c != null){
            c.moveToFirst();
        }
        //Material[] material = new Material[]{getMateialData()};
        Rooms rooms = new Rooms(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8));
        db.close();
        c.close();
        return rooms;
    }
    public Material getMateialData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + Table.MATERIAL, null);
        if(c != null){
            c.moveToNext();
        }
        Material material = new Material(c.getString(1),c.getString(2),c.getString(3),c.getString(4));
        db.close();
        c.close();
        return material;
    }
    public Users getUserData(String email){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from "+ Table.USER + " WHERE "+ User.EMAIL + " =?", new String[] {email});
        if(c != null){
            c.moveToNext();
        }
        Users user = new Users(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9));
        db.close();
        c.close();
        return user;
    }
    public Rol getRolData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + Table.ROL, null);
        if(c != null){
            c.moveToNext();
        }
        Rol rol = new Rol(c.getString(1),c.getString(2));
        db.close();
        c.close();
        return rol;
    }

    /**
     * List of users/rooms/edifices...
     */
    /*public List<Users> getAllUsers(){

        String sortOrder = User.NAME +" ASC";
        List<Users> userList = new ArrayList<Users>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + Table.USER + " ORDER BY "+ sortOrder, null);
        if(c.moveToFirst()){
            do {
                Users userCursor = new Users(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8));
                userCursor.setId(c.getString(c.getColumnIndex(User.ID)));
                userCursor.setName(c.getString(c.getColumnIndex(User.NAME)));
                userList.add(userCursor);
            }while (c.moveToNext());

            c.close();
            db.close();
        }
        return userList;
    }*/

    public Cursor getAllUsersCursor(){
        return getReadableDatabase().query(Table.USER,
                null,
                null,
                null,
                null,
                null,
                User.NAME +" ASC");
    }
    public Cursor getUserByID(String userId) {
        Cursor c = getReadableDatabase().query(
                Table.USER,
                null,
                User.ID + " LIKE ?",
                new String[]{userId},
                null,
                null,
                null);
        return c;
    }
    public Cursor getAllRoomsCursor(){
        return getReadableDatabase().query(Table.ROOMS,
                null,
                null,
                null,
                null,
                null,
                Room.NAME_ROOM + " ASC");
    }
    public Cursor getRoomByNumber(String number) {
        Cursor c = getReadableDatabase().query(
                Table.ROOMS,
                null,
                Room.NAME_ROOM + " LIKE ?",
                new String[]{number},
                null,
                null,
                null);
        return c;
    }
    public Cursor getRoomByID(String roomId) {
        Cursor c = getReadableDatabase().query(
                Table.ROOMS,
                null,
                Room.ID + " LIKE ?",
                new String[]{roomId},
                null,
                null,
                Room.NAME_ROOM + " ASC");
        return c;
    }

    public Cursor getAllHistoricalsCursor(){
        return getReadableDatabase().query(Table.HISTORICAL,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor getHistoricalsByMail(String email){
        return getReadableDatabase().query(
                Table.HISTORICAL,
                null,
                Historical.EMAIL + " LIKE ?",
                new String[]{email},
                null,
                null,
                null,
                null);
    }
    /**
     * Check if the email and password are corrects in order to do the login.
     */
    public boolean checkUser(String email, String password){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + Table.USER + " WHERE "+ User.EMAIL + " =?" + " AND " + User.PASSWORD + " =?", new String[] {email, password});
        int cursorCount = c.getCount();
        c.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    /**
     * Check if the RoomName exits
     */
    public boolean checkRoomName(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + Table.ROOMS + " WHERE "+ Room.NAME_ROOM + " =?" , new String[] {name});
        int cursorCount = c.getCount();
        c.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    public boolean checkEdificeName(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + Table.EDIFICE + " WHERE "+ Edifices.NAME_EDIFICE + " =?" , new String[] {name});
        int cursorCount = c.getCount();
        c.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }




}
