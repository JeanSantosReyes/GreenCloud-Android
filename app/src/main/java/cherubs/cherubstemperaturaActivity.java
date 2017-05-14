package cherubs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mand.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONException;

import java.util.ArrayList;

import Model.getData;

public class cherubstemperaturaActivity extends AppCompatActivity{
    private BarChart barra;
    private LineChart lineChart;

    private getData data = new getData();

    private Toolbar toolbar;

    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_cherubs_temperatura);

        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barra = (BarChart) findViewById(R.id.temperatura);

        lineChart = (LineChart) findViewById(R.id.temperaturas);

         inicializandoGraficarUno();
        try {
            iniciarlizarGraficaDos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Codigo de la grafica numero 1
    public void inicializandoGraficarUno(){
        data.llenarGrafica1(barra,"cherubs","temperatura");
    }
    //FIN DEL COEDIGO DE LA GRAFICA UNO

    //CODIGO DE LA GRAFICA DOS
    public void iniciarlizarGraficaDos() throws JSONException {
        data.llenarGrafica2(lineChart,"temperatura");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        toolbar.setTitle("Temperaturas");
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
