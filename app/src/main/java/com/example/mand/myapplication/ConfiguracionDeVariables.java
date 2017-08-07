package com.example.mand.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static com.example.mand.myapplication.R.id.toolbar;

public class ConfiguracionDeVariables extends AppCompatActivity {

    EditText txtMaximo, txtMinimo;
    Spinner spinnerTomate, spinnerVariable;
    Button btnGuardar, btnCancelar;
    String url = "";
    String parametros = "";
    ProgressDialog progeesDialog;
    private Toolbar toolbar;
    //
        String max = "", min = "", tomate = "", variable = "";
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_de_variables);

        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerTomate = (Spinner) findViewById(R.id.spinnerTomate);
        spinnerVariable = (Spinner) findViewById(R.id.spinnerVariable);
        txtMaximo = (EditText) findViewById(R.id.txtMax);
        txtMinimo = (EditText) findViewById(R.id.txtMin);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {

                    max = txtMaximo.getText().toString();
                    min = txtMinimo.getText().toString();
                    tomate = spinnerTomate.getSelectedItem().toString();
                    variable = spinnerVariable.getSelectedItem().toString();

                    if(max.isEmpty() || min.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "Ingrese los Valores", Toast.LENGTH_LONG).show();
                    }else{
                        int maxx, minn;
                        maxx = Integer.parseInt(max);
                        minn = Integer.parseInt(min);
                        if(minn > maxx){
                            Toast.makeText(getApplicationContext(), "El Máximo debe ser mayor que el Minimo", Toast.LENGTH_LONG).show();
                        }else {
                            progeesDialog = new ProgressDialog(ConfiguracionDeVariables.this);
                            progeesDialog.setMessage("Enviando datos, Espere Por Favor");
                            progeesDialog.setCancelable(false);
                            progeesDialog.show();

                            url = "http://improntadsi.000webhostapp.com/WebServiceGreen/registrarVariables.php";
                            parametros = "accion=" + "1" + "&user=" + "2" + "&tomate=" + tomate + "&variable=" + variable + "&max=" + max + "&min=" + min;
                            new SolicitaDados().execute(url);
                        }

                    }

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionDeVariables.this);
                    builder.setMessage("Verifica la Conexión a Internet")
                            .setTitle("ERROR");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ConfiguracionDeVariables.this, ViewConfiguracionVariables.class);
                startActivity(go);
                finish();
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle("Configuraciones");
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

    private class SolicitaDados extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return Conexion.postDados(urls[0], parametros);
        }
        protected void onPostExecute(String resultado) {

            if(resultado.contains("existe")){
                progeesDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionDeVariables.this);
                builder.setMessage("Configuración Existente")
                        .setTitle("Alerta");
                builder.setPositiveButton("2.-Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
                builder.setNegativeButton("1.-Modificar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        url = "http://improntadsi.000webhostapp.com/WebServiceGreen/registrarVariables.php";
                        parametros = "accion=" + "2" + "&user=" + "2" + "&tomate=" + tomate + "&variable=" + variable + "&max=" + max + "&min=" + min;
                        new actualizarDatos().execute(url);

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.show();

            }else if (resultado.contains("1")){
                progeesDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionDeVariables.this);
                builder.setMessage("Registro Exitoso")
                        .setTitle("Éxito");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        txtMaximo.setText("");
                        txtMinimo.setText("");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }else {
                progeesDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionDeVariables.this);
                builder.setMessage("Datos no Insertados papú")
                        .setTitle("ERROR");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }


        }
    }

    private class actualizarDatos extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return Conexion.postDados(urls[0], parametros);
        }
        protected void onPostExecute(String resultado) {

            if(resultado.contains("OK")){
                progeesDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionDeVariables.this);
                builder.setMessage("Actualizado Exitoso")
                        .setTitle("Éxito");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        txtMaximo.setText("");
                        txtMinimo.setText("");
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }else {
                progeesDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionDeVariables.this);
                builder.setMessage("Datos no Insertados papú")
                        .setTitle("ERROR");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
            }

        }
    }
}
