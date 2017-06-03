package com.example.mand.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import org.json.JSONException;

import Fragments.DialogFragmentGeneral;

public class tomateActivity extends AppCompatActivity {

    private Toolbar mTool;

    private Toolbar toolbar;
    private String type,position;

    private MyViewPager adapter;
    private ViewPager viewPager;

    //IMAGEViEw
    private ImageView imgTmp, imgHumeR, imgHumeS;


    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_tomate);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                DialogFragmentGeneral dfm = DialogFragmentGeneral.newInstance("fragmentestadisticas");
                dfm.show(fm,"");
                break;
        }
        return true;
    }

    //Click para la temperatura
    public void changeToTemperaturaCherubs(View v) throws JSONException {
        adapter.updateFragment(0,type,position,"temperatura","Temperatura actual");
        adapter.updateFragment(1,type,position,"temperatura","Temperaturas");
        imgTmp.setImageResource(R.drawable.temperaturasueloseleccionado);
        imgHumeR.setImageResource(R.drawable.humedadrelativa);
        imgHumeS.setImageResource(R.drawable.humedadsuelo);
        /*imgTmp.setFocusableInTouchMode(true);
        imgHumeS.setFocusableInTouchMode(false);
        imgHumeR.setFocusableInTouchMode(false);*/
    }

    //Click para la HumedadRelativa
    public void changeToHumedadRelativaCherubs(View v) throws JSONException {
        adapter.updateFragment(0,type,position,"humedadRelativa","Humedad relativa actual");
        adapter.updateFragment(1,type,position,"humedadRelativa","Humedades relativas");
        imgHumeR.setImageResource(R.drawable.humedadrelativaseleccionado);
        imgTmp.setImageResource(R.drawable.temperaturasuelo);
        imgHumeS.setImageResource(R.drawable.humedadsuelo);
        /*imgHumeR.setFocusableInTouchMode(true);
        imgHumeS.setFocusableInTouchMode(false);
        imgTmp.setFocusableInTouchMode(false);*/

    }
    //Click para la HumedadSuelo
    public void changeToHuedadSueloCherubs(View v) throws JSONException {
        adapter.updateFragment(0,type,position,"humedadSuelo","Humedad suelo actual");
        adapter.updateFragment(1,type,position,"humedadSuelo","Humedades suelo");
        imgHumeS.setImageResource(R.drawable.humedadsueloseleccionado);
        imgHumeR.setImageResource(R.drawable.humedadrelativa);
        imgTmp.setImageResource(R.drawable.temperaturasuelo);
        /*imgHumeS.setFocusableInTouchMode(true);
        imgTmp.setFocusableInTouchMode(false);
        imgHumeR.setFocusableInTouchMode(false);*/
    }

}