package com.example.filmoteca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity{

    ImageView imagen;
    String URL="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ_LeINXwS3DJEy58TenDLGyDJ-pW7wO4eLUooPuPajoNDY35Ov&usqp=CAU";
    EditText usuario;
    Button btn_aceptar;
    DBPeliculas BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagen = (ImageView) findViewById(R.id.imagen_login);
        usuario = (EditText) findViewById(R.id.txt_usuario_inicio);
        btn_aceptar = (Button) findViewById(R.id.btn_aceptar_inicio);

         /* Carga la imagen */
        CargaImagen ci = new CargaImagen();
        ci.execute(URL);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre_usuario = usuario.getText().toString();

                if(nombre_usuario.length()== 0 || nombre_usuario.equalsIgnoreCase(" ")){
                    Toast.makeText(MainActivity.this, "Debes introducir el nombre de usuario!", Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(MainActivity.this, "Boton aceptar pulsado, Usuario: " + nombre_usuario, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, ListaPeliculas.class);
                    intent.putExtra("Usuario", nombre_usuario);
                    startActivity(intent);
                }
            }
        });

        BD = new DBPeliculas(this);
        BD.abre();
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
    public void onResume(){
        super.onResume();
        BD.abre();
    }

    public void onStop(){
        super.onStop();
        BD.cierra();
    }
}


