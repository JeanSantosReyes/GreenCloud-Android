package com.example.mand.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;

import Model.ObtenerDatosGrafica2;

/**
 * Created by MAND on 19/05/2017.
 */
public class FragmetLinea extends FragmentGrafica {
    private LineChart linea;
    private ObtenerDatosGrafica2 data;
    String posicion,type,field;
    public static FragmetLinea newInstance(int page,String texto,String posicion,String type,String field){
        FragmetLinea fl = new FragmetLinea();
        Bundle bundle = new Bundle();
        bundle.putInt("someInt", page);
        bundle.putString("someString", texto);
        bundle.putString("posicion", posicion);
        bundle.putString("type",type);
        bundle.putString("field",field);
        fl.setArguments(bundle);
        return fl;
    }
    private int page;
    private String texto;

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

    }
    public View onCreateView(LayoutInflater inflater,ViewGroup group,Bundle bundle){
        View view = inflater.inflate(R.layout.fragment_lineal_1,group,false);
        linea = (LineChart) view.findViewById(R.id.linea);
        posicion = getArguments().getString("posicion");
        type = getArguments().getString("type");
        field = getArguments().getString("field");

        try {
            data = new ObtenerDatosGrafica2(linea,type,posicion,field,"",getActivity());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
    public void updateLinea(String type,String posicion,String field,String mensaje) throws JSONException {
        data = new ObtenerDatosGrafica2(linea,type,posicion,field,mensaje,getActivity());
    }
}
