package com.example.manoloreche.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.manoloreche.myapplication.models.Historical;
import com.example.manoloreche.myapplication.sql.DBHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UserView extends AppCompatActivity {

    private DBHelper database;
    private ImageView profilePic;
    private TextView namesurename;
    private TextView phone;
    private TextView email;
    private TextView textButton;
    private String mailFromLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_view);
        profilePic = (ImageView)findViewById(R.id.profilePic);
        namesurename = (TextView)findViewById(R.id.ameNsurename);
        phone = (TextView)findViewById(R.id.phoneNumber);
        email = (TextView)findViewById(R.id.mail);
        textButton = (TextView)findViewById(R.id.historicalButton);
        database = new DBHelper(getApplicationContext());
        final Activity activity = this;

        /*
        Get login mail from other activity
         */
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mailFromLogin = null;
            } else {
                mailFromLogin = extras.getString("neededMail");
            }

        } else {
            mailFromLogin = (String) savedInstanceState.getSerializable("neededMail");
        }

        namesurename.setText(database.getUserData(mailFromLogin).getName()+" "+database.getUserData(mailFromLogin).getSurename());
        phone.setText(database.getUserData(mailFromLogin).getPhone());
        email.setText(database.getUserData(mailFromLogin).getEmail());
        Glide
                .with(getApplicationContext())
                .load(Uri.parse("file:///android_asset/" + database.getUserData(mailFromLogin).getProfilePicture()))
                .asBitmap()
                .error(R.mipmap.ic_profile_image)
                .centerCrop()
                .into(new BitmapImageViewTarget(profilePic) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(getApplication().getResources(), resource);
                        drawable.setCircular(true);
                        profilePic.setImageDrawable(drawable);
                    }
                });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCamera);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /*Intent cameraIntent = new Intent(getApplicationContext(),CameraManagement.class);
                startActivity(cameraIntent);*/
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historical = new Intent(getApplicationContext(),HistoricalActivity.class);
                historical.putExtra("neededMail",mailFromLogin);
                startActivity(historical);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, getString(R.string.cancell_scan), Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    if(checkRoomAccessible(result)){
                        Toast.makeText(this, getString(R.string.correct_access),Toast.LENGTH_LONG).show();
                        saveToDatabaseRoom(result);
                    }else{
                        if(checkEdificeAccessible(result)){
                            Toast.makeText(this, getString(R.string.correct_access),Toast.LENGTH_LONG).show();
                            saveToDatabaseEdifice(result);
                        }else{
                            Toast.makeText(this, getString(R.string.denied_access),Toast.LENGTH_LONG).show();
                            saveToDatabaseUnauthorizedAccess();
                        }
                    }
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    public boolean checkRoomAccessible(IntentResult result){
        if(database.checkRoomName(result.getContents().toString())){
            if(database.getRoomsData(result.getContents().toString()).getNameRoom().equals(result.getContents())
                    && (database.getRoomsData(result.getContents().toString()).getIsAccessible().contains(database.getUserData(mailFromLogin).getIsAdmin()))
                    || database.getUserData(mailFromLogin).getIsAdmin().equals(R.string.boss) || database.getUserData(mailFromLogin).getIsAdmin().equals(R.string.cleanup)
                    || (database.getUserData(mailFromLogin).getIsAdmin().equals(R.string.project_manager)
                    &&((database.getRoomsData(result.getContents().toString()).getIsAccessible().contains("Software")
                    || (database.getRoomsData(result.getContents().toString()).getIsAccessible().contains("Hardware")))))){
                return true;
            }else{
                //Toast.makeText(this, getString(R.string.denied_access),Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            //Toast.makeText(this, getString(R.string.the_room) + result.getContents() + getString(R.string.not_exist) ,Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean checkEdificeAccessible(IntentResult result) throws java.text.ParseException {
        GregorianCalendar gcalendar = new GregorianCalendar();
        String dataStringParsed, seconds;
        if(gcalendar.get(Calendar.SECOND)<10){
            seconds = "0" + gcalendar.get(Calendar.SECOND);
        }else{
            seconds = String.valueOf(gcalendar.get(Calendar.SECOND));
        }
        dataStringParsed = gcalendar.get(Calendar.HOUR_OF_DAY) + ":" + gcalendar.get(Calendar.MINUTE) + ":" + seconds;

        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date dateO = format.parse(database.getEdificeData().getOpeningTime());
        Date dateC = format.parse(database.getEdificeData().getClosingTIme());
        Date currentDate = format.parse(dataStringParsed);
        if(database.checkEdificeName(result.getContents().toString())){
            if( dateO.before(currentDate) && dateC.after(currentDate) && (((int)gcalendar.get(Calendar.MONTH))+1 != 8)){
                return true;
            }else{
                //Toast.makeText(this, "Denegado en edificio",Toast.LENGTH_LONG).show();
                return false;
            }
        }else{
            //Toast.makeText(this, "Nombre edificio" ,Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void saveToDatabaseRoom(IntentResult result) throws java.text.ParseException {
        String name, lastName, room, edifice, accesHour, email,color;
        int month;
        GregorianCalendar gcalendar = new GregorianCalendar();
        String dataStringParsed, seconds;
        if(gcalendar.get(Calendar.SECOND)<10){
            seconds = "0" + gcalendar.get(Calendar.SECOND);
        }else{
            seconds = String.valueOf(gcalendar.get(Calendar.SECOND));
        }
        month = ((int) gcalendar.get(Calendar.MONTH)) + 1;
        dataStringParsed = gcalendar.get(Calendar.DAY_OF_MONTH)+"/"+ month +"/"+ gcalendar.get(Calendar.YEAR) +" - "+ gcalendar.get(Calendar.HOUR_OF_DAY) + ":" + gcalendar.get(Calendar.MINUTE) + ":" + seconds;

        name = database.getUserData(mailFromLogin).getName();
        lastName = database.getUserData(mailFromLogin).getSurename();
        room = database.getRoomsData(result.getContents().toString()).getNameRoom();
        edifice = database.getRoomsData(result.getContents().toString()).getEdifice();
        accesHour = dataStringParsed;
        email = database.getUserData(mailFromLogin).getEmail();
        color = "#413e4f";

        Historical historical = new Historical("",name,lastName,room,edifice,accesHour,email,color);
        database.saveHistorial(historical);
    }

    public void saveToDatabaseEdifice(IntentResult result) throws java.text.ParseException {
        String name, lastName, room, edifice, accesHour, email,color;
        int month;
        GregorianCalendar gcalendar = new GregorianCalendar();
        String dataStringParsed, seconds;
        if(gcalendar.get(Calendar.SECOND)<10){
            seconds = "0" + gcalendar.get(Calendar.SECOND);
        }else{
            seconds = String.valueOf(gcalendar.get(Calendar.SECOND));
        }
        month = ((int) gcalendar.get(Calendar.MONTH)) + 1;
        dataStringParsed = gcalendar.get(Calendar.DAY_OF_MONTH)+"/"+ month +"/"+ gcalendar.get(Calendar.YEAR) +"-"+ gcalendar.get(Calendar.HOUR_OF_DAY) + ":" + gcalendar.get(Calendar.MINUTE) + ":" + seconds;

        name = database.getUserData(mailFromLogin).getName();
        lastName = database.getUserData(mailFromLogin).getSurename();
        room = " ";
        edifice = result.getContents().toString();
        accesHour = dataStringParsed;
        email = database.getUserData(mailFromLogin).getEmail();
        color = "#413e4f";

        Historical historical = new Historical("",name,lastName,room,edifice,accesHour,email, color);
        database.saveHistorial(historical);
    }

    public void saveToDatabaseUnauthorizedAccess(){
        String name, lastName, accesHour, email, color;
        int month;
        GregorianCalendar gcalendar = new GregorianCalendar();
        String dataStringParsed, seconds;
        if(gcalendar.get(Calendar.SECOND)<10){
            seconds = "0" + gcalendar.get(Calendar.SECOND);
        }else{
            seconds = String.valueOf(gcalendar.get(Calendar.SECOND));
        }
        month = ((int) gcalendar.get(Calendar.MONTH)) + 1;
        dataStringParsed = gcalendar.get(Calendar.DAY_OF_MONTH)+"/"+ month +"/"+ gcalendar.get(Calendar.YEAR) +"-"+ gcalendar.get(Calendar.HOUR_OF_DAY) + ":" + gcalendar.get(Calendar.MINUTE) + ":" + seconds;

        name = database.getUserData(mailFromLogin).getName();
        lastName = database.getUserData(mailFromLogin).getSurename();
        accesHour = dataStringParsed;
        email = database.getUserData(mailFromLogin).getEmail();
        color = "#ed1010";

        Historical historical = new Historical("",name,lastName,"",getString(R.string.denied_access),accesHour,email, color);
        database.saveHistorial(historical);
    }


}
