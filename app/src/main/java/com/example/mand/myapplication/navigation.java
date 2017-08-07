package com.example.mand.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
        private Context thisContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Navegación");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("ArchivoSp" , thisContext.MODE_PRIVATE);

        SharedPreferences sharedPreferences1 = getPreferences(thisContext.MODE_PRIVATE);
        SharedPreferences.Editor editor  = sharedPreferences1.edit();
        editor.putString("MiDato" , "nature");
        editor.commit();

        SharedPreferences sharedPreferences2 = getPreferences(thisContext.MODE_PRIVATE);
        String valor = sharedPreferences2.getString("MiDato" , "");

        if(valor.length() < 0){
            finish();
        }


        //time time = new time();
        //time.execute();
        //startService(new Intent(thisContext, ServicioAlertas.class));
    }


    public boolean onKeyUp(int key,KeyEvent event){
        if(key == KeyEvent.KEYCODE_BACK){
            salir();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.consultaVariables) {
            Intent go = new Intent(navigation.this, MainActivity.class);
            startActivity(go);
        }else if(id == R.id.closeSesion){
            Intent close = new Intent(navigation.this, login.class);
            startActivity(close);
            stopService(new Intent(thisContext, ServicioAlertas.class));
        }else if(id == R.id.ConfiguracionVariables){
            Intent intent = new Intent(navigation.this,ViewConfiguracionVariables.class);
            startActivity(intent);
        }else if(id == R.id.ConfiguracionInvernaderos){
            Intent intent = new Intent(navigation.this, ConfiguracionInvernaderos.class);
            startActivity(intent);

        }


        /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void salir(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Estas seguro que deseas salir?");
        alert.setTitle("Alerta");
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navigation.this.finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Cancelar", null);
        alert.show();
    }
}
