package Model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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
    private float valorGrafica1;

    private JSONObject valorJSON;

    private PieChart barra;
    private String tabla,posicion,campo,mensaje;
    private Context context;
    private ArrayList<PieEntry> barEntries;
    private ArrayList<String> labels;

    public ObtenerDatosGrafica1(PieChart barra,String tabla,String posicion,String campo,String mensaje,Context context){
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
        try {


        llenarBarEntryData();
        llenandoLabels();


        PieDataSet dataSet = new PieDataSet(barEntries,"");


        PieData pieData = new PieData(dataSet);


        barra.setData(pieData);


        barra.setRotationEnabled(false);



        barra.setMaxAngle((valorGrafica1 * 180) / 100);

        barra.setRotationAngle(180f);

        barra.setCenterTextOffset(0, -20);

        barra.setCenterText(mensaje + " Actual");

       barra.setEntryLabelTextSize(12f);

        barra.getDescription().setText("");

        barra.getDescription().setTextSize(20);

        barra.setTransparentCircleColor(Color.WHITE);

        barra.setTransparentCircleAlpha(110);

        barra.setHoleRadius(58f);

        DisplayMetrics display = context.getResources().getDisplayMetrics();

        FrameLayout.LayoutParams _rootLayoutParams = new FrameLayout.LayoutParams(display.widthPixels, display.heightPixels);

        _rootLayoutParams.setMargins(0, 0, 0, 0);
        barra.setLayoutParams(_rootLayoutParams);

        barra.setTransparentCircleRadius(61f);

        barra.animateY(3000, Easing.EasingOption.EaseInOutQuad);

        barra.setCenterTextOffset(0, -20);
        }catch (Exception e){
            Toast.makeText(context, "NO internet"+e, Toast.LENGTH_SHORT).show();
        }
    }

    public void llenarBarEntryData(){
        //DECLARANDO LA LISTA QUE SEA UN UNICO VALOR QUE SE MOSTRAR EN LA LISTA
       barEntries = new ArrayList<>();
        //AGREGANDO EL VALOR ALA LISTA
        barEntries.add(new PieEntry(valorGrafica1,0));
        Log.d("valorGraficaUno",""+valorGrafica1);

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
