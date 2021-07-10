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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class graficas extends AppCompatActivity {
    private RequestQueue mQueue;
    private BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas);
        graficar();
    }


    private void graficar(){
        barChart = findViewById(R.id.bargraph);
        TextView salida_datos = findViewById(R.id.heroeData);
        String texto_buscar= getIntent().getExtras().getString("id_hero");
        mQueue = Volley.newRequestQueue(this);
        String url="https://superheroapi.com/api/4589618454427633/"+texto_buscar;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String salida="";
                        try {
                            salida = salida+response.getString("name")+"\n"+response.getJSONObject("biography").getString("full-name");
                            JSONObject listadepoder=response.getJSONObject("powerstats");
                            int intelligence=listadepoder.getInt("intelligence");
                            int strength = listadepoder.getInt("strength");
                            int speed=listadepoder.getInt("speed");
                            int durability=listadepoder.getInt("durability");
                            int power = listadepoder.getInt("power");
                            int combate=listadepoder.getInt("combat");
                            salida_datos.setText(salida);

                            ArrayList<BarEntry> barEntries = new ArrayList<>();
                            barEntries.add(new BarEntry(intelligence,0));
                            barEntries.add(new BarEntry(strength,1));
                            barEntries.add(new BarEntry(speed,2));
                            barEntries.add(new BarEntry(durability,3));
                            barEntries.add(new BarEntry(power,4));
                            barEntries.add(new BarEntry(combate,5));
                            BarDataSet barDataSet=new BarDataSet(barEntries,"Poder");

                            ArrayList<String> poderes =new ArrayList<>();
                            poderes.add("Inteligencia");
                            poderes.add("Fueerza");
                            poderes.add("Velocidad");
                            poderes.add("DUrabilidad");
                            poderes.add("Poder");
                            poderes.add("Combate");

                            BarData theData= new BarData(barDataSet);
                            barChart.setData(theData);
                            barChart.setTouchEnabled(true);
                            barChart.setDragEnabled(true);
                            barChart.setScaleEnabled(true);

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

/*
Nombre del heroe
Nombre completo
Inteligencia
* fuerza
* velocidad
* durabilidad
* poder
* combate*/