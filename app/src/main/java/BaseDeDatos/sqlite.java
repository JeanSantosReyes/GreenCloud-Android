package BaseDeDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlite  extends SQLiteOpenHelper {
    private String MaximosMinimosTable = "CREATE TABLE MMTable(id INTEGER PRIMARY KEY AUTOINCREMENT,temperatura_maxima TEXT,temperatura_minima TEXT,variedad TEXT)";
    public sqlite(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MaximosMinimosTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MMTable;");
        onCreate(db);
    }
}
