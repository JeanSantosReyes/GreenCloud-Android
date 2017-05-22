package com.example.mand.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import org.json.JSONException;

public class MyViewPager extends FragmentPagerAdapter {
    private static int NUM = 2;
    private Fragment[] fragmentsList;
    private FragmentManager fragmentManager;
    private FragmentGrafica grafica = FragmentGrafica.newInstance(0,"Barra","0,0","","");
    private FragmetLinea linea = FragmetLinea.newInstance(1,"Linea","0,0","","");
    private String posicion,type,field,mensaje;

    public MyViewPager(FragmentManager manager,String posicion,String type,String field,String mensaje){
        super(manager);
        this.posicion = posicion;
        this.type = type;
        this.field = field;
        this.mensaje = mensaje;


        this.fragmentManager = manager;
        fragmentsList = new Fragment[NUM];
        fragmentsList[0] = grafica;
        fragmentsList[1] = linea;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                fragmentsList[position] = FragmentGrafica.newInstance(0,"Barra",posicion,type,field);
                return fragmentsList[position];
            case 1:
                fragmentsList[position] = FragmetLinea.newInstance(1,"Historial",posicion,type,field);
                return fragmentsList[position];
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return NUM;
    }
    private String[] dos = {"Actual","Historial"};
    public CharSequence getPageTitle(int position){
        return dos[position];
    }

    public void updateFragment(int fragment,String type,String seccion,String field,String mensaje) throws JSONException {
        Log.d("update"," "+fragment+" " +seccion+" "+field+" "+type);
        switch (fragment){
            case 0:
                ((FragmentGrafica)fragmentsList[0]).updateGrafica(type,seccion,field,mensaje);
                break;
            case 1:
                ((FragmetLinea)fragmentsList[1]).updateLinea(type, seccion, field,mensaje);
                break;
        }
    }
}