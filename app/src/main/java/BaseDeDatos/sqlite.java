package BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlite  extends SQLiteOpenHelper {

    private String business = "CREATE TABLE business(" +
            "id_business INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "direccion TEXT," +
            "rfc TEXT," +
            "cp INTEGER," +
            "is_active INTEGER)";

    private String users = "" +
            "CREATE TABLE user(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "username TEXT," +
            "email TEXT," +
            "password TEXT," +
            "user_type INTEGER," +
            "id_business INTEGER)";

    private String invernadero = "" +
            "CREATE TABLE invernadero(" +
            "idinverdero INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "img TEXT," +
            "id_user INTEGER," +
            "is_active INTEGER)";

    private String sector = "CREATE TABLE sector(" +
            "idsector INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "posicion_x TEXT," +
            "posicion_y TEXT," +
            "id_invernadero INTEGER," +
            "id_tipo_cultivo INTEGER," +
            "is_active INTEGER)";

    private String tipo_cultivo = "" +
            "CREATE TABLE tipo_cultivo(" +
            "idtipo_cultivo INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "descripcion TEXT," +
            "id_invernadero INTEGER)";

    private String variable = "" +
            "CREATE TABLE variable(" +
            "idvariable INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_sector INTEGER," +
            "id_tipo_variable INTEGER," +
            "is_active INTEGER)";

    private String tipo_variable = "CREATE TABLE tipo_variable(" +
            "idtipo_variable INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "descripcion TEXT)";


    private String MaximosMinimosTable =
            "CREATE TABLE MMTable(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "temperatura_maxima TEXT," +
                    "temperatura_minima TEXT," +
                    "variedad TEXT," +
                    "unidad TEXT)";

    private String UltimaVariedad = "" +
            "CREATE TABLE UltimaVariedad(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "posicion TEXT," +
            "tipo TEXT," +
            "campo TEXT," +
            "hora TEXT," +
            "anio TEXT," +
            "mes TEXT," +
            "dia TEXT," +
            "valor TEXT)";

    public sqlite(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MaximosMinimosTable);
        db.execSQL(UltimaVariedad);
        db.execSQL(users);
        db.execSQL(invernadero);
        db.execSQL(sector);
        db.execSQL(variable);
        db.execSQL(business);
        db.execSQL(tipo_cultivo);
        db.execSQL(tipo_variable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MMTable;");
        db.execSQL("DROP TABLE IF EXISTS UltimaVariedad;");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS invernadero");
        db.execSQL("DROP TABLE IF EXISTS sector");
        db.execSQL("DROP TABLE IF EXISTS variable");
        db.execSQL("DROP TABLE IF EXISTS business");
        db.execSQL("DROP TABLE IF EXISTS tipo_cultivo");
        db.execSQL("DROP TABLE IF EXISTS tipo_variable");
        onCreate(db);
    }
}
