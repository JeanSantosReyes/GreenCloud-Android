package Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mand.myapplication.R;
import com.example.mand.myapplication.secciones;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import BaseDeDatos.sqlite;
import Fragments.DialogFragmentGeneral;

public class ObtenerDatosGrafica2 {
    private JSONObject valorJSON;
    private ArrayList<JSONObject> jsonValores;

    private LineChart linea;
    private String tabla,posicion,campo,mensaje;
    private Context context;
    private ArrayList<Entry> datos;
    private ArrayList<String> mensajes;
    private ArrayList<String> labels;
    private boolean flag;
    private int tipoEstadisca,size;

    private int temperaturaMaxima,temperaturaMinima,version;
    public ObtenerDatosGrafica2(LineChart linea,String tabla,String posicion,String campo,String mensaje,Context context,int tipoEstadistica) throws JSONException {
        this.linea = linea;
        this.tabla = tabla;
        this.posicion = posicion;
        this.campo = campo;
        this.mensaje = mensaje;
        this.context = context;
        this.tipoEstadisca = tipoEstadistica;

        this.version = Integer.parseInt(context.getString(R.string.version_db));

        this.datos = new ArrayList<>();
        this.mensajes = new ArrayList<>();




        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Obteniendo datos del servidor...");
        dialog.setMax(100);
        dialog.setProgress(0);
        dialog.setCancelable(false);

       new AsynTask(context,dialog,this).execute();

        obtenerMaximosMinimos();
    }
    public void obtenerMaximosMinimos(){
        sqlite bh = new sqlite(context,"MMTable",null,version);
        SQLiteDatabase db = bh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM MMTable WHERE variedad = '"+tabla+"'",null);
        long i = c.getCount();

        if(i>0){
            try{
                if(c.moveToFirst()){
                    flag = true;
                    temperaturaMaxima = Integer.parseInt(c.getString(1));
                    temperaturaMinima = Integer.parseInt(c.getString(2));
                }
            }finally {

            }
        }else{
            flag = false;
        }

    }

    static class AsynTask extends AsyncTask<Void,Void,Void>{
        Context context;
        ProgressDialog dialog;
        ObtenerDatosGrafica2 obj;
        public AsynTask(Context context,ProgressDialog dialog,ObtenerDatosGrafica2 obj){
            this.context = context;
            this.dialog = dialog;
            this.obj = obj;
        }

        @Override
        protected Void doInBackground(Void... params) {
                Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("America/Mexico_City"));
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH)+1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                //Formateando los datos para el servicio

                Log.d("eta",""+obj.tipoEstadisca);
                String y = (""+year).length()==1?"0"+year:""+year;
                String m = (""+month).length()==1?"0"+month:""+month;
                String d = (""+day).length()==1?"0"+day:""+day;
                String h  = (""+hour).length()==1?"0"+hour:""+hour;
                /* tipos de estadisticas
                *  1- es igual a una hora
                *  2-es igual a un dia
                *  3-es igual a una semana
                *  4-es igual a un mes
                */
                switch (obj.tipoEstadisca){
                    case 0:
                        Log.d("maickol", "Opcion numero 0");
                        //obj.obtenerValores();
                        //obj.filtroUltimos10();
                        obj.obtenerEstadisticasHora(y,m,d,h,0,"hora");
                        break;
                    case 1:
                        //obj.obtenerValores();
                        //obj.filtroUltimos10();
                        obj.obtenerEstadisticasHora(y,m,d,h,1,"hora");
                        Log.d("maickol", "Opcion numero 1 "+year+" "+month+" "+day+" "+hour);
                        break;
                    case 2:
                        obj.obtenerEstadisticasHora(y,m,d,h,2,"hora");
                        Log.d("maickol", "Opcion numero 1 " + year + " " + month + " " + day + " " + hour);
                        break;
                    case 3:
                        obj.obtenerEstadisticasHora(y,m,d,h,3,"dia");
                        break;
                    case 4:
                        obj.obtenerEstadisticasHora(y,m,d,h,4,"dia");
                        break;

                }

