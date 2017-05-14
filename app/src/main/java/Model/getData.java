package Model;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class getData{
    private float temperaturaActual,humeadadRelativaActual,humedadSueloActual;
    private JSONObject temperaturaJSON,humedadRelativaJSON,humedadSueloJSON;
    public JSONObject[] jsonObjects,jsonObjectsHRelativa,jsonObjectsHSuelo;
    public void ObtenerDatos(){
        String dir = "http://207.249.127.215:1026/v2/entities?type=cherubs";

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
                jsonObjectsHSuelo = jsonObjectsHRelativa = jsonObjects;


                temperaturaJSON = new JSONObject(jsonAr.getJSONObject(size-1).getString("temperatura"));
                temperaturaActual = Float.parseFloat(""+temperaturaJSON.getDouble("value"));

                humedadRelativaJSON = new JSONObject(jsonAr.getJSONObject(size-1).getString("humedadRelativa"));
                humeadadRelativaActual = Float.parseFloat(""+humedadRelativaJSON.get("value"));

                humedadSueloJSON = new JSONObject(jsonAr.getJSONObject(size-1).getString("humedadSuelo"));
                humedadSueloActual = Float.parseFloat(""+humedadSueloJSON.get("value"));

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
    public float getTempActual(){
        ObtenerDatos();
        return temperaturaActual;
    }
    public float getHumeadadRelativaActual(){
        ObtenerDatos();
        return humeadadRelativaActual;
    }
    public float getHumedadSueloActual(){
        ObtenerDatos();
        return humedadSueloActual;
    }
}
