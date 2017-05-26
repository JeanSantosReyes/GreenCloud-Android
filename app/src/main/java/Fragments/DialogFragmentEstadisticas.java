package Fragments;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mand.myapplication.R;

public class DialogFragmentEstadisticas extends DialogFragment{
   public DialogFragmentEstadisticas(){

   }

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle bundle){
        View v = inflater.inflate(R.layout.fragment_estadisticas,null);
        return v;
    }
}
