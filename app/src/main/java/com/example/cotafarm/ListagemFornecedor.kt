package com.example.cotafarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cotafarm.cadastroFornecedores.formFornecedores
import com.example.cotafarm.databinding.ActivityListagemFornecedorBinding
import com.example.cotafarm.telaListagem.AdapterFornDetalhes
import com.example.cotafarm.telaListagem.AdapterListaForm
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore


class ListagemFornecedor : AppCompatActivity() {

    private lateinit var binding: ActivityListagemFornecedorBinding
    private lateinit var adapter: AdapterListaForm
    private val fornecedores = mutableListOf<AdapterListaForm.Fornecedor>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListagemFornecedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView e Adapter
        adapter = AdapterListaForm(fornecedores, this)
        binding.recyclerViewForne.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewForne.adapter = adapter



        // Buscar dados do Firebase
        fetchFornecedoresFromFirebase()

        // Definir o listener para o clique no item da lista
        adapter.setOnItemClickListener { fornecedor ->
            Log.d("ListagemFornecedor", "Item clicado: ${fornecedor.nome}")  // Verifique se o clique é registrado
            val intent = Intent(this, AdapterFornDetalhes::class.java)
            intent.putExtra("FORNECEDOR_ID", fornecedor.id)  // Passando o ID do fornecedor
            startActivity(intent)
        }


        // Inicializar e configurar o clique do botão flutuante
        val botaofloat = findViewById<FloatingActionButton>(R.id.floatingActionButtonAddForn)
        botaofloat.setOnClickListener {
            val intent = Intent(this, formFornecedores::class.java)
            startActivity(intent)
        }


        // Excluir fornecedor
        adapter.setOnDeleteClickListener { fornecedor ->
            // Confirmação antes de excluir
            AlertDialog.Builder(this)
                .setTitle("Excluir Fornecedor")
                .setMessage("Deseja realmente excluir o fornecedor ${fornecedor.nome}?")
                .setPositiveButton("Sim") { _, _ ->
                    // Excluir do Firebase
                    FirebaseFirestore.getInstance().collection("fornecedor")
                        .document(fornecedor.id)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Fornecedor excluído com sucesso!", Toast.LENGTH_SHORT).show()
                            ListagemFornecedor() // Atualiza a lista após excluir
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Erro ao excluir: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .setNegativeButton("Não", null)
                .show()
        }




    }

    private fun fetchFornecedoresFromFirebase() {
        db.collection("fornecedor")
            .get()
            .addOnSuccessListener { result ->
                fornecedores.clear() // Limpar a lista antes de adicionar novos dados
                for (document in result) {
                    val nome = document.getString("nome") ?: ""
                    val telefone = document.getString("contato") ?: ""
                    val id = document.id
                    val fornecedor = AdapterListaForm.Fornecedor(id, nome, telefone)
                    fornecedores.add(fornecedor)
                }
                adapter.notifyDataSetChanged() // Atualizar o adaptador com os novos dados
            }
            .addOnFailureListener { exception ->
                // Lidar com erros na consulta
                exception.printStackTrace()
                Log.e("com.example.cotafarm.ListagemFornecedor", "Erro ao buscar fornecedores: ${exception.message}")
            }
    }




}
