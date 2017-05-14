package Model;


import android.graphics.Color;
import android.os.StrictMode;
import android.util.Log;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

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

public class getData{
    //Variables de la primera grafica
    private float valorGrafica1;

    //Variables de la segunda grafica
    private ArrayList<BarEntry> barEntry;
    private ArrayList<String> StringLABEL;

    //Colores de las graficas
    private ArrayList<Integer> colores = new ArrayList<>();

    //Variables para tratar los datos de tipo json que consumimos de fiware
    private JSONObject valorJSON;
    public JSONObject[] jsonObjects;

    //GRAFICA UNO

    /*Metodo que llena la graficar numero uno , Se le manda una variable de tpo BarChart que es la grafica
      y se le manda una variable type de tipo String para saber a que entidad se van a hacer las consultas en el servidor,
      y se le manda una tercera variable de tipo String llamada field para sacar el campo del json que quieramos mostrar en la grafica*/
    public void llenarGrafica1(BarChart barra,String type,String field){
        //Metodo para obtener los datos del servidor dependiendo el type y field que le mandemos
        ObtenerDatos(type, field);
        //Se llena la lista de colores
        colores.add(Color.RED);

        //Se llena la lista de barEntry con el dato actual que se mostrar en la grafica 1
        llenarBarEntryData();
        //Se llena la lista de labels que se van a mostrar en la grafica 1
        llenarLabelsLabel();


        BarDataSet dataSet = new BarDataSet(barEntry,"");
        dataSet.setColors(colores);
        BarData barData = new BarData(StringLABEL,dataSet);
        barra.animateY(3000);
        barra.setData(barData);

    }
    public void llenarBarEntryData(){
        barEntry = new ArrayList<>();
        barEntry.add(new BarEntry(getValorGrafica1(),0));
    }
    public void llenarLabelsLabel(){
        StringLABEL = new ArrayList<>();
        StringLABEL.add("Humedad Relativa");
    }

    //FIN DE LA GRAFICA UNO


    //INICIO DE LA GRAFICA DOS

    public void llenarGrafica2(LineChart lineChart,String data) throws JSONException {
        //Se inicializan dos listas la primera de typo Entry y la segunda de tipo String
        ArrayList<Entry> numeros = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        /*se le mandan 4 variables , los jsonObjects que se llenaron cuando se mando llamar la funcion  ObtenerDatos al llamar el metodo
        llenarGrafica1 ,Se le manda la lista numeros vacia para que la llene el metodo , y tambien la lista labels para que la llene,
        y por ultimo la variable data que data , es el campo que queremos del json del servor , por ejemplo si queremos la temperatura
        esa variable seria igual a "temperatura" data = "temperatura" */
        preparandoDatosGraficar(jsonObjects, numeros, labels, data);
        LineDataSet dataSet = new LineDataSet(numeros,"");
        dataSet.setColors(colores);
        LineData ldata = new LineData(labels,dataSet);
        lineChart.setData(ldata);
        lineChart.setDescription("Humedad Relativa");
        lineChart.animateY(3000);

    }

    //FIN GRAFICA DOS

    public void preparandoDatosGraficar(JSONObject[] jsonObjects,ArrayList<Entry> list,ArrayList<String> labels,String dat) throws JSONException {
        //Sacamos el ancho de los json que se guararon en la variable jsonObjects
        int size = jsonObjects.length;
        for (int i = 1;i<size;i++){
            //Guardamos el json anterior para poder hacer comparacion entre fechas
            JSONObject sant = jsonObjects[i-1];
            JSONObject fechaAnterior = new JSONObject(sant.getString("fecha"));
            String[] horaAnterior = fechaAnterior.getString("value").split(" ")[1].split(":");

            //Guardamos el json actual para poder haceer comparaciones con el json anterior
            JSONObject sact = jsonObjects[i];
            JSONObject fecha = new JSONObject(sact.getString("fecha"));
            String[] horaAtual = fecha.getString("value").split(" ")[1].split(":");

            /*Sacamos del jsonAnterior el campo que ocupamos con la variable dat, por ejemplo si queremos sacar la temperatura
            Entonces la cariable dat seria igua a temperatura dat="temperatura"
             */
            JSONObject tmpAnt = new JSONObject(sant.getString(dat));

            //Comparamos si la hora anterior y la actual son igual
            if(parseInt(horaAnterior[0])==parseInt(horaAtual[0])){
                //Comprobamos si hay una diferencia de 5 minutos entre la hora anterior y la hor actual
                if(parseInt(horaAtual[1])-parseInt(horaAnterior[1])>=5){
                    //Si hay la diferenci entonces guardamos la hora anterior en label y el valor de tmpAnt.getString("value")) en list

                    //Si i == 1, osea la primera vuelta del ciclo , entonces guardamos la primera hora en labels y el valor en list
                    if(i == 1){
                        labels.add(horaAnterior[0]+":"+horaAnterior[1]);
                    }else{
                        labels.add(horaAnterior[0]+":"+horaAnterior[1]);
                    }
                    list.add(new Entry(parseInt(tmpAnt.getString("value")), i - 1));
                }
            }else{
                //Si las horas no son iguales entonces guardamos en la lista de labels la hora anterior y los minutos
                labels.add(horaAnterior[0] + ":" + horaAnterior[1]);
                //y guardamos el valor anterior en la lista
                list.add(new Entry(parseInt(tmpAnt.getString("value")),i-1));
            }

        }
        //Con estas ultimas lineas ponermos el ultimo valor de la lista en la grafica , si las quitamos el ultimo valor no lo mostrara
        JSONObject sact = jsonObjects[size-1];
        JSONObject fecha = new JSONObject(sact.getString("fecha"));
        String[] horaAtual = fecha.getString("value").split(" ")[1].split(":");
        labels.add(horaAtual[0] + ":" + horaAtual[1]);

        JSONObject tmpAct = new JSONObject(sact.getString(dat));

        list.add(new Entry(parseInt(tmpAct.getString("value")), size));

    }
    public int parseInt(String c){ return Integer.parseInt(c);}


    public float getValorGrafica1(){
        return valorGrafica1;
    }



    public void ObtenerDatos(String type,String field){
        String dir = "http://207.249.127.215:1026/v2/entities?type="+type;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection connection;

        try {
            url = new URL(dir);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();

            if(code==HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                String json = "";
                while((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                json = response.toString();
                JSONArray jsonAr = null;

                try {
                    jsonAr = new JSONArray(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject obJSON = null;
                int size = jsonAr.length();
                jsonObjects = new JSONObject[size];
                for (int i = 0;i<size;i++){
                    obJSON = jsonAr.getJSONObject(i);
                    jsonObjects[i] = obJSON;
                }

                valorJSON = new JSONObject(jsonAr.getJSONObject(size-1).getString(field));

                valorGrafica1 = Float.parseFloat(""+valorJSON.getDouble("value"));

            }else{
                Log.e("json","Ocurrio un error");
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
