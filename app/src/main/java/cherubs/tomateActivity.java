package cherubs;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.mand.myapplication.R;

public class tomateActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private String type;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_tomate);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            type= extras.getString("type");
        }

       if(Build.VERSION.SDK_INT >= 21){
           Window window = this.getWindow();
           window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
       }

    }




    public boolean onCreateOptionsMenu(Menu menu){
        toolbar.setTitle(type);
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
        Intent intent = new Intent(this, tomatetemperaturaActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    public void changeToHumedadRelativaCherubs(View v){
        Intent intent = new Intent(this, tomatehrelativaActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    public void changeToHuedadSueloCherubs(View v){
        Intent intent = new Intent(this, tomatehsueloActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
