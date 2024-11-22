package com.example.cotafarm.telaListagem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cotafarm.R

class AdapterListaForm(
    private val fornecedores: List<Fornecedor>,
    private val context: Context
) : RecyclerView.Adapter<AdapterListaForm.MyViewHolder>() {


    private var onItemClickListener: ((Fornecedor) -> Unit)? = null
    private var onDeleteClickListener: ((Fornecedor) -> Unit)? = null

    fun setOnItemClickListener(listener: (Fornecedor) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnDeleteClickListener(listener: (Fornecedor) -> Unit) {
        onDeleteClickListener = listener
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.textNomeForm)
        val telefone: TextView = itemView.findViewById(R.id.textCellForn)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.adapter_listfornecer, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fornecedor = fornecedores[position]
        holder.nome.text = fornecedor.nome
        holder.telefone.text = fornecedor.telefone


        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(fornecedor)
        }


        // Evento do botão Excluir
        holder.btnDelete.setOnClickListener {
            onDeleteClickListener?.invoke(fornecedor)
        }

    }

    override fun getItemCount(): Int {
        return fornecedores.size
    }

    data class Fornecedor(
        val id: String,
        val nome: String,
        val telefone: String
    )

}
