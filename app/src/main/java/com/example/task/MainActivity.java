package com.example.task;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Task> tasks = new ArrayList<>();
    private TaskAdapter adapter;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.task_list);
        adapter = new TaskAdapter(this, tasks);
        listView.setAdapter(adapter);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            EditText editText = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Nueva tarea")
                    .setView(editText)
                    .setPositiveButton("Agregar", (dialog, which) -> {
                        String title = editText.getText().toString();
                        // Verificar si el EditText está vacío
                        if (isEmpty(editText)) {
                            // Mostrar un Toast con el mensaje de error al usuario
                            Toast.makeText(this, "Por favor ingrese un título para la tarea", Toast.LENGTH_SHORT).show();
                        } else {
                            // Crear y añadir la tarea a la lista
                            Task task = new Task(title);
                            tasks.add(task);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }
}