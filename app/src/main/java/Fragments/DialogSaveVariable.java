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
    public static DialogSaveVariable newInstance(String posicion,String tipo,String campo,String hora,String anio,String mes,String dia,String valor){
        DialogSaveVariable dsv = new DialogSaveVariable();
        Bundle extras = new Bundle();
        extras.putString("posicion",posicion);
        extras.putString("tipo",tipo);
        extras.putString("campo",campo);
        extras.putString("hora", hora);
        extras.putString("anio",anio);
        extras.putString("mes", mes);
        extras.putString("dia",dia);
        extras.putString("valor",valor);
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
        String posicion = getArguments().getString("posicion");
        String tipo = getArguments().getString("tipo");
        String campo = getArguments().getString("campo");
        String hora = getArguments().getString("hora");
        String anio = getArguments().getString("anio");
        String mes = getArguments().getString("mes");
        String dia = getArguments().getString("dia");
        String valor = getArguments().getString("valor");

        TextView mEditText = (TextView) view.findViewById(R.id.SaveMensaje);
        mEditText.setText(posicion+" "+tipo+" "+campo+" "+hora+" "+anio+" "+mes+" "+dia+" "+valor);
    }


}
