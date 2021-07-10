package com.example.amst_1ep_grupo8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class graficas extends AppCompatActivity {
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);
        graficar();
    }


    private void graficar(){
        TextView salida_datos = findViewById(R.id.habilidades_result);
        String texto_buscar= getIntent().getExtras().getString("mis_datos");
        mQueue = Volley.newRequestQueue(this);
        String url="https://superheroapi.com/api/4589618454427633/search/"+texto_buscar;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String salida="";
                            JSONArray jsonArray = response.getJSONArray("results");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject heroes_lista=jsonArray.getJSONObject(i);
                                salida=salida+"\n"+heroes_lista.getString("name");
                            }

                            salida_datos.setText(salida);
                            salida_datos.setText(texto_buscar);
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
}