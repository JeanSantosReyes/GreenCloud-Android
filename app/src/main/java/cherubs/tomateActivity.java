package cherubs;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.mand.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;

import Model.getData;

public class tomateActivity extends AppCompatActivity{
    private getData data = new getData();

    private Toolbar toolbar;
    private String type;

    private BarChart barra;
    private LineChart lineChart;

    private TextView medicion;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_tomate);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barra = (BarChart) findViewById(R.id.temperatura);
        lineChart = (LineChart) findViewById(R.id.temperaturas);
        barra.setVisibility(View.INVISIBLE);
        lineChart.setVisibility(View.INVISIBLE);
        medicion = (TextView) findViewById(R.id.medicion);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            type= extras.getString("type");
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
    public void changeToTemperaturaCherubs(View v) throws JSONException {
        barra.setVisibility(View.VISIBLE);//hacemos visible la barra para graficar
        lineChart.setVisibility(View.VISIBLE);
        medicion.setText("Temperatura");//se le manda el titulo de la variable de medicion
        data.llenarGrafica1(barra, type, "temperatura", "");//llamada al metodo
        int[] a = new int[3];
        int[] b = new int[3];
        data.llenarGrafica2(lineChart, "temperatura", a, b);
    }


    public void changeToHumedadRelativaCherubs(View v) throws JSONException {
        barra.setVisibility(View.VISIBLE);//hacemos visible la barra para graficar
        lineChart.setVisibility(View.VISIBLE);
        medicion.setText("Humedad Relativa");//se le manda el titulo de la variable de medicion
        data.llenarGrafica1(barra, type, "humedadRelativa", "");//llamada al metodo
        int[] a = new int[3];
        int[] b = new int[3];
        data.llenarGrafica2(lineChart, "tempera-tura", a, b);
    }
    public void changeToHuedadSueloCherubs(View v) throws JSONException {
        barra.setVisibility(View.VISIBLE);//hacemos visible la barra para graficar
        lineChart.setVisibility(View.VISIBLE);
        medicion.setText("Humedad del suelo");//se le manda el titulo de la variable de medicion
        data.llenarGrafica1(barra, type, "humedadSuelo", "");//llamada al metodo
        int[] a = new int[3];
        int[] b = new int[3];
        data.llenarGrafica2(lineChart, "temperatura", a, b);
    }
}
