package com.example.mand.myapplication;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;

import BaseDeDatos.sqlite;
import Fragments.DialogFragmentGeneral;
import Fragments.DialogSaveVariable;

public class tomateActivity extends AppCompatActivity {

    private Toolbar mTool;

    private int version;

    private Toolbar toolbar;
    private String type,position,variable;

    private MyViewPager adapter;
    private ViewPager viewPager;

    private  DialogFragmentGeneral dfm;

    //IMAGEViEw
    private ImageView imgTmp, imgHumeR, imgHumeS;

    private FloatingActionButton floatingActionButton;
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_tomate);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        variable = "temperatura";

        version = Integer.parseInt(getString(R.string.version_db));

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatinSave);
        //AQUI LE DAMOS EL EVENTO AL BOTON FLOTANTE , Y CUANDO SE LE DA CLICK SE ABRE UN DIALOGO
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tomateActivity.this," ",Toast.LENGTH_LONG).show();
                //SE MANDA LLAMAR EL METODO showDialog(); QUE MUESTRA EL DIALOGO PARA PODER GUARDAR LA TEMPERATURA RELEVANTE
                showDialog();
            }
        });


        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            type= extras.getString("type");
            position = extras.getString("position");
        }

        //IMAGE
        imgTmp = (ImageView) findViewById(R.id.imgTmp);
        imgHumeR = (ImageView) findViewById(R.id.imgHumeR);
        imgHumeS = (ImageView) findViewById(R.id.imgHumeS);
        //IMAGE

        //VIEW PAGER
        viewPager = (ViewPager) findViewById(R.id.VPGrafica);
        adapter = new MyViewPager(getSupportFragmentManager(),position,type,"temperatura","");
        viewPager.setAdapter(adapter);



        //VIEW PAGER

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

    }
    public void showDialog(){
        sqlite bh = new sqlite(tomateActivity.this,"UltimaVariedad",null,version);

        SQLiteDatabase db = bh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM UltimaVariedad WHERE campo = '" + variable + "' AND tipo = '" + type + "' AND posicion = '" + position + "'", null);

        FragmentManager f = getSupportFragmentManager();
        DialogSaveVariable dsf = null;
        try{
            if(c.moveToFirst()){
                dsf = DialogSaveVariable.newInstance(position,type,variable,c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8));
            }else{
                Log.d("esp","No Se encontraron datos "+variable+" "+type+" "+position);
            }
        }finally {

        }



        dsf.show(f, "");
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_grafica,menu);
        toolbar.setTitle(type);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.MenuEstadisticas:
                FragmentManager fm = getSupportFragmentManager();
                dfm = DialogFragmentGeneral.newInstance("fragmentestadisticas");
                dfm.show(fm,"");
                break;
        }
        return true;
    }
    public String traerVariedad(){
        Dialog dialogView = dfm.getDialog();

        Spinner variedadesEstadistica = (Spinner) dialogView.findViewById(R.id.variedadesEstadistica);

        String variedad = variedadesEstadistica.getSelectedItem().toString().replace(" ", "");

        variedad = String.valueOf(variedad.charAt(0)).toLowerCase()+variedad.substring(1, variedad.length());

        return variedad;
    }
    //ESTE METODO TE MUESTRA LAS ESTADISTICAS POR UNA HORA DEPENDIENDO QUE VARIABLE ESTE SELECCIONADA EN EL DIALOGO
    public void estadisticaUnaHora(View view) throws JSONException {
        //Toast.makeText(tomateActivity.this, "Maickol Rodriguez cornejo", Toast.LENGTH_SHORT).show();

        String variedad = traerVariedad();
        Log.d("mmm",variedad);

        adapter.updateFragment(1, type, position, variedad, "Temperaturas", 1);
        dfm.dismiss();
    }
    //ESTE METODO TE MUESTRA LAS ESTADISTICAS POR UN DIA DEPENDIENDO QUE VARIABLE ESTE SELECCIONADA EN EL DIALOGO
    public void estadisticasUnDia(View view) throws JSONException{
        //Toast.makeText(tomateActivity.this,"Maickol rodriguez cornejo 2",Toast.LENGTH_LONG).show();

        String variedad = traerVariedad();

        adapter.updateFragment(1,type,position,variedad,"Temperaturas",2);
        dfm.dismiss();
    }
    //ESTE METODO TE MUESTRA LAS ESTADISTICAS POR UNA SEMANA QUE VARIABLE ESTE SELECCIONADA EN EL DIALOGO
    public void estadisticasUnaSemana(View view) throws JSONException {

        String variedad = traerVariedad();
        //EL PRIMER PARAMATRO QUE SE MANDA ES UN 1 {0:GRAFICA BARRA,1:GRAFICA LINEA} , el segundo paramtro es el tipo
        //{cherubs,suburts....} el 3er parametro es la posicion que se quiere monitorear y el 4to es la variedad
        adapter.updateFragment(1,type,position,variedad,"Temperaturas",3);
        dfm.dismiss();
    }
    //ESTE METODO TE MUESTRA LAS ESTADISTICAS POR UN MES DEPENDIENDO QUE VARIABLE ESTE SELECCIONADA EN EL DIALOGO
    public void estadisticasMes(View view) throws JSONException {
        adapter.updateFragment(1,type,position,"temperatura","Temperaturas",4);
        dfm.dismiss();
    }

    //Click para la temperatura
    public void changeToTemperaturaCherubs(View v) throws JSONException {
        variable = "temperatura";
        adapter.updateFragment(0,type,position,"temperatura","Temperatura actual",0);
        adapter.updateFragment(1,type,position,"temperatura","Temperaturas",0);
        imgTmp.setImageResource(R.drawable.temperaturasueloseleccionadoo);
        imgHumeR.setImageResource(R.drawable.humedadrelativa);
        imgHumeS.setImageResource(R.drawable.humedadsuelo);
        /*imgTmp.setFocusableInTouchMode(true);
        imgHumeS.setFocusableInTouchMode(false);
        imgHumeR.setFocusableInTouchMode(false);*/
    }

    //Click para la HumedadRelativa
    public void changeToHumedadRelativaCherubs(View v) throws JSONException {
        variable = "humedadRelativa";
        adapter.updateFragment(0,type,position,"humedadRelativa","Humedad relativa actual",0);
        adapter.updateFragment(1,type,position,"humedadRelativa","Humedades relativas",0);
        imgHumeR.setImageResource(R.drawable.humedadrelativaseleccionadoo);
        imgTmp.setImageResource(R.drawable.temperaturasuelo);
        imgHumeS.setImageResource(R.drawable.humedadsuelo);
        /*imgHumeR.setFocusableInTouchMode(true);
        imgHumeS.setFocusableInTouchMode(false);
        imgTmp.setFocusableInTouchMode(false);*/

    }
    //Click para la HumedadSuelo
    public void changeToHuedadSueloCherubs(View v) throws JSONException {
        variable = "humedadSuelo";
        adapter.updateFragment(0,type,position,"humedadSuelo","Humedad suelo actual",0);
        adapter.updateFragment(1,type,position,"humedadSuelo","Humedades suelo",0);
        imgHumeS.setImageResource(R.drawable.humedadsueloseleccionadoo);
        imgHumeR.setImageResource(R.drawable.humedadrelativa);
        imgTmp.setImageResource(R.drawable.temperaturasuelo);
        /*imgHumeS.setFocusableInTouchMode(true);
        imgTmp.setFocusableInTouchMode(false);
        imgHumeR.setFocusableInTouchMode(false);*/
    }

}