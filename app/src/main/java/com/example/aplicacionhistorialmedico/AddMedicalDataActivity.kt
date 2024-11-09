package com.example.aplicacionhistorialmedico

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class AddMedicalDataActivity : AppCompatActivity() {

    private lateinit var diagnosisEditText: EditText
    private lateinit var treatmentEditText: EditText
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medical_data)

        diagnosisEditText = findViewById(R.id.diagnosisEditText)
        treatmentEditText = findViewById(R.id.treatmentEditText)

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            saveMedicalData()
        }
    }

    private fun saveMedicalData() {
        val diagnosis = diagnosisEditText.text.toString()
        val treatment = treatmentEditText.text.toString()

        if (diagnosis.isNotEmpty() && treatment.isNotEmpty()) {
            val medicalData = MedicalData(diagnosis, treatment)
            db.collection("medical_data")
                .add(medicalData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show()
                    finish() // Regresa a la actividad anterior
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
