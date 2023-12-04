package com.example.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.Vector;

public class ThemeSelect extends AppCompatActivity {

    private ListView themeListView;
    private Ecouteur ec;
    private Vector<HashMap<String, Object>> listeTheme;
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_select);
        getSupportActionBar().hide();
        themeListView = findViewById(R.id.listTheme);
        ec = new Ecouteur();
        listeTheme = new Vector<>();
        //remplissage du vecteur pour tout les theme possible
        listeTheme.add(
            new HashMap(){{
                put("name", "Drain Gang Theme");
                put("image", R.drawable.background1);
            }}
        );
        listeTheme.add(
                new HashMap(){{
                    put("name", "Bladee Theme");
                    put("image", R.drawable.background2);
                }}
        );
        listeTheme.add(
                new HashMap(){{
                    put("name", "Ecco2k Theme");
                    put("image", R.drawable.background3);
                }}
        );
        listeTheme.add(
                new HashMap(){{
                    put("name", "Thaiboy Digital Theme");
                    put("image", R.drawable.background4);
                }}
        );

        simpleAdapter = new SimpleAdapter(
                this,
                listeTheme,
                R.layout.liste_item,
                new String[]{"name", "image"},
                new int[]{R.id.name, R.id.image}
        );

        themeListView.setAdapter(simpleAdapter);
        themeListView.setOnItemClickListener(ec);
    }
    private class Ecouteur implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //on retourne du boomerang une hashmap contenant les information du theme selectionné
            Intent i = new Intent();
            HashMap<String , Object>themeChoisi = listeTheme.get(position);
            i.putExtra("theme", themeChoisi);
            setResult(RESULT_OK, i);
            finish();

        }
    }
}