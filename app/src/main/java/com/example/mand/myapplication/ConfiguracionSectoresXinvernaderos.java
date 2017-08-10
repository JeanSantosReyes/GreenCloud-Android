package com.example.mand.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import BaseDeDatos.FuncionesDB;

public class ConfiguracionSectoresXinvernaderos extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTituloInvernadero;
    private Button btnGenerar;
    private GridView gridView;
    private GridAdapter adapter;
    private EditText txtCantidadSectores;
    Bundle datosIntent;
    private int idInvernadero;
    private FuncionesDB fdb;
    private int versionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_sectores_xinvernaderos);

        txtTituloInvernadero = (TextView) findViewById(R.id.tvInvernadero);
        btnGenerar = (Button) findViewById(R.id.btn);
        txtCantidadSectores = (EditText) findViewById(R.id.txt);


        datosIntent = getIntent().getExtras();
        idInvernadero = datosIntent.getInt("idinvernadero");
        txtTituloInvernadero.setText("Invernadero "+idInvernadero);

        versionDB = Integer.parseInt(getString(R.string.version_db));

        fdb = new FuncionesDB(this,versionDB);

        fdb.getSectoresByInvernadero(idInvernadero);

        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = generarSectores();
                if (x == 0) {
                    eventoLista();
                }

            }
        });

        // ToolBar
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ToolBar
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle("Sectores por Invernadero");
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
    public void eventoLista(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialogLayout = layoutInflater.inflate(R.layout.activity_cantidad_sectores, null);

                final CheckBox cbHR = (CheckBox) dialogLayout.findViewById(R.id.rdHumedadRelativa);
                final CheckBox cbHS = (CheckBox) dialogLayout.findViewById(R.id.rdHumedadSuelo);
                final CheckBox cbTM = (CheckBox) dialogLayout.findViewById(R.id.rdTemperatura);

                TextView txtTituloSector = (TextView) dialogLayout.findViewById(R.id.tituloSector);
                Button btnGuardar = (Button) dialogLayout.findViewById(R.id.btnGuardar);
                Button btnCancelar = (Button) dialogLayout.findViewById(R.id.btnCancelar);

                txtTituloSector.setText("Seleccione las Variables Ambientales a Monitorear para : " + adapter.getItem(position).toString());

                final AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionSectoresXinvernaderos.this);
                builder.setView(dialogLayout);
                builder.setCancelable(false);

                final AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

                // BOTONES
                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Hr = "", Hs = "", Tm = "";
                        if (cbHR.isChecked()) {
                            Hr = cbHR.getText().toString();
                        }
                        if (cbHS.isChecked()) {
                            Hs = cbHS.getText().toString();
                        }
                        if (cbTM.isChecked()) {
                            Tm = cbTM.getText().toString();
                        }

                        String sector = adapter.getItem(position).toString();
                        //sector = sector.substring(sector.length() -1,sector.length());

                        String invernadero = txtTituloInvernadero.getText().toString();

                        String cFinal = invernadero + " " + sector + " " + Hr + " " + Hs + " " + Tm;

                        Toast.makeText(getApplicationContext(), "Valor a Registrar:  " + cFinal, Toast.LENGTH_LONG).show();
                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


            }
        });

    }

    public int generarSectores(){

        AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionSectoresXinvernaderos.this);
        int RSP = -1;
        if(txtCantidadSectores.getText().toString().length() <= 0){
            builder.setMessage("Introduzca la Cantidad de Sectores")
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
            String xy = txtCantidadSectores.getText().toString();
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
                final ArrayList<String> arrayList = new ArrayList<>();
                for (int x = 0; x < xx; x++) {
                    int num = x + 1;
                    arrayList.add("Sector: " + num);
                }
                fdb.guardarSectores(arrayList,idInvernadero);
                gridView = (GridView) findViewById(R.id.am_gv_gridview);
                adapter = new GridAdapter(this, arrayList, 2);
                gridView.setAdapter(adapter);
                RSP = 0;
            }

        }
        return RSP;
    }
}
