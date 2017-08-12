package com.example.mand.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import BaseDeDatos.FuncionesDB;
import Model.Invernadero;
import Model.Sector;
import adaptadores.adaptadorSectores;
import adaptadores.adaptadorVariedades;
import adaptadores.oyenteRecycler;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerVariedades;
    private List<Invernadero> variedadList;
    ArrayList<Sector> listaSectores;
    private adaptadorVariedades adaptador;
    private adaptadorSectores adaptadorSec;
    private FuncionesDB fdb;
    private int versionDB;
    private Invernadero invernadero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        versionDB = Integer.parseInt(getString(R.string.version_db));

        fdb = new FuncionesDB(this,versionDB);


        //CODIGO DEL RECICLER
        recyclerVariedades = (RecyclerView) findViewById(R.id.reciclerviewVariedades);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerVariedades.setLayoutManager(llm);
        metodosAdapter(0);


        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.darkLight));
        }
    }
    //METODOS ADAPTER SE ENCARGA DE LLAMAR A TODOS LOS METODOS NECESARIOS PARA LLENAR EL RECYCLER VIEW Y DARLE SUS EVENTOS
    public void metodosAdapter(int opcion){
        llenarDatos(opcion);
        inicializarAdapter(opcion);
        oyenteRecy();
    }
    public void llenarDatos(int opcion){
       switch (opcion){
           case 0:
               variedadList = fdb.getInvernaderoByIdUser(fdb.getIdUser());
               break;
           case 1:
               listaSectores = fdb.getSectoresByInvernadero(invernadero.getId_invernadero());
               break;
       }
    }
    public void inicializarAdapter(int opcion){
        switch (opcion){
            case 0:
                adaptador = new adaptadorVariedades(variedadList,MainActivity.this);
                recyclerVariedades.setAdapter(adaptador);
                break;
            case 1:
                adaptadorSec = new adaptadorSectores(listaSectores,MainActivity.this);
                Log.d("impro",""+listaSectores.size());
                recyclerVariedades.setAdapter(adaptadorSec);
                break;
        }

    }
    public void oyenteRecy(){
        recyclerVariedades.addOnItemTouchListener(new oyenteRecycler(this, recyclerVariedades, new oyenteRecycler.OnItemClickListener() {

            @Override
            public void onItemClick(View v, int position) {
                invernadero = variedadList.get(position);
                metodosAdapter(1);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Toast.makeText(MainActivity.this, "largo", Toast.LENGTH_SHORT).show();
            }
        }));
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle("Variedades");
        if(Build.VERSION.SDK_INT>=17){
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorFondo));
        }
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
