package com.example.cotafarm.telaListagem

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cotafarm.R
import com.google.firebase.firestore.FirebaseFirestore

class AdapterFornDetalhes : AppCompatActivity() {


    private lateinit var db: FirebaseFirestore
    private lateinit var nomeTextView: TextView
    private lateinit var telefoneTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var enderecoTextView: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adapter_forndetalhes)


        db = FirebaseFirestore.getInstance()

        // Inicializar TextViews
        nomeTextView = findViewById(R.id.textNomeFornecedor)
        telefoneTextView = findViewById(R.id.textTelefoneFornecedor)
        emailTextView = findViewById(R.id.textEmailFornecedor)
        enderecoTextView = findViewById(R.id.textEnderecoFornecedor)

        // Receber o ID do fornecedor da Intent
        val fornecedorId = intent.getStringExtra("FORNECEDOR_ID")
        // Carregar dados do fornecedor específico
        if (fornecedorId != null) {
            carregarFornecedor(fornecedorId)
        }
    }

    private fun carregarFornecedor(fornecedorId: String) {
        db.collection("fornecedor").document(fornecedorId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    nomeTextView.text = document.getString("nome")
                    emailTextView.text = document.getString("email")
                    telefoneTextView.text = document.getString("contato")
                    enderecoTextView.text = document.getString("endereco")
                }
            }
            .addOnFailureListener {
                // Exibir mensagem de erro
            }
    }




}