package com.example.mand.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsuario;
    TextInputEditText txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPass = (TextInputEditText) findViewById(R.id.txtPass);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = "nature";
                String pass = "nature";
                String usuario = txtUsuario.getText().toString();
                String contrasena = txtPass.getText().toString();

                Intent go2 = new Intent(login.this, navigation.class);
                startActivity(go2);
                finish();
                if (usuario.length() == 0 || contrasena.length() == 0) {
                    Toast.makeText(login.this, "Ingrese los datos", Toast.LENGTH_SHORT).show();
                } else {

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
        salir();
    }
    public boolean onKeyUp(int keyCode,KeyEvent keyEvent){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            salir();
        }

        return true;
    }
    public boolean onOptionItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                salir();
                break;
        }
        return true;
    }
    public void salir(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Estas seguro que deseas salir?");
        alert.setTitle("Alerta");
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                login.this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Cancelar", null);
        alert.show();
    }


}
