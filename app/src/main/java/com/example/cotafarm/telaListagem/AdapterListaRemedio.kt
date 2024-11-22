package com.example.cotafarm.telaListagem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cotafarm.R

class AdapterListaRemedio(

    private val remedios: List<AdapterListaRemedio.Remedio>,
    private val context: Context
) : RecyclerView.Adapter<AdapterListaRemedio.MyViewHolder>(){



    private var filteredRemedios: List<Remedio> = remedios.toList()


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.textNomeRem)
        val preco: TextView = itemView.findViewById(R.id.textPrecoRem)
        val datarem: TextView = itemView.findViewById(R.id.textDataRem)
        val fornecedor: TextView = itemView.findViewById(R.id.textFornRem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterListaRemedio.MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.adapter_lista_remedio, parent, false)
        return AdapterListaRemedio.MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val remedio = filteredRemedios[position] // Use filteredRemedios aqui
        holder.nome.text = remedio.nome
        holder.preco.text = remedio.preco
        holder.datarem.text = remedio.datarem
        holder.fornecedor.text = remedio.fornecedor
    }

    override fun getItemCount(): Int {
        return filteredRemedios.size // Retorna o tamanho da lista filtrada
    }



    fun filter(query: String) {
        filteredRemedios = if (query.isEmpty()) {
            remedios // Mostra todos os itens se a busca estiver vazia
        } else {
            remedios.filter {
                it.nome.contains(query, ignoreCase = true) // Filtra apenas pelos nomes que contêm a query
            }
        }
        notifyDataSetChanged() // Atualiza a RecyclerView
    }

    // ordenar pela menor e maior preço
    fun sortByPriceAscending() {
        filteredRemedios = filteredRemedios.sortedBy { it.preco.toDoubleOrNull() ?: Double.MAX_VALUE }
        notifyDataSetChanged()
    }
    fun sortByPriceDescending() {
        filteredRemedios = filteredRemedios.sortedByDescending { it.preco.toDoubleOrNull() ?: Double.MIN_VALUE }
        notifyDataSetChanged()
    }




    data class Remedio(
        val nome: String,
        val preco: String,
        val datarem:String,
        val fornecedor: String
    )



}