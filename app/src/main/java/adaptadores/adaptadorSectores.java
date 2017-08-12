package adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mand.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

import Model.Sector;

public class adaptadorSectores extends RecyclerView.Adapter<adaptadorSectores.SectoresHolder> {
    private Context context;
    private List<Sector> listSectores;

    public adaptadorSectores(List<Sector> listSectores,Context context){
        this.context = context;
        this.listSectores = listSectores;
    }


    public static class SectoresHolder extends RecyclerView.ViewHolder{
        private TextView textSector;
        private ImageView imgSector;
        public SectoresHolder(View view){
            super(view);
            textSector = (TextView) view.findViewById(R.id.textSector);
            imgSector = (ImageView) view.findViewById(R.id.imgSector);
        }
    }


    @Override
    public SectoresHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cascaron_sector,parent,false);
        return new SectoresHolder(view);
    }

    @Override
    public void onBindViewHolder(SectoresHolder holder, int position) {
        Sector sector = listSectores.get(position);

        holder.textSector.setText(sector.getNombre());
        holder.imgSector.setImageResource(R.drawable.greenhouse);
    }

    @Override
    public int getItemCount() {
        return listSectores.size();
    }

}
