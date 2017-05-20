package com.example.mand.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;

import java.util.Calendar;

import Model.getData;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.setTitle("Variedades");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        return true;
    }

    //METODO PARA CAMBIAR DE ACTIVIDAD
    public void cherubsActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "cherubs");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","cherubs");
        startActivity(intent);
    }

    //METODO PARA CAMBIAR ALA ACTIVIDAD DE ECLIPSES
    public void eclipsesActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "eclipses");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","eclipses");
        startActivity(intent);
    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE GLORYS
    public void glorysActivity(View v){
       /* Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type", "glorys");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","glorys");
        startActivity(intent);
    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE JUBIES
    public void jubilesActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type","jubilees"); //Cambiar a "jubilees", así cambió en la API
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","jubilees");
        startActivity(intent);

    }

    //METODO PARA CAMBIR ALA ACTIVIDAD DE SUNBURTS
    public void sunburtsActivity(View v){
        /*Intent intent = new Intent(this, tomateActivity.class);
        intent.putExtra("type","sunburts");
        startActivity(intent);*/
        Intent intent = new Intent(this,secciones.class);
        intent.putExtra("type","sunburts");
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Nature Sweet Tomatoes, Variedades", Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyUp(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            salir();
        }
        return true;
    }
    public void salir(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Estas seguro que deseas salir?");
        dialog.setTitle("Alert");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
            }
        });
        dialog.setNegativeButton("Cancelar",null);
        dialog.show();
    }
}
