package com.example.mand.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsuario, txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPass = (EditText) findViewById(R.id.txtPass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = "nature";
                String pass = "nature";
                String usuario = txtUsuario.getText().toString();
                String contrasena = txtPass.getText().toString();


                if(usuario.length() == 0 || contrasena.length() == 0){
                    Toast.makeText(login.this, "Ingrese los datos", Toast.LENGTH_SHORT).show();
                }else {

                    if (usuario.equals(user) && contrasena.equals(pass)) {
                        Intent go = new Intent(login.this, navigation.class);
                        startActivity(go);
                    } else {
                        Toast.makeText(login.this, "Usuario o Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Nature Sweet Tomatoes", Toast.LENGTH_SHORT).show();
    }

}
