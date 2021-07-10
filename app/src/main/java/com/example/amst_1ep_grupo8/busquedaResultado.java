package com.example.amst_1ep_grupo8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class busquedaResultado extends AppCompatActivity {
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_resultado);
        mostrar_busquedas();
    }

    private void mostrar_busquedas(){
        TextView salida_datos = findViewById(R.id.resultado_nombres);
        String texto_buscar= getIntent().getExtras().getString("mis_datos");
        mQueue = Volley.newRequestQueue(this);
        String url="https://superheroapi.com/api/4589618454427633/search/"+texto_buscar;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject heroes_lista=jsonArray.getJSONObject(i);
                                funcion_crear_botones(heroes_lista.getString("name"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    public void funcion_crear_botones(String nombre){
        LinearLayout general= (LinearLayout) findViewById(R.id.general);
        Button button = new Button(this);
        button.setText(nombre);
        general.addView(button);
    }
}


