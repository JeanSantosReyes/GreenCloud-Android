package BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlite  extends SQLiteOpenHelper {
    private String MaximosMinimosTable = "CREATE TABLE MMTable(id INTEGER PRIMARY KEY AUTOINCREMENT,temperatura_maxima TEXT,temperatura_minima TEXT,variedad TEXT,unidad TEXT)";

    private String UltimaVariedad = "CREATE TABLE UltimaVariedad(id INTEGER PRIMARY KEY AUTOINCREMENT,posicion TEXT,tipo TEXT,campo TEXT,hora TEXT,anio TEXT,mes TEXT,dia TEXT,valor TEXT)";

    private String users = "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT)";

    private String invenadero = "CREATE TABLE invernadero(idinverdero INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,img TEXT,id_user INTEGER)";

    private String sector = "CREATE TABLE sector(idsector INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, posicion_x TEXT, posicion_y TEXT, id_invernadero INTEGER)";

    private String variable = "CREATE TABLE variable(idvariable INTEGER PRIMARY KEY AUTOINCREMENT, variable_ambiental TEXT)";
    private String valor_variable = "CREATE TABLE valor_variable(idvalor_variable INTEGER PRIMARY KEY AUTOINCREMENT, id_variable INTEGER, id_sector INTEGER)";

    public sqlite(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MaximosMinimosTable);
        db.execSQL(UltimaVariedad);
        db.execSQL(users);
        db.execSQL(invenadero);
        db.execSQL(sector);
        db.execSQL(variable);
        db.execSQL(valor_variable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MMTable;");
        db.execSQL("DROP TABLE IF EXISTS UltimaVariedad;");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS invernadero");
        db.execSQL("DROP TABLE IF EXISTS sector");
        db.execSQL("DROP TABLE IF EXISTS variable");
        db.execSQL("DROP TABLE IF EXISTS valor_variable");
        onCreate(db);
    }
}
