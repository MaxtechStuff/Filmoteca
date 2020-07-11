package com.example.filmoteca;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorPeliculas extends RecyclerView.Adapter<AdaptadorPeliculas.ViewHolder>    {

    ArrayList<Pelicula> misfilas;

    public AdaptadorPeliculas(ArrayList<Pelicula>  datosEnviados) {
        misfilas=datosEnviados;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peliculas, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.nombre.setText(misfilas.get(position).getNombre());
        holder.publicacion.setText(misfilas.get(position).getPublicacion());
    }


    @Override
    public int getItemCount() {
        return misfilas.size();
    }

    public void remove(Pelicula c){
        misfilas.remove(c);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView publicacion;
        View separador;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre =  itemView.findViewById(R.id.item_nombre);
            publicacion = itemView.findViewById(R.id.item_publicacion);
            separador = itemView.findViewById(R.id.item_separador);
        }
    }
}