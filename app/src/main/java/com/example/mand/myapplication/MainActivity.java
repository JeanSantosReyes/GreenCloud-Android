package com.example.mand.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerVariedades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //CODIGO DEL RECICLER
        recyclerVariedades = (RecyclerView) findViewById(R.id.reciclerviewVariedades);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerVariedades.setLayoutManager(llm);


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
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
    //METODO PARA CAMBIAR DE ACTIVIDAD
    public void cherubsActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "cherubs");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","cherubs");
        startActivity(intent);
    }

    //METODO PARA CAMBIAR ALA ACTIVIDAD DE ECLIPSES
    public void eclipsesActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "eclipses");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","eclipses");
        startActivity(intent);
    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE GLORYS
    public void glorysActivity(View v){
       /* Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "glorys");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","glorys");
        startActivity(intent);
    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE JUBIES
    public void jubilesActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type","jubilees"); //Cambiar a "jubilees", así cambió en la API
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","jubilees");
        startActivity(intent);

    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE SUNBURTS
    public void sunburtsActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type","sunburts");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","sunburts");
        startActivity(intent);
    }




}
