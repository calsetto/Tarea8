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

public class Adapter_buscar extends ArrayAdapter<Users> {

    Context context;
    List<Users> arrayListUsers;

    public Adapter_buscar(@NonNull Context context, List<Users>arrayListUsers) {
        super(context, R.layout.list_item_buscar, arrayListUsers);
        this.context = context;
        this.arrayListUsers = arrayListUsers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buscar, null, true);
        TextView fecha = (TextView)view.findViewById(R.id.fecha_1);
        TextView record = (TextView)view.findViewById(R.id.record_1);

        fecha.setText(arrayListUsers.get(position).getFecha());
        record.setText(arrayListUsers.get(position).getRecordatorio());
        return view;

    }
}
