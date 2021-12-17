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

public class EditarPaciente extends AppCompatActivity {
    Spinner NombrePacientes;
    Spinner sintomas;
    ArrayList<String> opciones2;
    EditText e1, e2, e3, e4;
    String resultado = "";
    String nombrePaciente;
    String apellidoPaciente;
    int cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_paciente);
        e1 = findViewById(R.id.EditTextNombreEDITAR);
        e2 = findViewById(R.id.EditTextApellidoEDITAR);
        e3 = findViewById(R.id.EditTextEpsEDITAR);
        e4 = findViewById(R.id.EditTextNivelGEDITAR);

        sintomas = findViewById(R.id.spinnerSintomaEDITAR);
        String [] opciones = {"Cuadro neurovegetativos", "Trastornos de conciencia", "Signos de deshidratación", "Sepsis","Patologías agudas cardiovascular neurológica"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_primero, opciones);
        sintomas.setAdapter(adapter);

        NombrePacientes = findViewById(R.id.spinnerNombresEDITAR);
        opciones2 = new ArrayList<String>();
        opciones2.add("Seleccione un paciente");
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM pacientes", null);
        int numRows = (int) DatabaseUtils.longForQuery(bd, "SELECT COUNT(*) FROM pacientes", null);
        while(fila.moveToNext() != false){
            cedula = fila.getInt(0);
            nombrePaciente = fila.getString(1);
            apellidoPaciente = fila.getString(2);
            resultado = String.valueOf(cedula) + ". " + nombrePaciente + " " + apellidoPaciente + "\n";
            opciones2.add(resultado);

        }
        bd.close();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item_primero, opciones2);
        NombrePacientes.setAdapter(adapter2);

    }

    public void mostrarDatos(View v){
        String seleccion = NombrePacientes.getSelectedItem().toString();
        String[] posicionSeleccion = seleccion.split("\\.");
        String idPosicion = posicionSeleccion[0];
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery("SELECT * FROM pacientes WHERE cedula="+idPosicion,null);
        if(fila.moveToFirst()){
            e1.setText(fila.getString(1));
            e2.setText(fila.getString(2));
            e3.setText(fila.getString(3));
            e4.setText(fila.getString(5));
        }
    }

    public void Editar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nombre = e1.getText().toString();
        String apellido = e2.getText().toString();
        String eps = e3.getText().toString();
        String nivelGlucemia = e4.getText().toString();
        String seleccion = NombrePacientes.getSelectedItem().toString();
        String[] posicionSeleccion = seleccion.split("\\.");
        String idPosicion = posicionSeleccion[0];
        String seleccion2 = sintomas.getSelectedItem().toString();
        ContentValues registro = new ContentValues();
        registro.put("cedula", idPosicion);
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("eps", eps);
        registro.put("sintoma", seleccion2);
        registro.put("nivelglucemia", nivelGlucemia);
        float nivel = Float.parseFloat(nivelGlucemia);
        if(nivel >= 7.0 && nivel < 13.8){
            registro.put("crdiabetico", "Hiperglicemia Aislada");

        }
        if(nivel >= 13.8){
            registro.put("crdiabetico", "Cetoacidosis diabética");

        }
        if(nivel > 33){
            registro.put("crdiabetico", "Estado Hiperosmolar Hiperglucémico No Cetósico");

        }

        int cant = bd.update("pacientes",registro,"cedula="+idPosicion,null);
        if(cant==1){
            Toast.makeText(this,"El paciente se modificó con éxito", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"No se pudo modificar el pacientee", Toast.LENGTH_SHORT).show();
        }
        bd.close();
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("");

    }

    public void inicio(View v){
        Intent vInicio = new Intent(this, MainActivity.class);
        startActivity(vInicio);
    }

}