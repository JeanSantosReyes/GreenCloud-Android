package com.example.mand.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import Model.Alertas;

/**
 * Created by Hector on 27/07/2017.
 */

public class ServicioAlertas extends Service{
    private Context thisContext = this;
    int x = 0;

    time time = new time();
    @Override
    public void onCreate(){

    }

    @Override
    public int onStartCommand(Intent intent, int flag, int idProcess)
    {
        /*mp = MediaPlayer.create(thisContext, R.raw.cien);
        mp.start();*/
        Toast.makeText(thisContext, "Consultando las Alertas", Toast.LENGTH_SHORT).show();
        time.execute();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        Toast.makeText(thisContext, "Cerrando el Servicio", Toast.LENGTH_SHORT).show();
        time.cancel(true);
        x = 1;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    //creacion del hilo para la consulta de las alertar
    public void hilo(){
        try {
            //startService(new Intent(thisContext, ServicioAlertas.class));
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ejecutarConsulta(){
        time time = new time();
        time.execute();

    }

    public class time extends AsyncTask<Void,Integer,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            for(int i = 0; i < 5; i ++)
            {
                hilo();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(x == 0){
                Alertas obj1 = new Alertas(thisContext, "");
                obj1.llamar(100);
                ejecutarConsulta();
                Toast.makeText(ServicioAlertas.this, "5 Segundos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    ///////
}
