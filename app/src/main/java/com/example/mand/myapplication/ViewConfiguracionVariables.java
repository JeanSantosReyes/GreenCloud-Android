package com.example.mand.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ViewConfiguracionVariables extends AppCompatActivity{
    private Toolbar toolbar;
    private List<ProgramaResponse> listProgramas=new ArrayList<>();
    private ProgramasAdapter mAdapter;
    RecyclerView mRecycler;
    //Spinner spinner;
    ProgressDialog progeesDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_configuracion_variables);

        //spinner = (Spinner) findViewById(R.id.spinnerFiltro);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecycler = (RecyclerView) findViewById(R.id.lvLista);

        String consulta = "http://improntadsi.000webhostapp.com/WebServiceGreen/listarConfiguracionVariables.php";
        EnviarRecibirDatos(consulta);
        //
        progeesDialog =  new ProgressDialog(ViewConfiguracionVariables.this);
        progeesDialog.setMessage("Cargando datos...");
        progeesDialog.setCancelable(false);
        progeesDialog.show();
        mAdapter = new ProgramasAdapter(ViewConfiguracionVariables.this, listProgramas);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecycler.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent abreInicio = new Intent(getApplicationContext(),ConfiguracionDeVariables.class);
                startActivity(abreInicio);
                finish();
            }
        });



    }

    public void EnviarRecibirDatos(String URL) {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][", ",");
                if (response.length() > 0) {
                    try {

                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson", "" + ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    progeesDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Configuraciones No Existentes",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

    public void CargarListView(JSONArray ja) {

        //ArrayList<String> lista = new ArrayList<>();
        for (int i = 0; i < ja.length(); i += 5) {

            try {
                ProgramaResponse item=new ProgramaResponse();
                item.setIdRegistro(Integer.parseInt(ja.getString(i)));
                item.setTomate(ja.getString(i+1));
                item.setVariable(ja.getString(i + 2));
                item.setMaximo(ja.getString(i + 3));
                item.setMinimo(ja.getString(i + 4));
                listProgramas.add(item);
                //lista.add(ja.getString(i) + " " + ja.getString(i + 1) + " " + ja.getString(i + 2));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        mAdapter.notifyDataSetChanged();
        progeesDialog.dismiss();
        //spinner.setOnItemSelectedListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle("Lista de configuraciones");
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
