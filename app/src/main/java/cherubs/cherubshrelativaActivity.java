package cherubs;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.example.mand.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;


import Model.getData;

public class cherubshrelativaActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private BarChart barra;
    private LineChart lineChart;

    private getData data = new getData();

    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_cherubshrelativa);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barra = (BarChart) findViewById(R.id.humerdadRelativa);

        lineChart = (LineChart) findViewById(R.id.humerdadRelativas);

        inicializandoGraficarUno();
        try {
            iniciarlizarGraficaDos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Codigo de la grafica numero 1
    public void inicializandoGraficarUno(){
        data.llenarGrafica1(barra,"cherubs","humedadRelativa");
    }
    //FIN DEL COEDIGO DE LA GRAFICA UNO


    //CODIGO DE LA GRAFICA DOS
    public void iniciarlizarGraficaDos() throws JSONException {
        data.llenarGrafica2(lineChart,"humedadRelativa");
    }
    public boolean onCreateOptionsMenu(Menu menu){
        toolbar.setTitle("Humedad Relativa");
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
