package com.example.filmoteca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaPeliculas extends AppCompatActivity {

    RecyclerView recycler;
    AdaptadorPeliculas adaptador;
    TextView label_usuario;
    Button aceptar;
    ArrayList<Pelicula> array_peliculas;
    DBPeliculas BD;
    String usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_pelicula);

        recycler = (RecyclerView) findViewById(R.id.recycler_peliculas);
        label_usuario = (TextView) findViewById(R.id.label_usuario);
        aceptar = (Button) findViewById(R.id.btn_añadir);

        /* Recuperamos el nombre del usuario introducido */
        Intent intent = getIntent();
        if(intent != null) {
            usuario = intent.getStringExtra("Usuario");
            Log.w("Usuario-> ", usuario);

            label_usuario.setText("Usuario:      " + usuario);
        }

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("Usuario", usuario);
                //Toast.makeText(ListaPeliculas.this, "Usuario: " + usuario, Toast.LENGTH_SHORT).show();
                intent.setClass(ListaPeliculas.this, InsertaPeliculas.class);
                startActivity(intent);
            }
        });
        BD = new DBPeliculas(this);
        BD.abre();
        cargalistaPeliculas(usuario);
    }

    public void cargalistaPeliculas(String usuario){
        array_peliculas = new ArrayList<Pelicula>();
        Cursor c = BD.obtenerPeliculas(usuario);
        if(c == null || c.getCount() == 0){
            Toast.makeText(this, "Tabla vacía", Toast.LENGTH_SHORT).show();
        }else{
            int i = 0;
            if (c.moveToFirst()){
                do{
                    array_peliculas.add(new Pelicula(c.getString(0), c.getString(1)));
                    i++;
                }while(c.moveToNext());
                Toast.makeText(this, "Número de filas: " + array_peliculas.size(), Toast.LENGTH_SHORT).show();
            }
        }
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorPeliculas(array_peliculas);
        recycler.setAdapter(adaptador);
    }
}
