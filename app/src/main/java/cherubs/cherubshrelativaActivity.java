package cherubs;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.example.mand.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Model.getData;

public class cherubshrelativaActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private BarChart barra;
    private LineChart lineChart;

    private ArrayList<BarEntry> temperaturaData;
    private ArrayList<String> temperaturaLABEL;
    private ArrayList<Integer> colores = new ArrayList<>();

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
        colores.add(Color.GREEN);


        llenarTemperaturaData();
        llenarTemperaturaLabel();

        BarDataSet dataSet = new BarDataSet(temperaturaData,"");


        BarData barData = new BarData(temperaturaLABEL,dataSet);


        barra.animateY(3000);

        barra.setData(barData);

    }
    public void llenarTemperaturaData(){
        temperaturaData = new ArrayList<>();
        float temperaturaActual = data.getHumeadadRelativaActual();
        temperaturaData.add(new BarEntry(temperaturaActual, 0));
    }
    public void llenarTemperaturaLabel(){
        temperaturaLABEL = new ArrayList<>();
        temperaturaLABEL.add("Humedad Relativa");
    }
    //FIN DEL COEDIGO DE LA GRAFICA UNO

    //CODIGO DE LA GRAFICA DOS
    public void iniciarlizarGraficaDos() throws JSONException {
        ArrayList<Entry> numeros = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        preparandoDatosGraficar(data.jsonObjectsHRelativa,numeros,labels);

        LineDataSet dataSet = new LineDataSet(numeros,"");




        LineData ldata = new LineData(labels,dataSet);
        lineChart.setData(ldata);
        lineChart.animateY(3000);


    }
    public void preparandoDatosGraficar(JSONObject[] jsonObjectsHRelativa,ArrayList<Entry> list,ArrayList<String> labels) throws JSONException {
        String[] data;
        int size = jsonObjectsHRelativa.length;
        for (int i = 1;i<size;i++){

            JSONObject sant = jsonObjectsHRelativa[i-1];
            JSONObject fechaAnterior = new JSONObject(sant.getString("fecha"));
            String[] horaAnterior = fechaAnterior.getString("value").split(" ")[1].split(":");
            Log.d("puto", horaAnterior[0] + " " + horaAnterior[1]);

            JSONObject sact = jsonObjectsHRelativa[i];
            JSONObject fecha = new JSONObject(sact.getString("fecha"));
            Log.d("hq",""+fecha.toString());
            String[] horaAtual = fecha.getString("value").split(" ")[1].split(":");


            JSONObject tmpAnt = new JSONObject(sant.getString("humedadRelativa"));

            if(parseInt(horaAnterior[0])==parseInt(horaAtual[0])){
                if(parseInt(horaAtual[1])-parseInt(horaAnterior[1])>=5){
                    if(i == 1){
                        labels.add(horaAnterior[0]+":"+horaAnterior[1]);
                    }else{
                        labels.add(horaAnterior[0]+":"+horaAnterior[1]);
                    }
                    list.add(new Entry(parseInt(tmpAnt.getString("value")), i - 1));
                }
            }else{
                labels.add(horaAnterior[0] + ":" + horaAnterior[1]);
                list.add(new Entry(parseInt(tmpAnt.getString("value")),i-1));
            }

        }
        JSONObject sact = jsonObjectsHRelativa[size-1];
        JSONObject fecha = new JSONObject(sact.getString("fecha"));
        String[] horaAtual = fecha.getString("value").split(" ")[1].split(":");
        labels.add(horaAtual[0]+":"+horaAtual[1]);

        JSONObject tmpAct = new JSONObject(sact.getString("humedadRelativa"));

        list.add(new Entry(parseInt(tmpAct.getString("value")), size));

    }
    public int parseInt(String c){ return Integer.parseInt(c);}
    public double parseFloat(String c){ return Double.parseDouble(c);}


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
