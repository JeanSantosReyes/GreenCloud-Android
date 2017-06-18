package com.example.mand.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.sqlite;
import ReciclerMinimosMaximos.MinimosMaximos;
import ReciclerMinimosMaximos.adaptadorMinimosMaximos;

public class DetallesConfiguracion extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rvMinimosMaximos;
    private adaptadorMinimosMaximos adaptador;
    private int versionBD;
    private List<MinimosMaximos> minimosMaximos = new ArrayList<>();
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.detalle_configuracion);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        rvMinimosMaximos = (RecyclerView) findViewById(R.id.rvMinimosMaximos);

        versionBD = Integer.parseInt(getString(R.string.version_db));

        LinearLayoutManager lim = new LinearLayoutManager(DetallesConfiguracion.this);
        lim.setOrientation(LinearLayoutManager.VERTICAL);
        rvMinimosMaximos.setLayoutManager(lim);
        inicializarDatos();
        inicializarAdapter();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Arreglo para sacar todas las variedades de la base de datos
    String[] variedes = {"cherubs","eclipses","glorys","jubilees","sunburts"};
    //CON ESTE METODO SE INICIALIZAN LOS DATOS QUE SE MOSTRAN EN EL RECYCLER
    public void inicializarDatos(){
        sqlite bh = new sqlite(DetallesConfiguracion.this,"MMTable",null,versionBD);
        //HACEMOS UN CICLO PARA RECORRER TODAS LAS VARIEDADES
        for(String variedad : variedes){
            SQLiteDatabase db  = bh.getReadableDatabase();
            //CON ESTA CONSULTA SELECCIONAMOS CADA UNA DE LAS VARIEDADES QUE ESTAN EN EN CICLO
            Cursor c = db.rawQuery("SELECT * FROM MMTable WHERE variedad = '"+variedad+"'",null);
            String var ,uni,min,max;
            if(c.getCount()>0){
                try {
                    if(c.moveToFirst()){
                        do{
                            //DECLARAMOS UN OBJETO DE TIPO MINIMOSMAXIMOS PARA PODER LLENARLO A MUESTRA CONVENIENCIA
                            MinimosMaximos mm = new MinimosMaximos();
                            //SACAMOS LOS PRIMEROS REGISTROS DE LA BASE DE DATOS DEL TIPO SELECCIONADO CON EL CICLO FOR
                            var = c.getString(3);
                            uni = c.getString(4);
                            min = c.getString(2);
                            max = c.getString(1);

                            //SOLO UNA VEZ PONEMOS EL NOMBRE DE LA VARIEDAD
                            mm.setVariedad(var);

                            //MANDAMOS 4 PARAMETROS A ESTE METODO , EL PRIMERO ES LA UNIDAD {TEMPERATURA,HUMEDAD RELATIVA,SUELO} UNA DE
                            //ESTES TRES ES LA UNIDAD, Y LE MANDAMOS SU VALOR MAXIMO Y MINIMO , Y POR ULTIMO LE MANDAMOS EL OBJETO
                            //MM DE TIPO MINIMOMAXIMOS , Y ASI LLENARLO ALA CONVENIENCIA
                            mm = agregarMinimosMaximos(uni, min, max, mm);
                            //CON ESTE CICLO COMPROBAOS SI TIENE UN SIGUIENTE REGISTRO
                            if(c.moveToNext()){
                                //SI EXISTE UN REGISTRO SIGUIENTE HACEMOS LOS MISMO PASOS DEL ANTERIOR
                                uni = c.getString(4);
                                min = c.getString(2);
                                max = c.getString(1);

                                mm = agregarMinimosMaximos(uni, min, max, mm);
                            }
                            if(c.moveToNext()){
                                //SI EXISTE UN REGISTRO SIGUIENTE HACEMOS LOS MISMO PASOS DEL ANTERIOR
                                uni = c.getString(4);
                                min = c.getString(2);
                                max = c.getString(1);

                                mm = agregarMinimosMaximos(uni, min, max, mm);
                            }
                            minimosMaximos.add(mm);
                        }while(c.moveToNext());
                    }
                }finally {

                }
            }else{
                //Toast.makeText(DetallesConfiguracion.this,"No hay registro para mostrar",Toast.LENGTH_LONG).show();
            }
        }

    }
    //METODO PARA GUARDAR A NUESTRA CONVENIENCIA LOS REGISTROS DE LA BASE DE DATOS
    public MinimosMaximos agregarMinimosMaximos(String unidad,String min,String max,MinimosMaximos mm){
        switch (unidad) {
            case "Temperatura":
                mm.setTmaxima("Maxima "+max);
                mm.setTminima("Minima "+min);
                break;
            case "Humedad suelo":
                mm.setHSmaxima("Maxima "+max);
                mm.setHSminima("Minima "+min);
                break;
            case "Humedad relativa":
                mm.setHRmaxima("Maxima "+max);
                mm.setHRminima("Minima "+min);
                break;
        }
        return mm;
    }
    public void inicializarAdapter(){
        adaptador = new adaptadorMinimosMaximos(minimosMaximos);
        rvMinimosMaximos.setAdapter(adaptador);
    }
    public boolean onCreateOptionsMenu(Menu menu){
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
}
