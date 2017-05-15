package com.example.mand.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import cherubs.cherubsActivity;
import eclipses.eclipsesActivity;
import glorys.glorysActivity;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle("Variedades");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }

    //METODO PARA CAMBIAR DE ACTIVIDAD
    public void cherubsActivity(View v){
        Intent intent = new Intent(this, cherubsActivity.class);
        startActivity(intent);
    }

    //METODO PARA CAMBIAR ALA ACTIVIDAD DE ECLIPSES
    public void eclipsesActivity(View v){
        Intent intent = new Intent(this,eclipsesActivity.class);
        startActivity(intent);
    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE GLORYS
    public void glorysActivity(View v){
        Intent intent = new Intent(this,glorysActivity.class);
        startActivity(intent);
    }
}
