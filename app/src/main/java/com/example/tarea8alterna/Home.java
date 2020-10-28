package com.example.tarea8alterna;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    private ListView list;
    Adapter adapter;
    FloatingActionButton btn, btn_1;
    public static ArrayList<Users>users = new ArrayList<>();
    String Url = "http://192.168.0.14/tarea8/retrieve.php";
    Users usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list = (ListView)findViewById(R.id.list_view);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Context context;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogoItem = {"Editar recordatorio", "Eliminar recordatorio"};
                builder.setTitle(users.get(position).getRecordatorio());
                builder.setItems(dialogoItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(), Editar.class)
                                .putExtra("position", position));
                                break;
                            case 1:
                                eliminar(position);
                                break;

                        }
                    }
                });
                builder.create().show();
            }
        });
        btn = (FloatingActionButton)findViewById(R.id.fab);
        btn_1 = (FloatingActionButton)findViewById(R.id.fab_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_agregar();
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_buscar();
            }
        });
        adapter = new Adapter(this, users);
        list.setAdapter(adapter);
        Mostrar_Datos();
    }

    public void Mostrar_Datos(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                users.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String recordatorio = object.getString("recordatorio");
                            String fecha = object.getString("fecha");

                            usuarios = new Users(id, recordatorio, fecha);
                            users.add(usuarios);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Context context;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void eliminar(final int pos){

        final ProgressDialog progressDialog = new ProgressDialog(Home.this);
        progressDialog.setMessage("Eliminando...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.14/tarea8/eliminar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                        startActivity(getIntent());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",Home.users.get(pos).getId());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        requestQueue.add(stringRequest);

    }

    public void btn_agregar(){
        startActivity(new Intent(getApplicationContext(), Insertar.class));
    }

    public void btn_buscar(){
        startActivity(new Intent(getApplicationContext(), Buscar.class));
    }
}
