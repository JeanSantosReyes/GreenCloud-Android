package Fragments;


import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mand.myapplication.R;

import org.w3c.dom.Text;

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


        ImageView imageVariedad = (ImageView) view.findViewById(R.id.imgVariedadSave);
        ImageView imageVariable = (ImageView) view.findViewById(R.id.imgVariableSave);
        TextView variedadDescripcion = (TextView) view.findViewById(R.id.DescripcionVariable);
        TextView variedadValor = (TextView) view.findViewById(R.id.valorVariable);

        //METODO PARA PINTAR LA RESPECTIVA VARIEDAD EN EL DIALOGO
        chooseVariedad(imageVariedad,tipo);

        //METODO PARA PINTAR LA RESPECTIVA VARIABLE EN EL DIALOGO
        chooseVariable(imageVariable,campo);

        //ESTABLECEMOS LA DESCRIPCION DE LA VARIABLE
        variedadDescripcion.setText(campo);
        //ESTABLECEMOS EL VALOR DE LA VARIABLE
        variedadValor.setText(valor);


    }
    //ESTE METODO ES PARA SEPARAR DONDE SE PONE LA IMGEN EN AUTOMATICO EN EL DIALOGO DEPENDIENDO QUE VARIEDAD ESTE EN FOCO
    public void chooseVariedad(ImageView img,String tipo){
        switch (tipo){
            case "cherubs":
                img.setImageResource(R.drawable.cherub);
                break;
            case "sunburts":
                img.setImageResource(R.drawable.sunburst);
                break;
            case "glorys":
                img.setImageResource(R.drawable.glorys);
                break;
            case "jubilees":
                img.setImageResource(R.drawable.jubilees);
                break;
            case "eclipses":
                img.setImageResource(R.drawable.eclipse);
                break;
        }
    }
    public void chooseVariable(ImageView img,String variable){
        switch (variable){
            case "temperatura":
                img.setImageResource(R.drawable.temperatura);
                break;
            case "humedadRelativa":
                img.setImageResource(R.drawable.humedadrelativa);
                break;
            case "humedadSuelo":
                img.setImageResource(R.drawable.humedadsuelo);
                break;
        }
    }

}
