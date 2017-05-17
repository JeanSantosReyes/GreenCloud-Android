package com.example.mand.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class secciones extends AppCompatActivity {
    private Spinner spinner;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.secciones);
        if(Build.VERSION.SDK_INT>=21){
            Window window = this.getWindow();
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        spinner = (Spinner) findViewById(R.id.tomatesSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tomates_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
