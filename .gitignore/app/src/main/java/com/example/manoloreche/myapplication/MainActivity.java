package com.example.manoloreche.myapplication;


import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.manoloreche.myapplication.sql.DBHelper;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputEmail;
    private TextInputEditText inputPassword;
    private Button loginButton;
    private DBHelper dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase = new DBHelper(getApplicationContext());
        /*String name = dataBase.getEdificeData().getNameEdifice();
        String salaName = dataBase.getRoomsData().getMaterialName();
        String userName = dataBase.getUserData().getEmail();
        String materialName = dataBase.getMateialData().getName();
        String rolName = dataBase.getRolData().getRol();
        List usersList = dataBase.getAllUsers();
        String adminTest = "admin";
        String emailTest = "admin@admin.com";
        //Comparacion de strings --> String.valueOf(name).equals("Empresa")
        Log.d("id: "+usersList.size(), " ,material: "+name);*/

        inputEmail = (TextInputEditText) findViewById(R.id.email);
        inputPassword = (TextInputEditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFromSQL();
            }
        });

    }

    public void verifyFromSQL(){
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if(dataBase.checkUser(email,password)){
            if(dataBase.getUserData(email).getIsAdmin().equals("admin")){
                Intent adminIntent = new Intent(this,AdminViewInit.class);
                adminIntent.putExtra("neededMail",email);
                //Toast.makeText(MainActivity.this,"funciono como admin",Toast.LENGTH_LONG).show();
                startActivity(adminIntent);
            }else{
                Intent userIntent = new Intent(this,UserView.class);
                userIntent.putExtra("neededMail",email);
                //Toast.makeText(MainActivity.this,"funciono como worker",Toast.LENGTH_LONG).show();
                startActivity(userIntent);

            }
        }else{
            Toast.makeText(MainActivity.this,getString(R.string.error_valid_email_password),Toast.LENGTH_LONG).show();
        }
    }
}
