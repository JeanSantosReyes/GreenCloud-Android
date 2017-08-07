package com.example.mand.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ModificarConfiguracionVariable extends AppCompatActivity {

    EditText max, min;
    TextView type, variable, idRegistro;
    Bundle datosIntent;
    Button btnModificar, btnCancelar;

    ProgressDialog progeesDialog;
    String parametros = "";
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_configuracion_variable);

        max = (EditText) findViewById(R.id.txtMax);
        min = (EditText) findViewById(R.id.txtMin);
        type = (TextView) findViewById(R.id.typetomate);
        variable = (TextView) findViewById(R.id.variable);
        idRegistro = (TextView) findViewById(R.id.idRegistro);
        btnModificar = (Button) findViewById(R.id.btnGuardar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);

        datosIntent = getIntent().getExtras();

        String tomate, vari, maximo, minimo, id;

        id = datosIntent.getString("idRegistro");
        idRegistro.setText(id);

        tomate = datosIntent.getString("tomate");
        type.setText(tomate);

        vari = datosIntent.getString("variable");
        variable.setText(vari);

        maximo = datosIntent.getString("max");
        max.setText(maximo);

        minimo = datosIntent.getString("min");
        min.setText(minimo);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idR = idRegistro.getText().toString();
                String maxi = max.getText().toString();
                String mini = min.getText().toString();

                if(maxi.isEmpty() || mini.isEmpty()){
                    Toast.makeText(getApplicationContext(), "CAMPOS VACIOS", Toast.LENGTH_LONG).show();
                }
                else{

                    progeesDialog =  new ProgressDialog(ModificarConfiguracionVariable.this);
                    progeesDialog.setMessage("Modificando Datos, Espere...");
                    progeesDialog.setCancelable(false);
                    progeesDialog.show();

                    url = "http://improntadsi.000webhostapp.com/WebServiceGreen/registrarVariables.php";
                    parametros = "accion=" + "3" + "&id=" + idR + "&max=" + maxi + "&min=" + mini;
                    new ModificarConfiguracionVariable.ModificarVariable().execute(url);
                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ModificarConfiguracionVariable.this, ViewConfiguracionVariables.class);
                startActivity(go);
                finish();
            }
        });


    }


    private class ModificarVariable extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return Conexion.postDados(urls[0], parametros);
        }
        protected void onPostExecute(String resultado) {
            if (resultado.contains("OK")){
                progeesDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(ModificarConfiguracionVariable.this);
                builder.setMessage("Registro Modificado")
                        .setTitle("Éxito");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int iddd) {
                        dialog.dismiss();
                        Intent abreInicio = new Intent(ModificarConfiguracionVariable.this,ViewConfiguracionVariables.class);
                        startActivity(abreInicio);
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }else {
                progeesDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificarConfiguracionVariable.this);
                builder.setMessage("Datos No Modificados")
                        .setTitle("ERROR");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();
                //Toast.makeText(getApplicationContext(),"Usuario o Contraseña incorrectos",Toast.LENGTH_LONG).show();
                //progeesDialog.dismiss();
            }
        }
    }


}// FIN
