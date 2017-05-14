package cherubs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mand.myapplication.R;

public class cherubsActivity extends AppCompatActivity{
    private Toolbar toolbar;


    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_cherubs);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }




    public boolean onCreateOptionsMenu(Menu menu){
        toolbar.setTitle("Cherubs");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            break;
        }
        return true;
    }
    public void changeToTemperaturaCherubs(View v){
        Intent intent = new Intent(this,cherubstemperaturaActivity.class);
        startActivity(intent);
    }
    public void changeToHumedadRelativaCherubs(View v){
        Intent intent = new Intent(this,cherubshrelativaActivity.class);
        startActivity(intent);
    }
    public void changeToHuedadSueloCherubs(View v){
        Intent intent = new Intent(this,cherubshsueloActivity.class);
        startActivity(intent);
    }
}
