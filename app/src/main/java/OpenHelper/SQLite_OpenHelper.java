package OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLite_OpenHelper extends SQLiteOpenHelper {

    public SQLite_OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query= "create table usuarios(_ID integer primary key autoincrement, Nombre text, Correo text, Password text);";
        sqLiteDatabase.execSQL(query);
        String query2= "create table parqueos(_ID integer primary key autoincrement, NroMatricula text, Tiempo text, Usuario_ID integer, foreign key(Usuario_ID) references usuarios(_ID));";
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //metodo que permite abrir la bd
    public void abrir( ){


        this.getWritableDatabase();


    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    //metdodo que permite cerrar la bd
    public void cerrar(){
        this.close();
    }

    //metodo que permite insertar registros en la tabla usuarios
    public void insertarReg(String nom, String cor, String pas){
        ContentValues valores=new ContentValues();
        valores.put("Nombre", nom);
        valores.put("Correo", cor);
        valores.put("Password", pas);
        this.getWritableDatabase().insert("usuarios", null, valores);
    }

    public boolean validarUsuario(String nombre, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT Password FROM usuarios WHERE Nombre = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombre});


        if (cursor.moveToFirst()) {
            String storedPassword = cursor.getString(0);
            cursor.close();
            return storedPassword.equals(password);
        }


        cursor.close();
        return false;
    }
    public boolean validarNombreUsuario(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT Nombre FROM usuarios WHERE Nombre = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nombre});


        if (cursor.moveToFirst()) {
            String NombreGuardado = cursor.getString(0);
            cursor.close();
             NombreGuardado.equals(nombre);
             return false;
        }


        cursor.close();
        return true;
    }

    //metodo que permite insertar registros en la tabla usuarios
    public void insertarParq(String nro, String tiem, int id){
        ContentValues valores=new ContentValues();
        valores.put("NroMatricula", nro);
        valores.put("Tiempo", tiem);
        valores.put("Usuario_ID", id);
        this.getWritableDatabase().insert("parqueos", null, valores);
    }


    public boolean validarMatricula(String matricula) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT NroMatricula FROM parqueos WHERE NroMatricula = ?";
        Cursor cursor = db.rawQuery(query, new String[]{matricula});


        if (cursor.moveToFirst()) {
            String MatriculaGuardada = cursor.getString(0);
            cursor.close();
            MatriculaGuardada.equals(matricula);
            return false;
        }


        cursor.close();
        return true;
    }
}
