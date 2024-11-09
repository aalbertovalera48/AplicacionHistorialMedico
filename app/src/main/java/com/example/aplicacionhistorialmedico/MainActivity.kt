package com.example.aplicacionhistorialmedico

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicalDataAdapter
    private val medicalDataList = mutableListOf<MedicalData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Configuración de la vista de inicio de sesión
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserToDatabase(email, password)
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error de autenticación", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Configuración de la vista de formulario de datos médicos
        val diagnosisEditText = findViewById<EditText>(R.id.diagnosisEditText)
        val treatmentEditText = findViewById<EditText>(R.id.treatmentEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val diagnosis = diagnosisEditText.text.toString()
            val treatment = treatmentEditText.text.toString()
            saveMedicalData(diagnosis, treatment)
        }

        // Configuración de la vista de lista de datos médicos
        recyclerView = findViewById(R.id.medicalDataRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MedicalDataAdapter(medicalDataList)
        recyclerView.adapter = adapter

        // Cargar los datos desde Firebase
        loadMedicalData()
    }

    private fun showMedicalDataForm() {
        // Mostrar el formulario de datos médicos
        findViewById<LinearLayout>(R.id.loginLayout).visibility = View.GONE
        findViewById<LinearLayout>(R.id.medicalDataFormLayout).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.medicalDataListLayout).visibility = View.VISIBLE
    }

    private fun saveSecretKey(secretKey: SecretKey) {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val encodedKey = Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
        editor.putString("secret_key", encodedKey)
        editor.apply()
    }

    private fun getSecretKey(): SecretKey? {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val encodedKey = sharedPreferences.getString("secret_key", null) ?: return null
        val decodedKey = Base64.decode(encodedKey, Base64.DEFAULT)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }

    private fun saveMedicalData(diagnosis: String, treatment: String) {
        val secretKey = EncryptionUtil.generateKey()
        saveSecretKey(secretKey)
        val encryptedDiagnosis = EncryptionUtil.encrypt(diagnosis, secretKey)
        val encryptedTreatment = EncryptionUtil.encrypt(treatment, secretKey)
        val medicalData = MedicalData(encryptedDiagnosis, encryptedTreatment)
        database.child("medical_data").push().setValue(medicalData)
            .addOnSuccessListener {
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadMedicalData() {
        val secretKey = getSecretKey() ?: return
        database.child("medical_data").get().addOnSuccessListener { dataSnapshot ->
            medicalDataList.clear()
            for (dataSnapshot in dataSnapshot.children) {
                val data = dataSnapshot.getValue(MedicalData::class.java)
                if (data != null) {
                    val decryptedDiagnosis = EncryptionUtil.decrypt(data.diagnosis, secretKey)
                    val decryptedTreatment = EncryptionUtil.decrypt(data.treatment, secretKey)
                    medicalDataList.add(MedicalData(decryptedDiagnosis, decryptedTreatment))
                }
            }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error al cargar los datos: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun saveUserToDatabase(email: String, password: String) {
        val userId = auth.currentUser?.uid ?: return
        val user = User(email, password)
        database.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show()
            }
    }
}