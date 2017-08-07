package com.example.mand.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class ProgramasAdapter extends RecyclerView.Adapter<ProgramasAdapter.ItemViewHolder> {

    private List<ProgramaResponse> list;
    private Context context;
    String url = "";
    String parametros = "";
    ProgressDialog progeesDialog;
    public ProgramasAdapter(Context context, List<ProgramaResponse> list) {
        this.list = list;
        this.context=context;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemconfiguracionvariable, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final ProgramaResponse item = list.get(position);

        holder.idRegistro.setText(item.getIdRegistro()+"");
        holder.tomate.setText(item.getTomate());
        holder.variable.setText(item.getVariable());
        holder.maximo.setText(item.getMaximo());
        holder.minimo.setText(item.getMinimo());

        String type = holder.tomate.getText().toString();
        if(type.equals("cherubs")){
            holder.img.setImageResource(R.drawable.cherub);
        }else if(type.equals("eclipses")){
            holder.img.setImageResource(R.drawable.eclipse);
        }else if(type.equals("Sunburts")){
            holder.img.setImageResource(R.drawable.sunburst);
        }else if(type.equals("Glorys")){
            holder.img.setImageResource(R.drawable.glorys);
        }else if(type.equals("jubilees")){
            holder.img.setImageResource(R.drawable.jubilees);
        }

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ProgramasAdapter.this.context);
                builder.setMessage("Seleccione alguna opción del menú")
                        .setTitle("Opciones");
                builder.setPositiveButton("2.-Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        /**/

                        AlertDialog.Builder builder = new AlertDialog.Builder(ProgramasAdapter.this.context);


                        builder.setMessage("¿Desea Eliminar el Registro?")
                                .setTitle("Confirmación");
                        builder.setNegativeButton("1.-Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                url = "http://improntadsi.000webhostapp.com/WebServiceGreen/registrarVariables.php";
                                parametros = "accion=" + "4" + "&id=" + holder.idRegistro.getText().toString();

                                progeesDialog =  new ProgressDialog(ProgramasAdapter.this.context);
                                progeesDialog.setMessage("Eliminando, Espere...");
                                progeesDialog.setCancelable(false);
                                progeesDialog.show();
                                new ProgramasAdapter.EliminarDatos().execute(url);

                            }
                        });
                        builder.setPositiveButton("2.-Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                        AlertDialog dialoge = builder.create();
                        dialoge.setCancelable(false);
                        dialoge.show();

                    }
                });
                builder.setNegativeButton("1.-Modificar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(context,ModificarConfiguracionVariable.class);
                        intent.putExtra("idRegistro",holder.idRegistro.getText().toString());
                        intent.putExtra("tomate",holder.tomate.getText().toString());
                        intent.putExtra("variable", holder.variable.getText().toString());
                        intent.putExtra("max",holder.maximo.getText().toString());
                        intent.putExtra("min",holder.minimo.getText().toString());
                        context.startActivity(intent);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCancelable(true);
                dialog.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView idRegistro;
        public TextView tomate;
        public TextView variable;
        public TextView maximo;
        public TextView minimo;
        public LinearLayout linearLayout;
        public ImageView img;


        public ItemViewHolder(View v) {
            super(v);

            idRegistro = (TextView) v.findViewById(R.id.idRegistro);
            tomate = (TextView) v.findViewById(R.id.lbtypeTomate);
            variable = (TextView) v.findViewById(R.id.lbVariable);
            maximo = (TextView) v.findViewById(R.id.lbMaximo);
            minimo= (TextView) v.findViewById(R.id.lbMinimo);
            linearLayout =(LinearLayout) v.findViewById(R.id.car);
            img = (ImageView) v.findViewById(R.id.imgfondo);
        }
    }

    private class EliminarDatos extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            return Conexion.postDados(urls[0], parametros);
        }
        protected void onPostExecute(String resultado) {
            if (resultado.contains("OK")){
                progeesDialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(ProgramasAdapter.this.context);
                builder.setMessage("Registro Eliminado")
                        .setTitle("Éxito");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent intent = new Intent(context,ViewConfiguracionVariables.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }else {
                progeesDialog.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(ProgramasAdapter.this.context);
                builder.setMessage("NO Eliminado")
                        .setTitle("ERROR");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCancelable(false);
                dialog.show();

            }
        }
    }

}