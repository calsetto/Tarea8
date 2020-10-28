package com.example.tarea8alterna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Editar extends AppCompatActivity {

    EditText recordatorio_editar;
    CalendarView calendario_editar;
    Button editar_btn;
    TextView id;
    public String date_1;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        recordatorio_editar = (EditText)findViewById(R.id.recordatorio_editar);
        calendario_editar = (CalendarView) findViewById(R.id.calendario_editar);
        editar_btn = (Button) findViewById(R.id.editar_btn);
        editar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        id = (TextView)findViewById(R.id.id);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        id.setText(Home.users.get(position).getId());
        recordatorio_editar.setText(Home.users.get(position).getRecordatorio());
        String date = Home.users.get(position).getFecha();

        String parts[] = date.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        long milliTime = calendar.getTimeInMillis();
        calendario_editar.setDate(milliTime);

        calendario_editar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date_1 = (dayOfMonth) + "/" + month + "/" + year;
            }
        });


    }

    public void actualizar() {

        final String finaldate = date_1;

        if (recordatorio_editar.getText().toString().isEmpty()) {
            recordatorio_editar.setError("Debes completar los campos");
        } else {
            Context context;
            //Toast.makeText(Editar.this, finaldate, Toast.LENGTH_SHORT).show();
            final ProgressDialog progressDialog = new ProgressDialog(Editar.this);
            progressDialog.setMessage("Cargando...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.14/tarea8/update.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(Editar.this, response + " KLKKKK", Toast.LENGTH_SHORT).show();
                            Log.e("error", response);
                            startActivity(new Intent(getApplicationContext(), Home.class));
                            finish();
                            progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Editar.this, "Debes seleccinar una fecha", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", id.getText().toString());
                    params.put("recordatorio", recordatorio_editar.getText().toString());
                    params.put("fecha", finaldate);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Editar.this);
            requestQueue.add(stringRequest);
        }
    }
}
