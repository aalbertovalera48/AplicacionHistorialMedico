package com.example.aplicacionhistorialmedico

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MedicalDataListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MedicalDataAdapter
    private val medicalDataList = mutableListOf<MedicalData>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_data_list)

        recyclerView = findViewById(R.id.medicalDataRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MedicalDataAdapter(medicalDataList)
        recyclerView.adapter = adapter

        // Cargar los datos desde Firebase
        loadMedicalData()
    }

    private fun loadMedicalData() {
        db.collection("medical_data")
            .get()
            .addOnSuccessListener { documents ->
                medicalDataList.clear()
                for (document in documents) {
                    val data = document.toObject(MedicalData::class.java)
                    medicalDataList.add(data)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                // Manejo de error
            }
    }
}
