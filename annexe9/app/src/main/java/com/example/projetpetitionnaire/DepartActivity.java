package com.example.projetpetitionnaire;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class DepartActivity extends AppCompatActivity {


    Button boutonRecup, boutonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);

        boutonRecup = findViewById(R.id.boutonRecup);
        boutonStart = findViewById(R.id.boutonStart);

        Ecouteur ec = new Ecouteur();
        boutonStart.setOnClickListener(ec);
        boutonRecup.setOnClickListener(ec);
    }

    private class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            if ( view == boutonRecup)
            {
                try(
                        FileInputStream fis = openFileInput("fichier.ser");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        ){
                    Membre m = (Membre) ois.readObject();

                    AlertDialog.Builder builder = new AlertDialog.Builder(DepartActivity.this);
                    builder.setMessage(m.getPrenom() +" "+m.getNom());

                    AlertDialog alertDialog = builder.create();

                    alertDialog.show();

                }
                catch(IOException e){
                    e.printStackTrace();
                }
                catch (ClassNotFoundException e){
                    e.printStackTrace();
                }

            }
            else  // boutonStart
            {
                Intent i = new Intent(DepartActivity.this, ConteneurFragmentsActivity.class);
                startActivity(i);

            }
        }
    }
}