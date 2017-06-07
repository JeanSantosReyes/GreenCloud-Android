package com.example.mand.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
    private Spinner spinner;
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
        maximaTIL = (TextInputLayout) findViewById(R.id.maximaTIL);
        minimaTIL = (TextInputLayout) findViewById(R.id.minimaTIL);

        spinner.setOnItemSelectedListener(new oyenteSpinner());

    }

    public class oyenteSpinner implements AdapterView.OnItemSelectedListener{


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          sqlite bh = new sqlite(ConfiguracionVariables.this,"MMTable",null,version);

            SQLiteDatabase db = bh.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM MMTable WHERE variedad = '"+spinner.getSelectedItem().toString()+"'",null);
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
    public boolean onCreateMenuOptions(Menu menu){
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
        values.put("variedad",spinner.getSelectedItem().toString());

        sqlite bh = new sqlite(ConfiguracionVariables.this,"MMTable",null,version);
        SQLiteDatabase db = bh.getWritableDatabase();

        long insert = db.insert("MMTable", null, values);

        if(insert>0){
            Snackbar.make(v,"Guardado con exito",Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(v,"Ocurrio un error",Snackbar.LENGTH_SHORT).show();
        }
    }
    public void actualizar(View v){
        if(!comprobarMaxima() || !comprobarMinima()){
            return;
        }

        ContentValues values = new ContentValues();
        values.put("temperatura_maxima",maxima.getText().toString());
        values.put("temperatura_minima",minima.getText().toString());
        values.put("variedad",spinner.getSelectedItem().toString());

        sqlite bh = new sqlite(ConfiguracionVariables.this,"MMTable",null,version);
        SQLiteDatabase db = bh.getWritableDatabase();
        long update = db.update("MMTable",values,"variedad='"+spinner.getSelectedItem().toString()+"'",null);
        if(update>0){
            Snackbar.make(v,"Actualizado con exito",Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(v,"Ocurrio un error",Snackbar.LENGTH_LONG).show();
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
