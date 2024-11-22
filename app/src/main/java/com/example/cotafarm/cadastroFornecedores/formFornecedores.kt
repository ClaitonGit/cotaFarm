package com.example.cotafarm.cadastroFornecedores

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cotafarm.ListagemFornecedor
import com.example.cotafarm.databinding.ActivityFormFornecedoresBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class formFornecedores : AppCompatActivity() {

    private lateinit var binding: ActivityFormFornecedoresBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormFornecedoresBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonCadastrarForm.setOnClickListener{ view->
            val nome = binding.editnomeFor.text.toString()
            val contato = binding.editContatoFor.text.toString()
            val email = binding.editEmailFor.text.toString()
            val endereco = binding.editEnderecoFor.text.toString()

            if (email.isEmpty() || nome.isEmpty()){
                val snackbar = Snackbar.make(view,"Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{

                // Criação de um mapa de dados para o fornecedor
                val fornecedorData = hashMapOf(
                    "nome" to nome,
                    "contato" to contato,
                    "email" to email,
                    "endereco" to endereco
                )

                // Salva os dados no Firebase Firestore
                db.collection("fornecedor")
                    .add(fornecedorData)
                    .addOnSuccessListener {
                        // Mostra uma mensagem de sucesso
                        Toast.makeText(
                            this@formFornecedores,
                            "Cadastro realisado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Navegação para a tela de listagem de fornecedores
                        val intent = Intent(this@formFornecedores, ListagemFornecedor::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        // Mostra uma mensagem de erro
                        Snackbar.make(view, "Erro ao cadastrar: ${e.message}", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.RED)
                            .show()
                    }
            }

                }

            }
        }
