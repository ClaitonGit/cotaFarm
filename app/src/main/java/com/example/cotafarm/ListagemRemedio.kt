package com.example.cotafarm

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cotafarm.cadastroRemedio.CadastroRemedio
import com.example.cotafarm.databinding.ActivityListagemRemedioBinding
import com.example.cotafarm.telaListagem.AdapterListaRemedio
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class ListagemRemedio : AppCompatActivity() {

    private lateinit var adapter: AdapterListaRemedio
    private val remedios = mutableListOf<AdapterListaRemedio.Remedio>()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var binding : ActivityListagemRemedioBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListagemRemedioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = AdapterListaRemedio(remedios, this)
        binding.recyclerViewRem.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRem.adapter = adapter


        fetchRemedioFromFirebase()


        // Adicionar TextWatcher ao campo de busca
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("SearchDebug", "Texto digitado: $s")
                adapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Botão de ordenação por menor preço
        // Botão de ordenação por menor preço
        binding.sortButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.sort_menu, popupMenu.menu)

            // Mostrar o menu
            popupMenu.show()

            // Lidar com as opções de menu
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_sort_asc -> {
                        // Ordenar por menor preço
                        adapter.sortByPriceAscending()
                        true
                    }
                    R.id.menu_sort_desc -> {
                        // Ordenar por maior preço
                        adapter.sortByPriceDescending()
                        true
                    }
                    else -> false
                }
            }
        }





        // Inicializar e configurar o clique do botão flutuante
        val botaofloatRem = findViewById<FloatingActionButton>(R.id.floatingActionButtonAddRem)
        botaofloatRem.setOnClickListener {
            val intent = Intent(this, CadastroRemedio::class.java)
            startActivity(intent)
        }



    }


    private fun fetchRemedioFromFirebase() {
        db.collection("remedio")
            .get()
            .addOnSuccessListener { result ->
                remedios.clear()
                for (document in result) {
                    val nome = document.getString("nome") ?: ""
                    val preco = document.getString("preco") ?: ""
                    val fornecedor = document.getString("fornecedor") ?: ""
                    val datarem = document.getString("datarem") ?: ""
                    val remedio = AdapterListaRemedio.Remedio(nome, preco, datarem, fornecedor)
                    remedios.add(remedio)
                }
                adapter.filter("") // Garante que a lista inicial está preenchida
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                Log.e("ListagemRemedio", "Erro ao buscar remédios: ${exception.message}")
            }
    }




}