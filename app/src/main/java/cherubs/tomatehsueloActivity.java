package cherubs;

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

public class tomatehsueloActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private BarChart barra;
    private LineChart lineChart;

    private getData data = new getData();

    private String type;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.ativity_tomatehsuelo);

        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            type = extras.getString("type");
        }

        barra = (BarChart) findViewById(R.id.humerdadSuelo);

        lineChart = (LineChart) findViewById(R.id.humerdadSuelos);

        inicializandoGraficarUno();
        try {
            iniciarlizarGraficaDos();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Codigo de la grafica numero 1
    public void inicializandoGraficarUno(){
        data.llenarGrafica1(barra,type,"humedadSuelo","Humedad Suelo");
    }

    //FIN DEL COEDIGO DE LA GRAFICA UNO

    //CODIGO DE LA GRAFICA DOS
    public void iniciarlizarGraficaDos() throws JSONException {

        int[] a,b;
        a = new int[3];
        b = new int[3];
        data.llenarGrafica2(lineChart,"humedadSuelo",a,b);


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
