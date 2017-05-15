package glorys;

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



public class glorysActivity extends AppCompatActivity {
    private Toolbar toolbar;


    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_glorys);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

    }




    public boolean onCreateOptionsMenu(Menu menu){
        toolbar.setTitle("Glorys");
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
    public void changeToTemperaturaGlorys(View v){
        Intent intent = new Intent(this, glorystemperaturaActivity.class);
        startActivity(intent);
    }
    public void changeToHumedadRelativaGlorys(View v){
        Intent intent = new Intent(this, gloryshrelativaActivity.class);
        startActivity(intent);
    }
    public void changeToHuedadSueloGlorys(View v){
        Intent intent = new Intent(this, gloryshsueloActivity.class);
        startActivity(intent);
    }
}
