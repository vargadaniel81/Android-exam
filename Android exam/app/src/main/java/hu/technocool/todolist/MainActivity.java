package hu.technocool.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.technocool.todolist.adapter.ToDoAdapter;
import hu.technocool.todolist.model.ToDo;
import hu.technocool.todolist.model.ToDoItemDAO;

public class MainActivity extends AppCompatActivity {

    private static final int REQC_NEW = 1;
    private static final int REQC_EDIT = 2;
    private List<ToDo> items;
    private ToDoAdapter adapter;
    private ToDoItemDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
                startActivityForResult(intent, REQC_NEW);
            }
        });

        dao = new ToDoItemDAO(this);
        items = dao.getAllToDo();

        adapter = new ToDoAdapter(this, R.layout.listview_item, items);

        ListView lvItems = findViewById(R.id.lvToDoItems);
        lvItems.setAdapter(adapter);

        registerForContextMenu(lvItems);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        ToDo td = adapter.getItem(info.position);

        switch (item.getItemId()) {
            case R.id.mi_edit:
                Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
                intent.putExtra("td", td);
                intent.putExtra("index", info.position);
                startActivityForResult(intent, REQC_EDIT);

                return true;

            case R.id.mi_remove:
                dao.deleteToDoItem(td);
                adapter.remove(td);
                Toast.makeText(this, "Feladat törölve", Toast.LENGTH_LONG).show();

                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mi_newItem) {
            Intent intent = new Intent(getApplicationContext(), ToDoActivity.class);
            startActivityForResult(intent, REQC_NEW);
            return true;
        } else if (id == R.id.mi_removeAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Törlési megerősítés")
                    .setMessage("Biztos, hogy törölni akarsz minden feladatot?")
                    .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dao.deleteAllToDoItem();
                    adapter.clear();
                }
            });
            AlertDialog ad = builder.create();
            ad.show();

            Toast.makeText(this, "Összes feladat törlése", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            ToDo td = (ToDo) data.getSerializableExtra("td");
            if (requestCode == REQC_NEW) {
                adapter.add(td);
                dao.saveToDoItem(td);
            } else if (requestCode == REQC_EDIT) {
                int index = data.getIntExtra("index", -1);
                items.set(index, td);
                dao.saveToDoItem(td);
                adapter.notifyDataSetChanged();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
