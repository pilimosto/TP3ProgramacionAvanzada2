package com.example.tp3programacionavanzada2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.tp3programacionavanzada2.R

class CustomGridAdapter(
    private val context: Context,
    private val dataList: List<Pair<String, String>>
) : BaseAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.grid_item, parent, false)

        val nroMatriculaTextView: TextView = view.findViewById(R.id.textViewNroMatricula)
        val tiempoTextView: TextView = view.findViewById(R.id.textViewTiempo)

        val (nroMatricula, tiempo) = dataList[position]

        nroMatriculaTextView.text = nroMatricula
        tiempoTextView.text = tiempo

        return view
    }
}