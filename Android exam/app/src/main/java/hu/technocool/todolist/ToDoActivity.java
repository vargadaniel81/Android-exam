package hu.technocool.todolist;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hu.technocool.todolist.model.ToDo;

public class ToDoActivity extends AppCompatActivity {

    private Spinner spinner;
    private DatePickerDialog picker;
    private TextView tvw;

    private Intent intent;
    private ToDo td;
    private EditText etUrgent;
    private EditText etName;
    private EditText etTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        etName = findViewById(R.id.etName);
        spinner = findViewById(R.id.spinner);
        etUrgent = findViewById(R.id.etUrgent);
        tvw = (TextView) findViewById(R.id.textView1);
        etTime = (EditText) findViewById(R.id.etTime);
        etTime.setInputType(InputType.TYPE_NULL);

        intent = getIntent();

        td = (ToDo) intent.getSerializableExtra("td");


        if (td != null) {
            etName.setText(td.getName());
            etUrgent.setText(td.getUrgent());
            etTime.setText(td.getTime());
        }


        String[] cat = {"NAGYON FONTOS", "FONTOS", "NEM FONTOS"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.spinner_item, cat);

        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        if (td != null) {
            if (td.getUrgent().equals("NAGYON FONTOS")) {
                spinner.setSelection(0);
            } else if (td.getUrgent().equals("FONTOS")) {
                spinner.setSelection(1);
            } else {
                spinner.setSelection(2);
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        etUrgent.setText("NAGYON FONTOS");
                        break;
                    case 1:
                        etUrgent.setText("FONTOS");
                        break;
                    case 2:
                        etUrgent.setText("NEM FONTOS");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker = new DatePickerDialog(ToDoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                String m = String.valueOf(month + 1);
                                String d = String.valueOf(day);

                                if (month < 9) {
                                    m = "0" + m;
                                }
                                if (day < 10) {
                                    d = "0" + d;
                                }
                                etTime.setText(year + "." + m + "." + d);

                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    public void megsem(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void mentes(View view) {
        if (td == null) {
            td = new ToDo();
        }

        if ((etName.getText().length() < 1) && (etTime.getText().length() < 12)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Hiányos kitöltés")
                    .setMessage("A feladat menüpontot nem töltötted ki, biztosan akarod menteni?")
                    .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    td.setName(etName.getText().toString());
                    td.setUrgent(etUrgent.getText().toString());
                    td.setTime(etTime.getText().toString());

                    intent.putExtra("td", td);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            AlertDialog ad = builder.create();
            ad.show();
        } else if (etTime.getText().length() > 11) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Hiányos kitöltés")
                    .setMessage("A dátum menüpontot nem töltötted ki, biztosan akarod menteni?")
                    .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    td.setName(etName.getText().toString());
                    td.setUrgent(etUrgent.getText().toString());
                    td.setTime("");

                    intent.putExtra("td", td);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            AlertDialog ad = builder.create();
            ad.show();
        } else {
            td.setName(etName.getText().toString());
            td.setUrgent(etUrgent.getText().toString());
            td.setTime(etTime.getText().toString());

            intent.putExtra("td", td);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
