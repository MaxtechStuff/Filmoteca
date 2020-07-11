package com.example.filmoteca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class InsertaPeliculas extends AppCompatActivity {
    EditText nombre, publicacion;
    ImageView imagen;
    Button insertar;
    DBPeliculas BD;
    String URL = "https://image.freepik.com/vector-gratis/dibujos-animados-cine-cine-festival-pelicula-diseno_24877-44228.jpg";
    String usuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inserta_peliculas);

        nombre = (EditText) findViewById(R.id.txt_nombre_inserta);
        publicacion = (EditText) findViewById(R.id.txt_publicacion_inserta);
        imagen = (ImageView) findViewById(R.id.imagen_inserta);
        insertar = (Button) findViewById(R.id.btn_añadir_insertar);



        CargaImagen ci = new CargaImagen();
        ci.execute(URL);

        Intent intent = getIntent();
        if(intent != null){
            usuario = intent.getStringExtra("Usuario");
            //Toast.makeText(this, "Usuario obtenido: " + usuario, Toast.LENGTH_SHORT).show();
        }

        BD = new DBPeliculas(this);
        BD.abre();

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n, p;
                n = nombre.getText().toString();
                p = publicacion.getText().toString();
                Log.w("APP-INFO", "NOMBRE: " + n);
                Log.w("APP-INFO", "PUBLICACION: " + p);
                Log.w("APP-INFO", "USUARIO: " + usuario);

                /* nombre, publicacion, usuario*/
                //BD.insertaPelicula(n, p, usuario);
                if (BD.insertaPelicula(n,p,usuario)==-1){

                    Toast.makeText(getBaseContext(),"Error en la inserción", Toast.LENGTH_LONG).show();

                }
                Intent inte = new Intent();
                inte.setClass(InsertaPeliculas.this, ListaPeliculas.class);
                inte.putExtra("Usuario", usuario);
                startActivity(inte);
            }
        });
    }
    /* Clase para cargar la imagen */
    public class CargaImagen extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... param){
            Bitmap bitmap = null;
            try{
                InputStream input = new java.net.URL(param[0]).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result){
            imagen.setImageBitmap(result);
        }
    }
}
