package ReciclerMinimosMaximos;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mand.myapplication.R;

import java.util.List;

public class adaptadorMinimosMaximos extends RecyclerView.Adapter<adaptadorMinimosMaximos.minmaxHolder>{
    private List<MinimosMaximos> minimosMaximos;
    public adaptadorMinimosMaximos(List<MinimosMaximos> minimosmaximos){
        this.minimosMaximos = minimosmaximos;
    }

    public static class minmaxHolder extends RecyclerView.ViewHolder{
        private TextView TMin,TMax,HRMin,HRMax,HSMin,HSMax,TnombreVariedad,HSNombreVariedad,HRNombreVariedad;
        public minmaxHolder(View itemView) {
            super(itemView);

            TnombreVariedad = (TextView) itemView.findViewById(R.id.TNombreVariedad);
            TMax = (TextView) itemView.findViewById(R.id.TMaxima);
            TMin = (TextView) itemView.findViewById(R.id.TMinima);

            HSNombreVariedad = (TextView) itemView.findViewById(R.id.HSNombreVariedad);
            HSMin = (TextView) itemView.findViewById(R.id.HSMinima);
            HSMax = (TextView) itemView.findViewById(R.id.HSMaxima);

            HRNombreVariedad = (TextView) itemView.findViewById(R.id.HRNombreVariedad);
            HRMax = (TextView) itemView.findViewById(R.id.HRMaxima);
            HRMin = (TextView) itemView.findViewById(R.id.HRMinima);

        }
    }

    @Override
    public minmaxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carcasa_minimos_maximos,parent,false);
        return new minmaxHolder(view);
    }

    @Override
    public void onBindViewHolder(minmaxHolder holder, int position) {
        MinimosMaximos mm = minimosMaximos.get(position);
        holder.HSNombreVariedad.setText(mm.getVariedad());
        holder.TnombreVariedad.setText(mm.getVariedad());
        holder.HRNombreVariedad.setText(mm.getVariedad());

        holder.TMax.setText(mm.getTmaxima());
        holder.TMin.setText(mm.getTminima());

        holder.HRMax.setText(mm.getHRmaxima());
        holder.HRMin.setText(mm.getHRminima());

        holder.HSMax.setText(mm.getHSmaxima());
        holder.HSMin.setText(mm.getHSminima());

    }

    @Override
    public int getItemCount() {
        return minimosMaximos.size();
    }

}
