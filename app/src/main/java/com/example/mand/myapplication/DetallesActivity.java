package com.example.mand.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by Hector on 01/08/2017.
 */

public class DetallesActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalles);

        getSupportActionBar().setTitle(getIntent().getExtras().get("Nombre").toString());
    }
}
