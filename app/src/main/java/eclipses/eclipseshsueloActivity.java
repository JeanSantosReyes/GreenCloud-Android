package eclipses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mand.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;

import Model.getData;



public class eclipseshsueloActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private BarChart barra;
    private LineChart lineChart;

    private getData data = new getData();

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_eclipseshsuelo);

        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        barra = (BarChart) findViewById(R.id.humerdadSueloEclipses);

        lineChart = (LineChart) findViewById(R.id.humerdadSuelosEclipses);

        inicializandoGraficarUno();
        try {
            iniciarlizarGraficaDos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Codigo de la grafica numero 1
    public void inicializandoGraficarUno(){
        data.llenarGrafica1(barra,"eclipses","humedadSuelo");
    }

    //FIN DEL COEDIGO DE LA GRAFICA UNO

    //CODIGO DE LA GRAFICA DOS
    public void iniciarlizarGraficaDos() throws JSONException {
        data.llenarGrafica2(lineChart,"humedadSuelo");

    }

    public boolean onCreateOptionsMenu(Menu menu){
        toolbar.setTitle("Humedad suelo");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
