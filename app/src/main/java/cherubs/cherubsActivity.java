package cherubs;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mand.myapplication.R;
import Model.getData;

public class cherubsActivity extends AppCompatActivity{
    private Toolbar toolbar;
    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_cherubs);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Obteniendo cherubs del servidor");
        progress.setMax(100);
        progress.setProgress(0);
        progress.setCancelable(false);
        new getData("cherubs",progress,this).execute();

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
}
