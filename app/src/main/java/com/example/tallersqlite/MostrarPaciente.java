package com.example.tallersqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MostrarPaciente extends AppCompatActivity {
    TextView t1;
    String resultado = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_paciente);
        t1 = findViewById(R.id.TextViewMostrarPacientes);
        int cedula;
        String nombrePaciente;
        String apellidoPaciente;
        String eps;
        String sintoma;
        float nivelglucemia;
        String crdiabetico;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM pacientes",null);

        while(fila.moveToNext() != false){
            cedula = fila.getInt(0);
            nombrePaciente = fila.getString(1);
            apellidoPaciente = fila.getString(2);
            eps = fila.getString(3);
            sintoma = fila.getString(4);
            nivelglucemia = fila.getFloat(5);
            crdiabetico = fila.getString(6);

            resultado += "Cédula: "+ cedula + ", Nombre:  " + nombrePaciente + " " + apellidoPaciente + ", EPS: " + eps + ", Síntoma: " + sintoma + ", Nivel de Glucemia: " + nivelglucemia + ", Cuadro diabético: " + crdiabetico +"\n"+"\n";

        }
        t1.setText(resultado);
    }

    public void inicio(View v){
        Intent vInicio = new Intent(this, MainActivity.class);
        startActivity(vInicio);
    }




}