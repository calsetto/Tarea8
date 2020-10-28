package com.example.tarea8alterna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Insertar extends AppCompatActivity {

    EditText recordatorio;
    CalendarView calendario;
    Button guardar_btn;
    public String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordatorio = (EditText)findViewById(R.id.recordatorio);
        calendario = (CalendarView)findViewById(R.id.calendario);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = (dayOfMonth) + "/" + month + "/" + year;
            }
        });
        guardar_btn = (Button)findViewById(R.id.guardar_btn);
        guardar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertar();
            }
        });
    }
    private void insertar() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        final String finaldate = date;

        if (recordatorio.getText().toString().isEmpty()) {
            recordatorio.setError("Debes completar los campos");
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.14/tarea8/insertar.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Datos insertados")) {
                        progressDialog.dismiss();
                        recordatorio.setText("");
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    } else {
                        Toast.makeText(Insertar.this, response  + "", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Insertar.this, "Debes seleccionar una fecha" + "MMG", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("recordatorio", recordatorio.getText().toString());
                    params.put("fecha", finaldate);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Insertar.this);
            requestQueue.add(request);
        }
    }}
