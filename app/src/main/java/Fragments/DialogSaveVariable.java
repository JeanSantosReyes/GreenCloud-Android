package Fragments;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mand.myapplication.R;

public class DialogSaveVariable extends DialogFragment{
    public static DialogSaveVariable newInstance(String fecha,String variedad,String sector,String valor){
        DialogSaveVariable dsv = new DialogSaveVariable();
        Bundle extras = new Bundle();
        extras.putString("fecha",fecha);
        extras.putString("variedad",variedad);
        extras.putString("sector",sector);
        extras.putString("valor", valor);
        dsv.setArguments(extras);

        return dsv;
    }
    public DialogSaveVariable(){

    }
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle bundle){
        return inflater.inflate(R.layout.fragmente_save_value,null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String fecha = getArguments().getString("fecha");
        String variedad = getArguments().getString("variedad");
        String sector = getArguments().getString("sector");
        String valor = getArguments().getString("valor");

        TextView mEditText = (TextView) view.findViewById(R.id.texto);
        mEditText.setText(fecha+" "+variedad+" "+sector+" "+valor);
    }


}
