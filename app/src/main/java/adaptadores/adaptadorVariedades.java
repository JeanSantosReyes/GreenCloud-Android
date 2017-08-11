package adaptadores;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mand.myapplication.R;

import java.util.List;

import Model.Invernadero;

public class adaptadorVariedades extends RecyclerView.Adapter<adaptadorVariedades.VariedadesHolder>{
    private List<Invernadero> variedadList;
    private Context context;

    public adaptadorVariedades(List<Invernadero> variedadList,Context context){
        this.variedadList = variedadList;
        this.context = context;
    }

    public static class VariedadesHolder extends RecyclerView.ViewHolder{
        private ImageView logo;
        private TextView nombre;
        public VariedadesHolder(View itemView){
            super(itemView);

            logo = (ImageView) itemView.findViewById(R.id.imgVariedad);
            nombre = (TextView) itemView.findViewById(R.id.textVariedad);
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
        holder.logo.setImageResource(R.drawable.eclipse);
    }

    @Override
    public int getItemCount() {
        return variedadList.size();
    }

}
