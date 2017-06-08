package Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

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


public class ObtenerDatosGrafica1 {
    public static float valorGrafica1;
    public static String enviarp="";
    private JSONObject valorJSON;


    private BarChart barra;
    private String tabla,campo,mensaje;
    private static String posicion;
    private Context context;
    private ArrayList<BarEntry> barEntries;
    private ArrayList<String> labels;


    public ObtenerDatosGrafica1(BarChart barra,String tabla,String posicion,String campo,String mensaje,Context context){
        this.barra = barra;
        this.tabla = tabla;
        this.posicion = posicion;
        this.campo = campo;
        this.mensaje = mensaje;
        this.context = context;

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Obteniendo datos del servidor...");
        dialog.setMax(100);
        dialog.setProgress(0);
        dialog.setCancelable(false);


        new AsynTask(context,dialog,this).execute();



    }
    //TAREA ASINCRONA PARA TRAER LOS DATOS DEL SERVIDOR
    static class AsynTask extends AsyncTask<Void,Void,Void>{
        private ProgressDialog dialog;
        private Context context;
        private ObtenerDatosGrafica1 obj;
        public AsynTask(Context context,ProgressDialog dialog,ObtenerDatosGrafica1 obj){
            this.dialog = dialog;
            this.context = context;
            this.obj = obj;
            Log.d("maicol","yea");
        }
        @Override
        public Void doInBackground(Void... params) {
            obj.obtenerValor();
            return null;
        }
        @Override
        public void onPreExecute(){
            Log.d("maicol","yea");
            dialog.show();
        }
        @Override
        public void onPostExecute(Void unused) {
            obj.llenarGrafica();
            enviarp = posicion;
            //Alertas obj1 = new Alertas(context, enviarp);
           // obj1.llamar(valorGrafica1 );
            dialog.dismiss();
        }

    }



    public void llenarGrafica(){

        barra.getXAxis().setDrawLabels(false);
        barra.getAxisRight().setDrawLabels(false);
        barra.setDescription(null);
        barra.getLegend().setEnabled(false);

        //LINEAS PARA QUITAR LAS LINEAS DE LA BARRA
        barra.getAxisRight().setDrawGridLines(false);
        barra.getAxisLeft().setDrawGridLines(false);
        barra.getXAxis().setDrawGridLines(false);


        //OBTENIENDO EL AXIS DE LA BARRA PARA PODER PONERLE QUE SEA DE 0 A 100
        YAxis axis = barra.getAxisLeft();
        //PONIENDO EL MINIMO COMO 0 DE LA BARRA
        axis.setAxisMinValue(0);
        //PONIENDO EL MAXIMO COMO 100 DE LA BARRA
        axis.setAxisMaxValue(100);
        llenarBarEntryData();
        llenandoLabels();

        //PREPARANDOS LOS VALORES EN UN BARDATASET
        BarDataSet dataSet = new BarDataSet(barEntries,"");
        dataSet.setHighlightEnabled(false);
        dataSet.setValueTextSize(20);
        //Agregando los mensaje y el dataset a barData
        BarData barData = new BarData(dataSet);
        //QUITANDO LA DESCRIPCION ALA GRAFICA
       // barra.setDescription("");
        //AGREGANDO UNA ANIMAZION ALA BARRA
        barra.animateY(3000);
        //AGREGANDO LOS VALORES ALA LISTA
        barra.setData(barData);

    }
    public void llenarBarEntryData(){
        //DECLARANDO LA LISTA QUE SEA UN UNICO VALOR QUE SE MOSTRAR EN LA LISTA
        barEntries = new ArrayList<>();
        //AGREGANDO EL VALOR ALA LISTA
        Log.d("mikol",""+valorGrafica1);
        barEntries.add(new BarEntry(0,valorGrafica1));
    }
    public void llenandoLabels(){
        //AGREGANDO UN MESAJE A UNA LISTA
        labels = new ArrayList<>();
        //AGREGANDO EL MENSAJE ALA LISTA LABELS
       // labels.add(mensaje);

    }

    public void obtenerValor(){
        //PREPARAMOS LA DIRECCION A DONDE SE VA A REALIZAR LA PETICION
      // String dir = "http://207.249.127.215:1026/v2/entities?q=position=='"+posicion+"'&type="+tabla;
        String dir = "http://tatallerarquitectura.com/fiware/actual/"+tabla+"/"+posicion;
        Log.d("maickol",dir);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conexion;


        try {
            //CRAMOS EL OBJETO DE URL PASANDOLE AL CONTRUCTOR LA  DIRECCION
            url = new URL(dir);
            //SE ABRE UNA CONEXION CON LA URL Y GUARDANDO LA CONEXION EN LA VARIABLE CONEXION
            conexion =(HttpURLConnection)url.openConnection();
            //LE INDICAMOS EL TUPO DE PETICION
            conexion.setRequestMethod("GET");
            //CONTECAMOS CON LA URL
            conexion.connect();
            //OBTENEMOS EL CODIGO DE RESPUESTRA
            int code = conexion.getResponseCode();
            //SI ES DEIFENTE EL CODIGO DE LA RESPUESTA A 200 ENTONCES MOSTRAMOS UN MENSAE DE ERROR Y PARAMOS LA EJECUCION CON RETURN
            if(code != HttpURLConnection.HTTP_OK){
                Toast.makeText(context,"Ocurrio un error "+code,Toast.LENGTH_LONG).show();
                return;
            }
            //CREAMOS UN BUFFER CON LOS DATOS OBTENIDOS
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";
            //GUARDAMOS LOS DATOS OBTENIDOS EN RESPONSE
            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            //PASAMOS LOS DATOS OBTENIDOS DE RESPONSE A JSON
            json = response.toString();
            //PARSEAMOS A JSON LA VARIABLE JSON CON LOS DATOS DEL SERVIDOR
            JSONArray jsonArray = null;
            //CREAMOS UN ARRAY CON TODOS LOS DATOS OBTENIDOS DEL SERVIDOR
            jsonArray = new JSONArray(json);
            Log.d("michi",""+jsonArray.length());
            if(!jsonArray.getJSONObject(0).getString("temperatura").equalsIgnoreCase("null") && jsonArray.length()>0){
                //OBTENEMOS EL ULTIMO VALOR DEL JSON QUE SERA EL QUE SE GRAFICA Y LO  GUSRAMOS EN valorJSON
                valorGrafica1 = Float.parseFloat("" + jsonArray.getJSONObject(0).getString("temperatura"));
            }else{
                valorGrafica1 = 0;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
