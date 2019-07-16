package hu.technocool.todolist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hu.technocool.todolist.R;
import hu.technocool.todolist.model.ToDo;

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    private int resource;

    public ToDoAdapter(Context context, int resource, List<ToDo> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ToDo td = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvTime = convertView.findViewById(R.id.tvTime);

        if (td.getUrgent().equals("NAGYON FONTOS")) {
            convertView.setBackgroundColor(Color.parseColor("#CC0000"));
        } else if (td.getUrgent().equals("FONTOS")) {
            convertView.setBackgroundColor(Color.parseColor("#D66A00"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#008D00"));
        }

        tvName.setText(td.getName());
        tvTime.setText(td.getTime());

        return convertView;
    }
}
