package com.example.filmoteca;

import android.bluetooth.BluetoothA2dp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBPeliculas {
    /* Estructura Tabla */
    public static final String CAMPO_ID = "ID";
    public static final String CAMPO_NOMBRE = "NOMBRE";
    public static final String CAMPO_PUBLICACION = "DENOMINACION_ORIGEN";
    public static final String CAMPO_NOMBRE_USUARIO = "USUARIO";
    public static final String TAG = "BD_PELICULAS";


    /* Datos Base datos */
    public static final String BD_NOMBRE = "Base_Datos_Peliculas";
    public static final String BD_NOMBRE_TABLA = "PELICULAS";
    public static final Integer BD_VER = 2;


    public static final String BD_CREATE =
            "create table " + BD_NOMBRE_TABLA + " ("
                    + CAMPO_ID + " integer primary key autoincrement, "
                    + CAMPO_NOMBRE_USUARIO + " text not null, "
                    + CAMPO_NOMBRE + " text not null, "
                    + CAMPO_PUBLICACION + " text not null"
                    +")";

    private final Context contexto;
    private AyudaBD ayuda;
    private SQLiteDatabase BD;

    public DBPeliculas(Context con){
        this.contexto = con;
        Log.w(TAG, "creando ayuda...");
        ayuda = new AyudaBD(contexto);
    }

    public DBPeliculas abre () throws SQLException{
        Log.w(TAG, " abriendo base de datos");
        BD = ayuda.getWritableDatabase();
        return this;
    }

    public void cierra(){
        ayuda.close();
    }

    public Cursor obtenerPeliculas(String usuario){
        return BD.query(BD_NOMBRE_TABLA, new String[]
                {CAMPO_NOMBRE, CAMPO_PUBLICACION},
                CAMPO_NOMBRE_USUARIO + "=?",new String[]{usuario}, null, null, null, null);
    }

    /* Acciones que se pueden llevar a cabo contra la base de datos. */

    /* Insercion de una pelicula */
    public long insertaPelicula(String nombre, String publicacion, String usuario){
        ContentValues valores = new ContentValues();
        valores.put(CAMPO_NOMBRE, nombre);
        valores.put(CAMPO_PUBLICACION, publicacion);
        valores.put(CAMPO_NOMBRE_USUARIO, usuario);

        Log.w("DATOS-OBTENIDOS->  ","Nombre: " + nombre);
        Log.w("DATOS-OBTENIDOS->  ", "Publicación: " + publicacion);
        Log.w("DATOS-OBTENIDOS->  ", "Usuario: " + usuario);

        return BD.insert(BD_NOMBRE_TABLA, null, valores);
    }

    /* Borrar una pelicula */
    public void borrarPelicula(int id, String usuario){
        String CADENA = "DELETE FROM " + BD_NOMBRE_TABLA + " WHERE " + CAMPO_ID + " = " + id + " AND " + CAMPO_NOMBRE_USUARIO + " = '" + usuario + "' ";
        BD.execSQL(CADENA);
    }

    /* Actualizar un registro */
    public void actualizaPelicula(int id, String nombre, String publicacion, String usuario){
        Log.w("DATOS-OBTENIDOS->  ","ID: " + id);
        Log.w("DATOS-OBTENIDOS->  ","Nombre: " + nombre);
        Log.w("DATOS-OBTENIDOS->  ", "Publicación: " + publicacion);
        Log.w("DATOS-OBTENIDOS->  ", "Usuario: " + usuario);

        /*
        BD.execSQL("UPDATE "+ BD_NOMBRE_TABLA + " SET " + CAMPO_NOMBRE + " = '" + nombre + "' where " + CAMPO_`PUBLICACION` + " = '"+ denominacion+ "'");
         */

        BD.execSQL("UPDATE " + BD_NOMBRE_TABLA + " SET " + CAMPO_NOMBRE + " = " + nombre + " WHERE "+ CAMPO_ID + " = " + id + " AND " + CAMPO_NOMBRE_USUARIO + " = '" + usuario + "' ");
        BD.execSQL("UPDATE " + BD_NOMBRE_TABLA + " SET " + CAMPO_PUBLICACION + " = " + publicacion + " WHERE "+ CAMPO_ID + " = " + id + " AND " + CAMPO_NOMBRE_USUARIO + " = '" + usuario + "' ");
    }

    /* Obtener datos de los registros */
    public String getNombre(String id){
        String nombre;
        Cursor c = BD.query(BD_NOMBRE_TABLA, new String[]{CAMPO_NOMBRE},
                CAMPO_ID + " =?",new String[]{id}, null, null, null);
        c.moveToFirst();
        nombre = c.getString(0);
        return nombre;
    }

    public String getPublicacion(String id){
        String publicacion;
        Cursor c = BD.query(BD_NOMBRE_TABLA, new String[]{CAMPO_PUBLICACION},
                CAMPO_ID + " =?",new String[]{id}, null, null, null);
        c.moveToFirst();
        publicacion = c.getString(0);
        return publicacion;
    }

    public class AyudaBD extends SQLiteOpenHelper{
        public AyudaBD(Context con) {
            super(con, BD_NOMBRE, null, BD_VER);
            Log.w(TAG, "contructor de ayuda");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                Log.w(TAG, "creando base de datos " + BD_CREATE);
                db.execSQL(BD_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Actualizando base de datos de la version " + oldVersion + " a la versión " + newVersion + ". Todos los datos serán borrados.");
            BD.execSQL("DROP TABLE IF EXISTS " + BD_NOMBRE_TABLA);
            onCreate(BD);
        }
    }
}
