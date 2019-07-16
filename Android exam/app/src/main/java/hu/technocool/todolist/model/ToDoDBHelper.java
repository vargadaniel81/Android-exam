package hu.technocool.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;

    public ToDoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todoitem (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, time TEXT, urgent TEXT)");

        ContentValues cv = new ContentValues();
        cv.put("name", "Az alkalmazás használata: színek fontosság szerint");
        cv.put("time", "");
        cv.put("urgent", "FONTOS");
        db.insert("todoitem", null, cv);

        cv = new ContentValues();
        cv.put("name", "NEM FONTOS színű");
        cv.put("time", "");
        cv.put("urgent", "NEM FONTOS");
        db.insert("todoitem", null, cv);

        cv = new ContentValues();
        cv.put("name", "FONTOS színű");
        cv.put("time", "");
        cv.put("urgent", "FONTOS");
        db.insert("todoitem", null, cv);

        cv = new ContentValues();
        cv.put("name", "NAGYON FONTOS színű");
        cv.put("time", "");
        cv.put("urgent", "NAGYON FONTOS");
        db.insert("todoitem", null, cv);

        cv = new ContentValues();
        cv.put("name", "Az elemen hosszan nyomva további funkciók érhetők el");
        cv.put("time", "");
        cv.put("urgent", "FONTOS");
        db.insert("todoitem", null, cv);

        cv = new ContentValues();
        cv.put("name", "Új feladat hozzáadása a jobb alsó gombbal");
        cv.put("time", "");
        cv.put("urgent", "FONTOS");
        db.insert("todoitem", null, cv);

        cv = new ContentValues();
        cv.put("name", "Most már KEZDHETSZ: az Összes feladat törlésével, jobb felső gomb");
        cv.put("time", "");
        cv.put("urgent", "NEM FONTOS");
        db.insert("todoitem", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE todoitem");
        onCreate(db);
    }
}
