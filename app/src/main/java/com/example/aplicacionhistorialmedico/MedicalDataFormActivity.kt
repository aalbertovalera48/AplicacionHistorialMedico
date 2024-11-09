package com.example.aplicacionhistorialmedico

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MedicalDataFormActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_data_form)

        firestore = FirebaseFirestore.getInstance()

        val diagnosisEditText = findViewById<EditText>(R.id.diagnosisEditText)
        val treatmentEditText = findViewById<EditText>(R.id.treatmentEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val diagnosis = diagnosisEditText.text.toString()
            val treatment = treatmentEditText.text.toString()

            val medicalData = hashMapOf(
                "diagnosis" to diagnosis,
                "treatment" to treatment
            )

            firestore.collection("medical_data")
                .add(medicalData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
