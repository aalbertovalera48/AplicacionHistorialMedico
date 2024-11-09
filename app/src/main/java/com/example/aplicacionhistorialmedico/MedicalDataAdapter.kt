package com.example.aplicacionhistorialmedico

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicalDataAdapter(private val medicalDataList: List<MedicalData>) :
    RecyclerView.Adapter<MedicalDataAdapter.MedicalDataViewHolder>() {

    class MedicalDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val diagnosisTextView: TextView = itemView.findViewById(R.id.diagnosisTextView)
        val treatmentTextView: TextView = itemView.findViewById(R.id.treatmentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicalDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medical_data, parent, false)
        return MedicalDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicalDataViewHolder, position: Int) {
        val data = medicalDataList[position]
        holder.diagnosisTextView.text = "Diagnóstico: ${data.diagnosis}"
        holder.treatmentTextView.text = "Tratamiento: ${data.treatment}"
    }

    override fun getItemCount(): Int {
        return medicalDataList.size
    }
}
