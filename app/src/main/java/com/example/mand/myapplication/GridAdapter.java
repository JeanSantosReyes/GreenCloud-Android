package com.example.mand.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hector on 02/08/2017.
 */

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> arrayList;
    private int x = 0;

    public GridAdapter(Context context, ArrayList<String> arrayList, int x){
        this.context = context;
        this.arrayList = arrayList;
        this.x = x;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grid,null);
        }

        TextView tituloTv = (TextView) convertView.findViewById(R.id.ig_tv_titulo);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgIndicador);
        if(x == 1)
            img.setImageResource(R.drawable.housegreegreen);
        if(x == 2)
            img.setImageResource(R.drawable.hojasector);

        tituloTv.setText(arrayList.get(position));

        return convertView;
    }
}
