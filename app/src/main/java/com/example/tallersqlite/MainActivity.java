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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   public void Agregar(View v){
       Intent vAgregar = new Intent(this, AgregarPaciente.class);
       startActivity(vAgregar);
   }

    public void MostrarPacientes(View v){
        Intent vMostrar = new Intent(this, MostrarPaciente.class);
        startActivity(vMostrar);
    }

    public void EliminarPacientes(View v){
        Intent vEliminar = new Intent(this, EliminarPaciente.class);
        startActivity(vEliminar);
    }

    public void EditarPacientes(View v){
        Intent vEditar = new Intent(this, EditarPaciente.class);
        startActivity(vEditar);
    }

    public void ExamenGlucemia(View v){
        Intent vExamen = new Intent(this, ExamenGlicemia.class);
        startActivity(vExamen);
    }





}