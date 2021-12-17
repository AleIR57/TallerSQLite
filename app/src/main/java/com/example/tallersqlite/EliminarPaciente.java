package com.example.tallersqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EliminarPaciente extends AppCompatActivity {
    Spinner NombrePacientes;
    ArrayList<String> opciones;
    String resultado = "";
    String nombrePaciente;
    String apellidoPaciente;
    int cedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_paciente);
        NombrePacientes = findViewById(R.id.spinnerNombres);
        opciones = new ArrayList<String>();
        opciones.add("Seleccione un paciente");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM pacientes", null);
        int numRows = (int) DatabaseUtils.longForQuery(bd, "SELECT COUNT(*) FROM pacientes", null);
        while(fila.moveToNext() != false){
            cedula = fila.getInt(0);
            nombrePaciente = fila.getString(1);
            apellidoPaciente = fila.getString(2);
            resultado = String.valueOf(cedula) + ". " + nombrePaciente + " " + apellidoPaciente + "\n";
            opciones.add(resultado);

        }
        bd.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_primero, opciones);
        NombrePacientes.setAdapter(adapter);
    }

    public void Eliminar(View v){
        String seleccion = NombrePacientes.getSelectedItem().toString();
        String[] posicionSeleccion = seleccion.split("\\.");
        String idPosicion = posicionSeleccion[0];


        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int cant = bd.delete("pacientes","cedula="+idPosicion,null);
        if(cant==1){
            Toast.makeText(this,"Se eliminó con éxito al paciente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"No se pudo eliminar al paciente", Toast.LENGTH_SHORT).show();
        }
        bd.close();


    }

    public void inicio(View v){
        Intent vInicio = new Intent(this, MainActivity.class);
        startActivity(vInicio);
    }

}