package BaseDeDatos;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.security.Key;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FuncionesDB {
    private Context context;
    private int version;
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
        for (int i = 0;i<invernaderos.size();i++){
            sqlite bh = new sqlite(context,"invernadero",null,version);
            SQLiteDatabase db = bh.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("name","invernadero "+(i+1));
            cv.put("id_user",idUser);
            db.insert("invernadero", null, cv);
        }

    }
    public void deleteInvernaderosById(int id){
        checkLogin();
        sqlite bh = new sqlite(context,"invernadero",null,version);
        SQLiteDatabase db = bh.getWritableDatabase();



    }
    public int getIdUser(){
        preferences = context.getSharedPreferences(prefence, MODE);
        int idUser = preferences.getInt("id", 0);
        return idUser;
    }
    public ArrayList<String> getInvernaderoByIdUser(int iduser){
        sqlite bh = new sqlite(context,"invernadero",null,version);
        SQLiteDatabase db = bh.getReadableDatabase();

        ArrayList<String> invernaderos = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT * FROM invernadero WHERE id_user = "+iduser,null);
        if(c.moveToFirst()){
            do {
                Log.d("maickol12",c.getString(1)+" "+c.getString(0));
                invernaderos.add(c.getString(1));
            }while(c.moveToNext());
        }
        return invernaderos;
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


}
