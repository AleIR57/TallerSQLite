package com.example.tallersqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AgregarPaciente extends AppCompatActivity {

    EditText e1,e2,e3;
    Spinner s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_paciente);
        e1 = findViewById(R.id.EditTextNombre);
        e2 = findViewById(R.id.EditTextApellido);
        e3 = findViewById(R.id.EditTextEps);


        s1 = findViewById(R.id.spinnerSintoma);


        String [] opciones = {"Cuadro neurovegetativos", "Trastornos de conciencia", "Signos de deshidratación", "Sepsis","Patologías agudas cardiovascular neurológica"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_primero, opciones);
        s1.setAdapter(adapter);

    }

    public void insertar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int numRows = (int) DatabaseUtils.longForQuery(bd, "SELECT COUNT(*) FROM pacientes", null);
        String nombre = e1.getText().toString();
        String apellido = e2.getText().toString();
        String eps = e3.getText().toString();
        String sintoma = s1.getSelectedItem().toString();
        ContentValues registro = new ContentValues();
        registro.put("cedula", numRows+1);
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("eps", eps);
        registro.put("sintoma", sintoma);
        registro.put("nivelglucemia", 0.0);
        registro.put("crdiabetico", "No presenta");
        bd.insert("pacientes", null, registro);
        bd.close();
        e1.setText("");
        e2.setText("");
        e3.setText("");
        Toast.makeText(this, "El paciente fue creado correctamente", Toast.LENGTH_SHORT).show();
        Intent vExamen = new Intent(this, ExamenGlicemia.class);
        startActivity(vExamen);

    }

    public void inicio(View v){
        Intent vInicio = new Intent(this, MainActivity.class);
        startActivity(vInicio);
    }



}