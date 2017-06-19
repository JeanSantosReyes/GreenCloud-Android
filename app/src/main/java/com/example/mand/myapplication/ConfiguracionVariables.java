package com.example.mand.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import BaseDeDatos.sqlite;

/**
 * Created by MAND on 05/06/2017.
 */
public class ConfiguracionVariables extends AppCompatActivity {
    private boolean TempSet;
    private Toolbar toolbar;
    private EditText maxima,minima;
    private Spinner spinner,spinner_unidad;
    private TextInputLayout maximaTIL,minimaTIL;
    private int version;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.layout_configuracion_variables);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);

        version = Integer.parseInt(getString(R.string.version_db));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        maxima = (EditText) findViewById(R.id.tmpMaxima);
        minima = (EditText) findViewById(R.id.tmpMinima);
        spinner = (Spinner) findViewById(R.id.spinnerVariedades);
        spinner_unidad = (Spinner) findViewById(R.id.spinnerUnidad);
        maximaTIL = (TextInputLayout) findViewById(R.id.maximaTIL);
        minimaTIL = (TextInputLayout) findViewById(R.id.minimaTIL);

        spinner.setOnItemSelectedListener(new oyenteSpinner());
        spinner_unidad.setOnItemSelectedListener(new oyenteSpinner());

    }

    public void deleteAllBSD(){
        sqlite sq = new sqlite(ConfiguracionVariables.this,"MMTable",null,version);
        SQLiteDatabase db = sq.getWritableDatabase();

        db.execSQL("DELETE FROM MMTable");
    }
    public class oyenteSpinner implements AdapterView.OnItemSelectedListener{


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          sqlite bh = new sqlite(ConfiguracionVariables.this,"MMTable",null,version);

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM MMTable WHERE variedad = '"+spinner.getSelectedItem().toString()+"' AND unidad = '"+spinner_unidad.getSelectedItem().toString()+"'",null);
            Cursor c = db.rawQuery("SELECT * FROM MMTable",null);
           if(c.moveToFirst()){
               do{
                   Log.d("base",c.getInt(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4));
               }while(c.moveToNext());
           }
            long i = cursor.getCount();
            if(i>0){
                try{
                    if(cursor.moveToFirst()){
                        TempSet = false;
                        maxima.setText(cursor.getString(1));
                        minima.setText(cursor.getString(2));
                    }
                }finally {

                }
            }else{
                TempSet = true;
                maxima.setText("");
                minima.setText("");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_configuracion_variables,menu);
        toolbar.setTitle("Configuracion");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.VisualizarVariables:
                Intent intent = new Intent(ConfiguracionVariables.this,DetallesConfiguracion.class);
                startActivity(intent);
                break;
            case R.id.eliminar:
                deleteAllBSD();
                break;
        }
        return true;
    }
    public void insert(View v){
      if(TempSet){
          insetarNuevo(v);
      }else{
          actualizar(v);
      }
    }
    public void insetarNuevo(View v){
       if(!comprobarMaxima() || !comprobarMinima()){
            return;
        }

        ContentValues values = new ContentValues();
        values.put("temperatura_maxima",maxima.getText().toString());
        values.put("temperatura_minima",minima.getText().toString());
        values.put("unidad",spinner_unidad.getSelectedItem().toString());
        values.put("variedad",spinner.getSelectedItem().toString());

        sqlite bh = new sqlite(ConfiguracionVariables.this,"MMTable",null,version);
        SQLiteDatabase db = bh.getWritableDatabase();

        long insert = db.insert("MMTable", null, values);

        if(insert>0){
            TempSet = false;
            Toast.makeText(ConfiguracionVariables.this,"Guardado con exito",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ConfiguracionVariables.this,"Ocurrio un error",Toast.LENGTH_SHORT).show();
        }
    }
    public void actualizar(View v){
        if(!comprobarMaxima() || !comprobarMinima()){
            return;
        }

        ContentValues values = new ContentValues();
        values.put("temperatura_maxima",maxima.getText().toString());
        values.put("temperatura_minima",minima.getText().toString());
        values.put("unidad",spinner_unidad.getSelectedItem().toString());
        values.put("variedad",spinner.getSelectedItem().toString());

        sqlite bh = new sqlite(ConfiguracionVariables.this,"MMTable",null,version);
        SQLiteDatabase db = bh.getWritableDatabase();
        long update = db.update("MMTable", values, "variedad='" + spinner.getSelectedItem().toString() + "' AND unidad = '"+spinner_unidad.getSelectedItem().toString()+"'", null);
        if(update>0){
            Toast.makeText(ConfiguracionVariables.this,"Actualizado con exito",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(ConfiguracionVariables.this,"Ocurrio un error",Toast.LENGTH_SHORT).show();
        }

    }
    public boolean comprobarMaxima(){

        if(maxima.getText().toString().isEmpty()){
            maximaTIL.setError("Campo vacio");
            return false;
        }else{
            maximaTIL.setErrorEnabled(false);
            return true;
        }
    }
    public boolean comprobarMinima() {
        if (minima.getText().toString().isEmpty()) {
            minimaTIL.setError("Campo vacio");
            return false;
        } else {
            minimaTIL.setErrorEnabled(false);
            return true;

        }
    }
}
