package com.example.mand.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import BaseDeDatos.FuncionesDB;
import Model.Invernadero;


public class ConfiguracionInvernaderos extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView gridView;
    private GridAdapter adapter;
    private EditText txtCantidad;
    private Button btng;
    private int versionDB;
    private ArrayList<Invernadero> arrayList;
    private  FuncionesDB fdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_invernaderos);

        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        versionDB = Integer.parseInt(getText(R.string.version_db).toString());
        txtCantidad = (EditText) findViewById(R.id.txt);
        btng = (Button) findViewById(R.id.btn);

        int x = 0;


        fdb = new FuncionesDB(this,versionDB);

        arrayList = fdb.getInvernaderoByIdUser(fdb.getIdUser());

        gridView = (GridView) findViewById(R.id.am_gv_gridview);
        adapter = new GridAdapter(this, showListView(arrayList), 1);
        gridView.setAdapter(adapter);

        x =(arrayList.size()>0)?0:-1;

        eventoLista(x);

        btng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = llenar();
                eventoLista(x);
            }
        });



    }
    public ArrayList<String> showListView(ArrayList<Invernadero> lista){
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0;i<lista.size();i++){
            list.add(lista.get(i).getNombre());
        }
        return list;
    }

    public void eventoLista(int x){
        if (x == 0) {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        /*LayoutInflater layoutInflater = getLayoutInflater();
                        View dialogLayout = layoutInflater.inflate(R.layout.alert_invernaderos, null);

                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionInvernaderos.this);
                        builder.setView(dialogLayout);
                        builder.show();*/
                    Intent intent = new Intent(ConfiguracionInvernaderos.this, ConfiguracionSectoresXinvernaderos.class);
                    intent.putExtra("idinvernadero", arrayList.get(position).getId_invernadero());
                    startActivity(intent);

                    //https://www.youtube.com/watch?v=DOXBg1HwXcI
                    //https://www.youtube.com/watch?v=BPoNXAV1ghQ
                    //https://www.youtube.com/watch?v=SiJvwBkI2Lg

                }
            });
        }
    }
    public int llenar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionInvernaderos.this);
        int RSP = -1;
        if(txtCantidad.getText().toString().length() <= 0){
            builder.setMessage("Introduzca la Cantidad de Invernaderos")
                    .setTitle("ERROR");
            builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
            RSP = 1;

        }else{
            String xy = txtCantidad.getText().toString();
            if(xy.contains(".")){
                builder.setMessage("Introce un Número Válido")
                        .setTitle("ERROR");
                builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }else {
                int xx = Integer.parseInt(xy);
                 ArrayList<String> array = new ArrayList<>();
                for (int x = 0; x < xx; x++) {
                    int num = x + 1;
                    array.add("Invernadero: " + num);
                }
                FuncionesDB fdb = new FuncionesDB(this,versionDB);
                fdb.guardarInvernadero(array);
                array = fdb.getInvernaderosById();
                gridView = (GridView) findViewById(R.id.am_gv_gridview);
                adapter = new GridAdapter(this, array, 1);
                gridView.setAdapter(adapter);
                arrayList = fdb.getInvernaderoByIdUser(fdb.getIdUser());
                RSP = 0;
            }

        }
        return RSP;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle("Configuracón de Invernaderos");
        getMenuInflater().inflate(R.menu.menu_invernaderos,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.deleteInvernaderos:
                finish();
                fdb.deleteInvernaderos(false);
                break;
        }
        return true;
    }
}

