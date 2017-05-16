package cherubs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.mand.myapplication.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONException;


import java.util.Calendar;

import Model.getData;

public class tomatetemperaturaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private BarChart barra;
    private LineChart lineChart;

    private getData data = new getData();

    private Toolbar toolbar;

    String type;

    private DatePicker datePicker;
    private Calendar calendar;
    private int Currentyear, Currentmonth, Currentday;

    public void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_tomate_temperatura);

        toolbar = (Toolbar) findViewById(R.id.toolbarGen);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            type = extras.getString("type");
        }


        barra = (BarChart) findViewById(R.id.temperatura);

        lineChart = (LineChart) findViewById(R.id.temperaturas);

         inicializandoGraficarUno();
        try {
            iniciarlizarGraficaDos();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        calendar = Calendar.getInstance();
        Currentday = calendar.get(Calendar.YEAR);
        Currentmonth = calendar.get(Calendar.MONTH);
        Currentyear = calendar.get(Calendar.DAY_OF_MONTH);
        Toast.makeText(this,Currentday+" "+Currentmonth+" "+Currentyear,Toast.LENGTH_LONG).show();

    }

    //Codigo de la grafica numero 1
    public void inicializandoGraficarUno(){
        data.llenarGrafica1(barra, type, "temperatura", "Temperatura");
    }
    //FIN DEL COEDIGO DE LA GRAFICA UNO

    //CODIGO DE LA GRAFICA DOS
    public void iniciarlizarGraficaDos() throws JSONException {
        data.llenarGrafica2(lineChart, "temperatura",0,0,0);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        toolbar.setTitle("Temperaturas");
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


    public void setDate(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"Picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Toast.makeText(this,year+" "+monthOfYear+" "+" "+dayOfMonth,Toast.LENGTH_LONG).show();
        try {
            data.llenarGrafica2(lineChart,"temperatura",dayOfMonth,monthOfYear,year);
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
