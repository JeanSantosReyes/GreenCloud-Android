package com.example.mand.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;

import Model.ObtenerDatosGrafica1;

public class FragmentGrafica extends Fragment {
    private BarChart grafica1;
    private ObtenerDatosGrafica1 data;
    private String position,type,field;
    public static FragmentGrafica newInstance(int page,String title,String position,String type,String field){
        FragmentGrafica fragmentGrafica1 = new FragmentGrafica();
        Bundle bundle = new Bundle();
        bundle.putInt("someInt",page);
        bundle.putString("someString",title);
        bundle.putString("position",position);
        bundle.putString("type",type);
        bundle.putString("field",field);
        fragmentGrafica1.setArguments(bundle);
        return fragmentGrafica1;
    }
    private int page;
    private String title;
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
    }
    public View onCreateView(LayoutInflater inflater,ViewGroup group,Bundle bundle){
        View view = inflater.inflate(R.layout.fragment_grafica_1,group,false);
        grafica1 = (BarChart) view.findViewById(R.id.grafica1);

        position = getArguments().getString("position");

        type = getArguments().getString("type");

        field = getArguments().getString("field");


        data = new ObtenerDatosGrafica1(grafica1,type,position,field,"temperatura",getActivity());


        return view;
    }

    public void updateGrafica(String type,String seccion,String field,String mensaje){
       data = new ObtenerDatosGrafica1(grafica1,type,seccion,field,mensaje,getActivity());
    }
}
