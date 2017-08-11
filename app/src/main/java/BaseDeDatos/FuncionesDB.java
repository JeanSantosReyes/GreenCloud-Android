package BaseDeDatos;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.mand.myapplication.ConfiguracionInvernaderos;

import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Model.Invernadero;
import Model.Sector;

public class FuncionesDB {
    private Context context;
    private int version;
    //EN ESTA VARIABLE SE GUARDA EL ID DE UN INVERNADERO POR SI SE REQUIRE ELIMAR ALGUNAS VEZ LOS SECTORES DEL INVERNADERO
    private int idInvernadero;
    public FuncionesDB(Context context,int version){
        this.version = version;
        this.context = context;
    }
    public int login(String user,String password) throws Exception{
        sqlite bh = new sqlite(context,"user",null,version);
        password = encrypt(password);

        SQLiteDatabase db = bh.getReadableDatabase();

       Cursor c = db.rawQuery("SELECT * FROM user WHERE username = '" + user + "' OR email = '" + user + "' AND password = '" + password + "'", null);

        int id = 0;
        if(c.moveToFirst()){
            id = c.getInt(0);
        }

       return id;
    }
    public long registerUsuario(String correo, String usuario, String passwords) throws Exception{
        sqlite bh = new sqlite(context,"user",null,version);
       long i = 0;
        if(bh!=null){
            SQLiteDatabase db = bh.getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("username",usuario);
            cv.put("email",correo);
            cv.put("password", encrypt(passwords));

            i =  db.insert("user", null, cv);
        }
        return i;
    }
    //GUARDANDO EL INVERNADERO
    public void guardarInvernadero(ArrayList<String> invernaderos){
        checkLogin();
        int idUser = getIdUser();
        String usename = preferences.getString("UserEmail", null).toString();
        String password = preferences.getString("pass", null).toString();
        Toast.makeText(context,""+invernaderos.size(),Toast.LENGTH_SHORT).show();
        int start = countInvernaderos();
        for (int i = start;i<invernaderos.size()+start;i++){
            sqlite bh = new sqlite(context,"invernadero",null,version);
            SQLiteDatabase db = bh.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("name","invernadero "+(i+1));
            cv.put("id_user",idUser);
            cv.put("img","null");
            db.insert("invernadero", null, cv);
        }

    }
    public ArrayList<String> getSectoresByInvernadero(int idInvernadero){
        checkLogin();
        sqlite bh = new sqlite(context,"sector",null,version);
        SQLiteDatabase db = bh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM sector WHERE id_invernadero = " + idInvernadero, null);
        ArrayList<String> salida = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                salida.add(c.getString(1));
            }while(c.moveToNext());
        }
        return salida;
    }


    public int getIdUser(){
        preferences = context.getSharedPreferences(prefence, MODE);
        int idUser = preferences.getInt("id", 0);
        return idUser;
    }
    public ArrayList<Invernadero> getInvernaderoByIdUser(int iduser){
        sqlite bh = new sqlite(context,"invernadero",null,version);
        SQLiteDatabase db = bh.getReadableDatabase();

        ArrayList<Invernadero> invernaderos = new ArrayList<Invernadero>();
        Cursor c = db.rawQuery("SELECT * FROM invernadero WHERE id_user = " + iduser, null);
        if(c.moveToFirst()){
            do {
                Log.d("maickol12",c.getString(1)+" "+c.getString(0));
                invernaderos.add(new Invernadero(c.getInt(0),c.getString(1),c.getString(3),c.getInt(3)));
            }while(c.moveToNext());
        }
        return invernaderos;
    }
    //GUARDAR INVERNADEROS EN LA BASE DE DATOS
    public void guardarSectores(ArrayList<String> sectores,int id_invernadero){
        sqlite bh = new sqlite(context,"sector",null,version);


        int i = 0;
        int contador = 2;
        int vueltas = 0;
        for (String nombre : sectores){
            SQLiteDatabase db = bh.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("nombre",nombre);
            cv.put("id_invernadero",id_invernadero);
            cv.put("posicion_x",i);
            cv.put("posicion_y",(contador%2));
            Log.d("michael",i+" "+(contador%2)+" SECTOR "+id_invernadero);
            contador++;
            vueltas++;
            if(vueltas==2){
                vueltas = 0;
                i++;
            }
            db.insert("sector",null,cv);


        }
        comp();

    }
    public void comp(){
        sqlite bh = new sqlite(context,"sector",null,version);
        SQLiteDatabase db = bh.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM sector",null);
        if(c.moveToFirst()){
            do{
                Log.d("maka",""+c.getString(4));
            }while(c.moveToNext());
        }
    }
    //OBTENER SECTORES POR ID DE INVERNADERO
    public void obtenerSectoresById(int id){
        checkLogin();
        ArrayList<Sector> sectores = new ArrayList<>();

        sqlite bh = new sqlite(context,"sector",null,version);
        SQLiteDatabase db = bh.getReadableDatabase();


        Cursor c = db.rawQuery("SELECT * FROM sector WHERE idsector = "+id,null);

        if(c.moveToFirst()){
            do {
                sectores.add(new Sector(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getInt(4)));
            }while(c.moveToNext());
        }
    }
    //FUNCIONES PARA ENCRIPTAR Y DEDENCRIPTAR UNA PASSWORD
    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78";

    public static String encrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(FuncionesDB.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
        return encryptedValue64;

    }
    public void logout(){
        preferences = context.getSharedPreferences(prefence, MODE);
        editor = preferences.edit();

        editor.clear();
        editor.commit();
    }
    public static String decrypt(String value) throws Exception
    {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(FuncionesDB.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;

    }

    private static Key generateKey() throws Exception
    {
        Key key = new SecretKeySpec(FuncionesDB.KEY.getBytes(),FuncionesDB.ALGORITHM);
        return key;
    }
    String prefence = "LOGIN";
    int  MODE = 1;
    //FUNCION PARA VER SI EL USUARIO ESTA LOGUEADO
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public void checkLogin(){
        preferences = context.getSharedPreferences(prefence, MODE);
        editor = preferences.edit();

        if(preferences.getBoolean("islog",false)){
            Toast.makeText(context,"Estas logueado ",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"No esta logueado ",Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteInvernaderos(boolean flag){
        checkLogin();

        //OBTENIENDO EL ID DEL USUARIO EN LINEA
        int idUsuario = getIdUser();
        if (flag) {
            sqlite bh = new sqlite(context,"invernadero",null,version);
            SQLiteDatabase db = bh.getWritableDatabase();
            db.delete("invernadero", "id_user = " + idUsuario, null);
        }else{
            confirmacion("Eliminacion de invernaderos", "Cuidado", 1);
        }
    }
    //FUNCION PARA ELIMINAR TODOS LOS SECTORES DEL INVERNADERO
    public void deleteSelectoresByInvernadero(int idInvernadero,boolean flag){
        this.idInvernadero = idInvernadero;
        if(flag){
            sqlite bh = new sqlite(context,"sector",null,version);
            SQLiteDatabase db = bh.getWritableDatabase();
            db.delete("sector", "id_invernadero=" + idInvernadero, null);
        }else{
            confirmacion("Eliminacion de sectores", "Cuidado",0);
        }
    }

    //ESTA FUNCION MUESTRA UNA VENTANA DE CONFIRMACION
    public void confirmacion(String mensaje, final String titulo, final int opcion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Estoy de acuerdo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               switch (opcion){
                   case 0:
                       deleteSelectoresByInvernadero(idInvernadero,true);
                       break;
                   case 1:
                       deleteInvernaderos(true);
                       break;
               }
                Toast.makeText(context,"Los "+titulo+" se han eliminado...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ConfiguracionInvernaderos.class);
                context.startActivity(intent);
            }
        });
        builder.show();
    }

    //METODO PARA SACAR TODOS LOS INVERNADEROS POR USUARIO
    public ArrayList<String> getInvernaderosById(){
        ArrayList<String> lista = new ArrayList<>();
        //SACAMOS EL ID DEL USUARIO EN LINEA
        int idUser = getIdUser();

        sqlite bh = new sqlite(context,"invernadero",null,version);
        SQLiteDatabase db = bh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM invernadero WHERE id_user = "+idUser,null);

        if(c.moveToFirst()){
            do{
                lista.add(c.getString(1));
            }while(c.moveToNext());
        }

        return lista;
    }

    //METODO PARA CONTAR TODOS INVERNADEROS
    public int countInvernaderos(){
        //SACAMOS EL ID DEL USUARIO EN LINEA
        int idUser = getIdUser();
        sqlite bh = new sqlite(context,"invernadero",null,version);
        SQLiteDatabase db = bh.getReadableDatabase();

        int count = db.rawQuery("SELECT * FROM invernadero WHERE id_user = "+idUser,null).getCount();

        return count;
    }

}
