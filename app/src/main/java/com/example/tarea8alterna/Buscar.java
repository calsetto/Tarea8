package com.example.tarea8alterna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Buscar extends AppCompatActivity {

    private ListView list;
    Adapter_buscar adapter;
    public static ArrayList<Users> users_buscar = new ArrayList<>();
    String Url = "http://192.168.0.14/tarea8/buscar.php";
    Users usuarios;
    private Calendar calendar_find;
    private SimpleDateFormat dateFormat;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        list = (ListView)findViewById(R.id.list_view_buscar);
        adapter = new Adapter_buscar(this, users_buscar);
        list.setAdapter(adapter);

        calendar_find = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar_find.getTime());
        //Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        Buscar();

    }

    public void Buscar(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                users_buscar.clear();
                Log.e("error", response);
                try{
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
                            users_buscar.add(usuarios);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(Buscar.this, e.getMessage() + "cARENALGA", Toast.LENGTH_SHORT).show();
                    Log.e("error", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Buscar.this, error.getMessage() + "MMG", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fecha", date);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
