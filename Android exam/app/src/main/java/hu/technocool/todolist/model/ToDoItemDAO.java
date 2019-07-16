package hu.technocool.todolist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemDAO {

    private ToDoDBHelper helper;
    private SQLiteDatabase db;


    public ToDoItemDAO(Context context) {
        this.helper = new ToDoDBHelper(context);
    }

    public List<ToDo> getAllToDo() {
        db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM todoitem", null);

        cursor.moveToFirst();
        List<ToDo> items = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
           // String done = cursor.getString(cursor.getColumnIndex("done"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String urgent = cursor.getString(cursor.getColumnIndex("urgent"));

            ToDo td = new ToDo(id, name, time, urgent);
            items.add(td);

            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return items;
    }

    public void deleteToDoItem(ToDo td) {
        db = helper.getWritableDatabase();
        db.delete("todoitem", "_id=?", new String[]{td.getId() + ""});
        db.close();
    }

    public void saveToDoItem(ToDo td) {
        db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", td.getName());
        cv.put("time", td.getTime());
        cv.put("urgent",td.getUrgent());
        if (td.getId() == -1) {
            long id = db.insert("todoitem", null, cv);
            td.setId((int) id);
        } else {
            db.update("todoitem", cv, "_id=?", new String[]{td.getId() + ""});
        }
        db.close();
    }

    public void deleteAllToDoItem() {
        db = helper.getWritableDatabase();

        db.delete("todoitem", "", null);
        db.execSQL("DELETE FROM todoitem");
        db.close();
    }

}
