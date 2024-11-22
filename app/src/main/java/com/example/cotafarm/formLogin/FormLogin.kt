package com.example.cotafarm.formLogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cotafarm.cadastroForm.form_cadastro
import com.example.cotafarm.databinding.ActivityFormLoginBinding
import com.example.cotafarm.telaPrincipal.telaPrincipal
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener{ view->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view,"Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }else{
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener{autenticacao ->
                    if (autenticacao.isSuccessful){
                        navegarTelaPrincipal()
                    }

                }.addOnFailureListener{
                    val snackbar = Snackbar.make(view, "Erro ao fazer o login! tente novamente",Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }

            }
        }

        binding.txtLinkTelacadastro.setOnClickListener{
            val intent = Intent(this, form_cadastro::class.java)
            startActivity(intent)
            }
    }

    private fun navegarTelaPrincipal(){
        val intent = Intent(this,telaPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        val userAtual = FirebaseAuth.getInstance().currentUser
        if (userAtual != null){
            navegarTelaPrincipal()
        }
    }
}