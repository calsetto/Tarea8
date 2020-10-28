package com.example.tarea8alterna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Adapter extends ArrayAdapter<Users> {
    Context context;
    List<Users>arrayListUsers;

    public Adapter(@NonNull Context context, List<Users>arrayListUsers) {
        super(context, R.layout.list_item,arrayListUsers);
        this.context = context;
        this.arrayListUsers = arrayListUsers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null, true);
        TextView fecha = (TextView)view.findViewById(R.id.fecha);
        TextView record = (TextView)view.findViewById(R.id.record);

        fecha.setText(arrayListUsers.get(position).getFecha());
        record.setText(arrayListUsers.get(position).getRecordatorio());
        return view;
    }
}
