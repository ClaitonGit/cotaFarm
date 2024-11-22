package com.example.cotafarm.cadastroRemedio

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cotafarm.DatCustom
import com.example.cotafarm.ListagemRemedio
import com.example.cotafarm.PrecoCustom
import com.example.cotafarm.databinding.ActivityCadastroRemedioBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class CadastroRemedio : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroRemedioBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var fornecedorAutoComplete: AutoCompleteTextView
    private val fornecedoresList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroRemedioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()
        fornecedorAutoComplete = binding.editFornecedorRem


        // Inicializar a mascara de data
        val datCustom = DatCustom()
        val dataAtual = datCustom.dataAtual()
        binding.editdataRem.setText(dataAtual)

        // Aplicando a máscara de preço no campo editPrecoRem
        PrecoCustom(binding.editPrecoRem)

        // Chama os fornecedores no campo
        carregarFornecedores()

        binding.buttonCadastrarRem.setOnClickListener { view ->
            val nome = binding.editnomeRem.text.toString()
            val datarem = binding.editdataRem.text.toString()
            val preco = binding.editPrecoRem.text.toString()
            val fornecedor = binding.editFornecedorRem.text.toString()
            val descricao = binding.editDescricaorem2.text.toString()

            if (preco.isEmpty() || fornecedor.isEmpty() || nome.isEmpty()) {
                val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {
                val remedioData = hashMapOf(
                    "nome" to nome,
                    "datarem" to datarem,
                    "preco" to preco,
                    "fornecedor" to fornecedor,
                    "descricao" to descricao
                )

                db.collection("remedio")
                    .add(remedioData)
                    .addOnSuccessListener {
                        // Mostra uma mensagem de sucesso
                        Toast.makeText(
                            this@CadastroRemedio,
                            "Cadastro realisado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Navegação para a tela de listagem de remedios
                        val intent = Intent(this@CadastroRemedio, ListagemRemedio::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Snackbar.make(view, "Erro ao cadastrar: ${e.message}", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.RED)
                            .show()
                    }
            }
        }
    }

    private fun carregarFornecedores() {
        db.collection("fornecedor")
            .get()
            .addOnSuccessListener { querySnapshot ->
                fornecedoresList.clear()
                for (document in querySnapshot) {
                    val fornecedorNome = document.getString("nome")
                    fornecedorNome?.let { fornecedoresList.add(it) }
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, fornecedoresList)
                fornecedorAutoComplete.setAdapter(adapter)
            }

        fornecedorAutoComplete.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                fornecedorAutoComplete.showDropDown()
            }
        }

        fornecedorAutoComplete.setOnClickListener {
            fornecedorAutoComplete.showDropDown()
        }
    }

}
