package com.example.amst_1ep_grupo8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buscar = findViewById(R.id.buscar);
        buscar.setOnClickListener(v -> jsonParse());
    }
    
    private void jsonParse(){
        mQueue = Volley.newRequestQueue(this);
        final EditText busqueda_heroe = findViewById(R.id.textoabuscar);
        final TextView subtitulo = findViewById(R.id.subtitulo);
        String texto_buscar = busqueda_heroe.getText().toString();
        String url="https://superheroapi.com/api/4589618454427633/search/"+texto_buscar;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                                Intent nuevo = new Intent(MainActivity.this,busquedaResultado.class);
                                nuevo.putExtra("mis_datos",texto_buscar);
                                startActivity(nuevo);
                            /*for (int i =0; i< jsonArray.length(); i++){
                                JSONObject heroe=jsonArray.getJSONObject(i);
                                String nombre=heroe.getString("name");
                                //subtitulo.setText(nombre);
                                subtitulo.setText(jsonArray.length());
                            }*/
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