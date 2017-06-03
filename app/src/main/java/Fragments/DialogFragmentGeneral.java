package Fragments;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mand.myapplication.R;

public class DialogFragmentGeneral extends DialogFragment{
    public static DialogFragmentGeneral newInstance(String type){
        DialogFragmentGeneral dfg = new DialogFragmentGeneral();
        Bundle extras = new Bundle();
        extras.putString("type", type);
        dfg.setArguments(extras);
        return dfg;
    }
    private String type;
    public DialogFragmentGeneral(){
   }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle bundle){
        type = getArguments().getString("type");
        View v = null;
        switch (type){
            case "fragmentestadisticas":
                v = inflater.inflate(R.layout.fragment_estadisticas,null);
                break;
            case "resuengrafica":
                v = inflater.inflate(R.layout.fragment_resumen_grafica,null);
                break;

        }
        return v;
    }
}
