package cherubs;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mand.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Model.getData;

public class cherubsActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private PieChart dona;
    private LineChart lineChart;

    private ArrayList<Entry> temperaturaData;
    private ArrayList<String> temperaturaLABEL;
    private ArrayList<Integer> colores = new ArrayList<>();

    private PieData pieData;
    private PieDataSet pieDataSet;


    private getData data = new getData();

    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_cherubs);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);

        dona = (PieChart) findViewById(R.id.temperatura);
        lineChart = (LineChart) findViewById(R.id.temperaturas);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inicializandoGraficarUno();
        try {
            iniciarlizarGraficaDos();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //Codigo de la grafica numero 1
    public void inicializandoGraficarUno(){
        colores.add(Color.RED);
        colores.add(Color.GREEN);


        llenarTemperaturaData();
        llenarTemperaturaLabel();

        pieDataSet = new PieDataSet(temperaturaData,"");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setSliceSpace(5);
        pieDataSet.setColors(colores);

        pieData = new PieData(temperaturaLABEL,pieDataSet);

        dona.setData(pieData);
        dona.animateY(3000);
    }
    public void llenarTemperaturaData(){
        temperaturaData = new ArrayList<>();
        temperaturaData.add(new Entry(70,0));
        temperaturaData.add(new Entry(data.getTempActual(), 0));
    }
    public void llenarTemperaturaLabel(){
        temperaturaLABEL = new ArrayList<>();
        temperaturaLABEL.add("");
        temperaturaLABEL.add("Temperatura");
    }
    //FIN DEL COEDIGO DE LA GRAFICA UNO

    //CODIGO DE LA GRAFICA DOS
    public void iniciarlizarGraficaDos() throws JSONException {
        ArrayList<Entry> numeros = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        preparandoDatosGraficar(data.jsonObjects,numeros,labels);

        LineDataSet dataSet = new LineDataSet(numeros,"");




        LineData ldata = new LineData(labels,dataSet);
        lineChart.setData(ldata);
        lineChart.animateY(3000);


    }
    public void preparandoDatosGraficar(JSONObject[] jsonObjects,ArrayList<Entry> list,ArrayList<String> labels) throws JSONException {
        String[] data;
        for (int i = 1;i<jsonObjects.length;i++){

            JSONObject sant = jsonObjects[i-1];
            JSONObject fechaAnterior = new JSONObject(sant.getString("fecha"));
            String[] horaAnterior = fechaAnterior.getString("value").split(" ")[1].split(":");
            Log.d("puto",horaAnterior[0]+" "+horaAnterior[1]);

            JSONObject sact = jsonObjects[i];
            JSONObject fecha = new JSONObject(sact.getString("fecha"));
            String[] horaAtual = fecha.getString("value").split(" ")[1].split(":");


            JSONObject tmpAct = new JSONObject(sact.getString("temperatura"));
            JSONObject tmpAnt = new JSONObject(sant.getString("temperatura"));

            if(parseInt(horaAnterior[0])==parseInt(horaAtual[0])){
                Log.d("DIF",""+(parseInt(horaAtual[1])-parseInt(horaAnterior[1]))+" "+parseInt(horaAtual[1])+" "+parseInt(horaAnterior[1]));
                if(parseInt(horaAtual[1])-parseInt(horaAnterior[1])>=5){
                    labels.add((i==1)?horaAnterior[0]+":"+horaAnterior[1]:horaAtual[0]+":"+horaAtual[1]);
                    list.add(new Entry(parseInt(tmpAct.getString("value")), i - 1));
                }
            }else{
                labels.add(horaAnterior[0] + ":" + horaAnterior[1]);
                Log.d("maickol"," "+horaAnterior[0]+" "+horaAnterior[1]);
                list.add(new Entry(parseInt(tmpAnt.getString("value")),i-1));
            }

        }
    }
    public int parseInt(String c){ return Integer.parseInt(c);}
    public double parseFloat(String c){ return Double.parseDouble(c);}

    public boolean onCreateOptionsMenu(Menu menu){
        toolbar.setTitle("Cherubs");
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
