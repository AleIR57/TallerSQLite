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

public class ExamenGlicemia extends AppCompatActivity {
    Spinner NombrePacientes;
    ArrayList<String> opciones;
    EditText e1;
    TextView t1;
    String resultado = "";
    String nombrePaciente;
    String apellidoPaciente;
    int cedula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen_glicemia);
        e1 = findViewById(R.id.EditTextNivelG);
        t1 = findViewById(R.id.TextViewResultados);
        NombrePacientes = findViewById(R.id.spinnerNombresExamen);
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

    public void resultados(View v){
        String seleccion = NombrePacientes.getSelectedItem().toString();
        String[] posicionSeleccion = seleccion.split("\\.");
        String idPosicion = posicionSeleccion[0];
        String nivelGlucemia = e1.getText().toString();
        float nivel = Float.parseFloat(nivelGlucemia);
        ContentValues registro = new ContentValues();

        registro.put("nivelglucemia", nivelGlucemia);
        if(nivel >= 7.0 && nivel < 13.8){
            registro.put("crdiabetico", "Hiperglicemia Aislada");
            t1.setText("Indicar glucemia en ayunas y TGP en pacientes sin diagnóstico. - Si deshidratación, rehidratación oral o EV según las demandas. - Reevaluar conducta terapéutica en diabéticos y cumplimiento de los pilares. - Reevaluar dosis de hipoglucemiantes.");
        }
        if(nivel >= 13.8){
            registro.put("crdiabetico", "Cetoacidosis diabética");
            t1.setText("Coordinar traslado y comenzar tratamiento. - Hidratación con Solución salina 40 ml/Kg en las primeras 4 horas. 1-2 L la primera hora. - Administrar potasio al restituirse la diuresis o signos de hipopotasemia (depresión del ST, Onda U ≤ 1mv, ondas U≤ T). - Evitar insulina hasta desaparecer signos de hipopotasemia. - Administrar insulina simple 0,1 U/kg EV después de hidratar");
        }
        if(nivel > 33){
            registro.put("crdiabetico", "Estado Hiperosmolar Hiperglucémico No Cetósico");
            t1.setText("Coordinar traslado y comenzar tratamiento. - Hidratación con Solución Salina 10-15 ml/Kg/h hasta conseguir estabilidad hemodinámica. - Administrar potasio al restituirse la diuresis o signos de hipopotasemia (depresión del ST, Onda U ≤ 1mv, ondas U≤ T).");
        }
        if(nivel >= 1.0 && nivel < 7.0){
            registro.put("crdiabetico", "No presenta");
            t1.setText("No hay recomendaciones");
        }

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        int cant = bd.update("pacientes",registro,"cedula="+idPosicion,null);
        if(cant==1){
            Toast.makeText(this,"El paciente se realizó el exámen de glucemia con éxito", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"No se pudo realizar el exámen de glucemia", Toast.LENGTH_SHORT).show();
        }
        bd.close();
        e1.setText("");


    }


    public void inicio(View v){
        Intent vInicio = new Intent(this, MainActivity.class);
        startActivity(vInicio);
    }


}