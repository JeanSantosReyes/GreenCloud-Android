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

public class getData extends AsyncTask<Void,Void,Void>{
    private Context context;
    private String type;
    private ProgressDialog progress;
    public getData(String type,ProgressDialog progress,Context context){
        this.type = type;
        this.progress = progress;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        bajarType();
        return null;
    }

    public void onPreExecute() {
        progress.show();
    }
    public void onPostExecute(Void unused) {
        progress.dismiss();
    }

    public void bajarType(){
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

                for (int i = 0;i<jsonAr.length();i++){
                    obJSON = jsonAr.getJSONObject(i);
                    Log.e("json",obJSON.toString());
                }

            }else{
                Log.e("json","Ocurrio un error");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("json", "Ocurrio un error "+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("json", "Ocurrio un error " + e.getMessage());
        } catch (JSONException e) {
            Log.e("json", "Ocurrio un error "+e.getMessage());
            e.printStackTrace();
        }

    }
}
