package tk.developeramit.sqlite_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by developeramit on 14/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Students.db";
    private static final String TABLE_NAME = "Students";

    private int res;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE "+TABLE_NAME+" (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,username TEXT,password TEXT)";
        db.execSQL(queryCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String quertDropTableIfExits = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(quertDropTableIfExits);
        onCreate(db);
    }

    public boolean insertData(String tableName,ContentValues content)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        long res = db.insert(tableName,null,content);

        if(res != -1)
            return true;
        else
            return false;
    }

    public Cursor showAllData(String selectQuery,String[] selectionArgs){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,selectionArgs);

        return cursor;
    }

    public boolean updateData(String tableName,String id,ContentValues contentValues){
        SQLiteDatabase db = this.getWritableDatabase();

        try{
            res = db.update(tableName,contentValues,"id = ?", new String[] {id});
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("#ERROR: ",""+e);
        }

        if(res > 0)
            return true;
        else
            return false;
    }
}
