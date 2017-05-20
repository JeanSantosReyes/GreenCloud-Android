package Model;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

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

/**
 * Created by MAND on 19/05/2017.
 */
public class ObtenerDatosGrafica1 {
    private float valorGrafica1;

    private JSONObject valorJSON;

    private BarChart barra;
    private String tabla,posicion,campo,mensaje;
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

        this.obtenerValor();
        this.llenarGrafica();

    }
    public void llenarGrafica(){
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
        //Agregando los mensaje y el dataset a barData
        BarData barData = new BarData(labels,dataSet);
        //QUITANDO LA DESCRIPCION ALA GRAFICA
        barra.setDescription("");
        //AGREGANDO UNA ANIMAZION ALA BARRA
        barra.animateY(3000);
        //AGREGANDO LOS VALORES ALA LISTA
        barra.setData(barData);

    }
    public void llenarBarEntryData(){
        //DECLARANDO LA LISTA QUE SEA UN UNICO VALOR QUE SE MOSTRAR EN LA LISTA
       barEntries = new ArrayList<>();
        //AGREGANDO EL VALOR ALA LISTA
        barEntries.add(new BarEntry(valorGrafica1,0));
    }
    public void llenandoLabels(){
        //AGREGANDO UN MESAJE A UNA LISTA
        labels = new ArrayList<>();
        //AGREGANDO EL MENSAJE ALA LISTA LABELS
        labels.add(mensaje);
    }

    public void obtenerValor(){
        //PREPARAMOS LA DIRECCION A DONDE SE VA A REALIZAR LA PETICION
        String dir = "http://207.249.127.215:1026/v2/entities?q=position=='"+posicion+"'&type="+tabla;

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
            //OBTENEMOS EL ULTIMO VALOR DEL JSON QUE SERA EL QUE SE GRAFICA Y LO  GUSRAMOS EN valorJSON
            valorJSON = new JSONObject(jsonArray.getJSONObject(jsonArray.length()-1).getString(campo));
            Log.d("JSONIN",valorJSON.toString());
            valorGrafica1 = Float.parseFloat("" + valorJSON.getDouble("value"));



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