            return null;
        }

        @Override
        public void onPreExecute(){
            Log.d("maicol", "yea");
            dialog.show();
        }
        @Override
        public void onPostExecute(Void unused) {
            if(obj.size > 0){
                obj.graficar();
            }else{
                Toast.makeText(context,"No hay datos para mostar en las estadisticas",Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        }
    }
    public void graficar(){
        LineDataSet dataSet = new LineDataSet(datos,"");

        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        dataSet.setDrawFilled(true);

        dataSet.setLineWidth(5);



        LineData lineData = new LineData(dataSet);

       if(flag){
           LimitLine limitLine = new LimitLine(temperaturaMaxima,"Temperatura maxima");

           linea.getAxisLeft().addLimitLine(limitLine);

           LimitLine limitLine1 = new LimitLine(temperaturaMinima, "Temperatura minima");

           linea.getAxisLeft().addLimitLine(limitLine1);

           limitLine.setLineWidth(4f);
           limitLine.setTextSize(12f);

           limitLine1.setLineWidth(4f);
           limitLine1.setTextSize(12f);
       }





        XAxis xAxis = linea.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, AxisBase axisBase) {
                Log.d("androide",mensajes.get((int) v % mensajes.size()));
                return mensajes.get((int) v % mensajes.size());
            }
        });

        linea.setDescription(null);

        linea.setData(lineData);


        linea.animateY(3000);

        linea.getLegend().setEnabled(false);

        linea.getAxisRight().setDrawLabels(false);

        //linea.setOnChartValueSelectedListener(new oyenteGrafica());

    }
    public class oyenteGrafica implements OnChartValueSelectedListener{


        @Override
        public void onValueSelected(Entry entry, Highlight highlight) {
            Toast.makeText(context,""+entry.toString(),Toast.LENGTH_SHORT).show();
            FragmentManager fm = ((FragmentActivity)context).getSupportFragmentManager();
            DialogFragmentGeneral dialogFragmentGeneral = DialogFragmentGeneral.newInstance("resuengrafica");
            dialogFragmentGeneral.show(fm, "");
            Log.d("maickol", "" + highlight.toString());
        }

        @Override
        public void onNothingSelected() {

        }
    }

    public void obtenerEstadisticasHora(String year,String month,String day,String hora,int tipo,String valorLabel){
        //String dir = "http://207.249.127.215:1026/v2/entities?q=position=='"+posicion+"'&type="+tabla;
        String dir= "";
        switch (tipo){
            case 0:
                dir = "http://tatallerarquitectura.com/fiware/hora/"+tabla+"/"+posicion+"/"+year+"/"+month+"/"+day+"/"+hora;
                break;
            case 1:
                dir = "http://tatallerarquitectura.com/fiware/hora/"+tabla+"/"+posicion+"/"+year+"/"+month+"/"+day+"/"+hora;
                break;
            case 2:
                dir = "http://tatallerarquitectura.com/fiware/dia/"+tabla+"/"+posicion+"/"+year+"/"+month+"/"+day;
                break;
            case 3:
                dir = "http://tatallerarquitectura.com/fiware/semana/"+tabla+"/"+posicion+"/"+year+"/"+month+"/"+day;
                break;
            case 4:
                dir = "http://tatallerarquitectura.com/fiware/mes/"+tabla+"/"+posicion+"/"+year+"/"+month;
                break;
        }

        Log.d("mikol",dir+" "+hora);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        URL url = null;
        HttpURLConnection conexion;

        try{
            url = new URL(dir);

            conexion = (HttpURLConnection) url.openConnection();

            conexion.setRequestMethod("GET");

            conexion.connect();

            if(conexion.getResponseCode() == HttpURLConnection.HTTP_OK){
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

                Log.d("mikol", ""+jsonArray.toString());
                jsonValores = new ArrayList<>();
                size = jsonArray.length();
                int index = 0;
                Log.d("mikol",""+size);
                for(int i = 0;i<size;i++){
                    valorJSON = jsonArray.getJSONObject(i);
                    mensajes.add(valorJSON.getString(valorLabel));
                    datos.add(new Entry(i,parse(valorJSON.getString(campo))));
                }
            }
        }catch (Exception e){

        }

    }


    public String[] split(String value,int position){
        return value.split(" ")[position].split(":");
    }
    public int parse(String c){ return Integer.parseInt(c);}
}
