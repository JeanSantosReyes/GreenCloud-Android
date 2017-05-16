package cherubs;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mand.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;

import java.util.Calendar;

import Model.getData;

public class tomateActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private getData data = new getData();

    private Toolbar toolbar;
    private String type;

    private BarChart barra;
    private LineChart lineChart;

    private TextView medicion;

    private DatePicker datePicker;
    private Calendar calendar;
    private int Currentyear, Currentmonth, Currentday;


    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_tomate);
        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        barra = (BarChart) findViewById(R.id.temperatura);
        lineChart = (LineChart) findViewById(R.id.temperaturas);
        barra.setVisibility(View.INVISIBLE);
        lineChart.setVisibility(View.INVISIBLE);
        medicion = (TextView) findViewById(R.id.medicion);

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
    public void changeToTemperaturaCherubs(View v) throws JSONException {
        barra.setVisibility(View.VISIBLE);//hacemos visible la barra para graficar
        lineChart.setVisibility(View.VISIBLE);
        medicion.setText("Temperatura");//se le manda el titulo de la variable de medicion
        data.llenarGrafica1(barra, type, "temperatura", "Temperatura");//llamada al metodo
        int[] a = new int[3];
        int[] b = new int[3];
        data.llenarGrafica2(lineChart, "temperatura", a, b);
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


    public void setDate(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "Picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(this, year + " " + monthOfYear + " " + " " + dayOfMonth, Toast.LENGTH_LONG).show();
        try {
            int[] a,b;
            a = new int[3];
            b = new int[3];
            data.llenarGrafica2(lineChart,"temperatura",a,b);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }

    }
}
