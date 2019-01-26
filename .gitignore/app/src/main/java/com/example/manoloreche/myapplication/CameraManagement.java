package com.example.manoloreche.myapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.manoloreche.myapplication.sql.DBHelper;
import java.io.File;

public class CameraManagement extends AppCompatActivity {

    private Button camreaButton;
    private ImageView image;
    private ImageView imageDB;
    private DBHelper database;
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_management);
        camreaButton = (Button)findViewById(R.id.buttonCamera);
        image = (ImageView)findViewById(R.id.imageFromCamera);


        camreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Log.e("ACTION: ",MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bmp = (Bitmap)data.getExtras().get("data");
        image.setImageBitmap(bmp);
        database = new DBHelper(getApplicationContext());

        image.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                //if (bmp.sameAs())
                /*try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse("file:///android_asset/" + database.getRoomsData("Sala 1").getQR()));
                    bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse("file:///android_asset/" + database.getRoomsData("Sala 2").getQR()));
                    bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse("file:///android_asset/" + database.getRoomsData("Sala 3").getQR()));
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                Log.e("el path es: ", String.valueOf(path));
                File file = new File(path, "Test1");
                Log.e("Archivo: ", String.valueOf(file));
                if (file.exists()){
                    Toast.makeText(getApplicationContext(),"la foto esta",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"la foto no esta",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
