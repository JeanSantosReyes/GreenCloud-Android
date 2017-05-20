package com.example.mand.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;

import java.util.Calendar;

import Model.getData;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle("Variedades");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                salir();
                break;
        }
        return true;
    }

    //METODO PARA CAMBIAR DE ACTIVIDAD
    public void cherubsActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "cherubs");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","cherubs");
        startActivity(intent);
    }

    //METODO PARA CAMBIAR ALA ACTIVIDAD DE ECLIPSES
    public void eclipsesActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "eclipses");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","eclipses");
        startActivity(intent);
    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE GLORYS
    public void glorysActivity(View v){
       /* Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "glorys");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","glorys");
        startActivity(intent);
    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE JUBIES
    public void jubilesActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type","jubilees"); //Cambiar a "jubilees", así cambió en la API
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","jubilees");
        startActivity(intent);

    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE SUNBURTS
    public void sunburtsActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type","sunburts");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","sunburts");
        startActivity(intent);
    }


<<<<<<< HEAD
    public static class tomateActivity extends AppCompatActivity{
        private getData data = new getData();
        private Toolbar mTool;

        private Toolbar toolbar;
        private String type,position;

        private BarChart barra;
        private LineChart lineChart;

        private TextView medicion;

        private DatePicker datePicker;
        private Calendar calendar;
        private int Currentyear, Currentmonth, Currentday;
        private Button BTNFecha;

        //IMAGEViEw
        private ImageView imgTmp, imgHumeR, imgHumeS;
        TabHost tabHost;
        LinearLayout linearLayout;


        public void onCreate(Bundle b) {
            super.onCreate(b);
            setContentView(R.layout.activity_tomate);
            toolbar = (Toolbar) findViewById(R.id.toolbarGen);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //IMAGE
            imgTmp = (ImageView) findViewById(R.id.imgTmp);
            imgHumeR = (ImageView) findViewById(R.id.imgHumeR);
            imgHumeS = (ImageView) findViewById(R.id.imgHumeS);
            //IMAGE

            //TABS
            tabHost = (TabHost) findViewById(R.id.tabHost);
            tabHost.setup();
            TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab1");
            tabSpec.setIndicator("Actual");
            tabSpec.setContent(R.id.tab1);
            tabHost.addTab(tabSpec);

            tabHost.setup();
            TabHost.TabSpec tabSpec2=tabHost.newTabSpec("tab2");
            tabSpec2.setIndicator("Histórica");
            tabSpec2.setContent(R.id.tab222);
            tabHost.addTab(tabSpec2);
            //TABS
            barra = (BarChart) findViewById(R.id.temperatura);
            lineChart = (LineChart) findViewById(R.id.temperaturas);
            barra.setVisibility(View.INVISIBLE);
            lineChart.setVisibility(View.INVISIBLE);
            medicion = (TextView) findViewById(R.id.medicion);
            //ESTA DE ABAJO
            //BTNFecha = (Button) findViewById(R.id.fecha);

            Bundle extras = getIntent().getExtras();
            if(extras!=null){
                type= extras.getString("type");
                position = extras.getString("position");
            }

           if(Build.VERSION.SDK_INT >= 21){
               Window window = this.getWindow();
               window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
           }


        }
        public boolean onCreateOptionsMenu(Menu menu){
            toolbar.setTitle(type);
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

        //Click para la temperatura
        public void changeToTemperaturaCherubs(View v) throws JSONException {
            //imgTmp.setBackground(getResources().getDrawable(R.drawable.temperaturasueloseleccionado));
            imgTmp.setImageResource(R.drawable.temperaturasueloseleccionado);
            imgHumeS.setImageResource(R.drawable.humedadsuelo);
            imgHumeR.setImageResource(R.drawable.humedadrelativa);

            barra.setVisibility(View.VISIBLE);//hacemos visible la barra para graficar
            lineChart.setVisibility(View.VISIBLE);
            medicion.setText("Temperatura");//se le manda el titulo de la variable de medicion
            data.llenarGrafica1(barra, type,position, "temperatura", "");//llamada al metodo
            data.llenarGrafica2(lineChart, "temperatura");
        }

        //Click para la HumedadRelativa
        public void changeToHumedadRelativaCherubs(View v) throws JSONException {

            imgTmp.setImageResource(R.drawable.temperaturasuelo);
            imgHumeS.setImageResource(R.drawable.humedadsuelo);
            imgHumeR.setImageResource(R.drawable.humedadrelativaseleccionado);

            barra.setVisibility(View.VISIBLE);//hacemos visible la barra para graficar
            lineChart.setVisibility(View.VISIBLE);
            medicion.setText("Humedad Relativa");//se le manda el titulo de la variable de medicion
            data.llenarGrafica1(barra, type, position,"humedadRelativa", "");//llamada al metodo
            data.llenarGrafica2(lineChart, "temperatura");
        }
        //Click para la HumedadSuelo
        public void changeToHuedadSueloCherubs(View v) throws JSONException {

            imgTmp.setImageResource(R.drawable.temperaturasuelo);
            imgHumeS.setImageResource(R.drawable.humedadsueloseleccionado);
            imgHumeR.setImageResource(R.drawable.humedadrelativa);

            barra.setVisibility(View.VISIBLE);//hacemos visible la barra para graficar
            lineChart.setVisibility(View.VISIBLE);
            medicion.setText("Humedad del suelo");//se le manda el titulo de la variable de medicion
            data.llenarGrafica1(barra, type, position,"humedadSuelo", "");//llamada al metodo
            data.llenarGrafica2(lineChart, "temperatura");
        }

    }

=======
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Nature Sweet Tomatoes, Variedades", Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyUp(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            salir();
        }
        return true;
    }
    public void salir(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Estas seguro que deseas salir?");
        dialog.setTitle("Alerta");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
            }
        });
        dialog.setNegativeButton("Cancelar",null);
        dialog.show();
    }
>>>>>>> 49cd8d9ed15fa63f7eb97e8ca3251d462eaf94f2
}
