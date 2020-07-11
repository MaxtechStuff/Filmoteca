package com.example.filmoteca;

public class Pelicula {
    String nombre, publicacion;

    public Pelicula(){

    }

    public Pelicula(String nombre, String publicacion){
        this.nombre = nombre;
        this.publicacion = publicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPublicacion() {
        return publicacion;
    }
}
