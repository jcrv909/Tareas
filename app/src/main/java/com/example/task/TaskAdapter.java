package com.example.task;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private final List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        final Task task = getItem(position);

        TextView titleView = convertView.findViewById(R.id.title);
        titleView.setText(task.getTitle());

        CheckBox completedView = convertView.findViewById(R.id.completed);
        completedView.setChecked(task.isCompleted());
        completedView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setCompleted(isChecked);
            }
        });

        ImageButton deleteButton = convertView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el builder del AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // Establecer el título y el mensaje del diálogo
                builder.setTitle("Eliminar tarea");
                builder.setMessage("¿Estás seguro de que quieres eliminar esta tarea?");
                // Establecer el botón positivo (Sí) y su acción
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Eliminar la tarea de la lista y notificar al adaptador
                        tasks.remove(task);
                        notifyDataSetChanged();
                    }
                });
                // Establecer el botón negativo (No) y su acción
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancelar el diálogo sin hacer nada más
                        dialog.cancel();
                    }
                });
                // Crear y mostrar el diálogo a partir del builder
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return convertView;
    }

}