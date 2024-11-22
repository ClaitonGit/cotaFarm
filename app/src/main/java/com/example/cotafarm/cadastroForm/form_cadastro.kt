package com.example.cotafarm.cadastroForm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cotafarm.databinding.ActivityFormCadastroBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class form_cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCadastrar.setOnClickListener{ view ->
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val nome = binding.editnome.text.toString()

            if( email.isEmpty() || senha.isEmpty()){
                Toast.makeText(
                    this@form_cadastro,
                    "Preencha os campos vazios!",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                auth.createUserWithEmailAndPassword(email,senha) .addOnCompleteListener{ cadastro->
                 if(cadastro.isSuccessful){
                     Toast.makeText(
                         this@form_cadastro,
                         "Cadastro realisado com sucesso!",
                         Toast.LENGTH_SHORT
                     ).show()
                     binding.editEmail.setText("")
                     binding.editnome.setText("")
                     binding.editSenha.setText("")

                 }
                }.addOnFailureListener{ exception ->
                    val mensagensErro = when(exception){
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no minimo 6 caracteres!"
                        is FirebaseAuthInvalidCredentialsException -> "Digite um email válido"
                        is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada!"
                        is FirebaseNetworkException -> "Sem conexão com a internet!"
                        else -> "Erro ao cadastrar ususario"
                    }
                    Toast.makeText(
                        this@form_cadastro,
                        mensagensErro,
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
        }
    }
}