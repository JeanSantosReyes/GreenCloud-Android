package adaptadores;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mand.myapplication.R;

import java.util.List;

import BaseDeDatos.FuncionesDB;
import Model.Invernadero;

public class adaptadorVariedades extends RecyclerView.Adapter<adaptadorVariedades.VariedadesHolder>{
    private List<Invernadero> variedadList;
    private Context context;
    private FuncionesDB fdb;

    public adaptadorVariedades(List<Invernadero> variedadList,Context context, FuncionesDB fdb){
        this.variedadList = variedadList;
        this.context = context;
        this.fdb = fdb;
    }

    public static class VariedadesHolder extends RecyclerView.ViewHolder{
        private ImageView logo;
        private TextView nombre,barra1,barra2,descripcion;
        public VariedadesHolder(View itemView){
            super(itemView);

            logo = (ImageView) itemView.findViewById(R.id.imgVariedad);
            nombre = (TextView) itemView.findViewById(R.id.textVariedad);
            barra1 = (TextView) itemView.findViewById(R.id.barraLateral1Variedad);
            barra2 = (TextView) itemView.findViewById(R.id.barraLateral2Variedad);
            descripcion = (TextView) itemView.findViewById(R.id.descripcionVariedad);
        }
    }

    @Override
    public VariedadesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carcaron_variedad,parent,false);
        return new VariedadesHolder(v);
    }

    @Override
    public void onBindViewHolder(VariedadesHolder holder, int position) {
        Invernadero var = variedadList.get(position);
        holder.nombre.setText(var.getNombre());
        holder.logo.setImageResource(R.drawable.greenhouse);

        //SACANDO EL NUMERO DE sectores

        int numeroSectores = this.fdb.coutSectores(variedadList.get(position).getId_invernadero());
        holder.descripcion.setText(numeroSectores+" Sectores Activos");
        if(numeroSectores==0){
            holder.barra1.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.barra2.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.barra1.setBackgroundColor(context.getResources().getColor(R.color.barColor));
            holder.barra2.setBackgroundColor(context.getResources().getColor(R.color.barColor));
        }
    }

    @Override
    public int getItemCount() {
        return variedadList.size();
    }

}
