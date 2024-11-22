package com.example.cotafarm.telaPrincipal


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cotafarm.ListagemFornecedor
import com.example.cotafarm.ListagemRemedio
import com.example.cotafarm.cadastroFornecedores.formFornecedores
import com.example.cotafarm.cadastroRemedio.CadastroRemedio
import com.example.cotafarm.databinding.ActivityTelaPrincipalBinding
import com.example.cotafarm.formLogin.FormLogin
import com.google.firebase.auth.FirebaseAuth

class telaPrincipal : AppCompatActivity() {

    private lateinit var binder: ActivityTelaPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binder.root)

        binder.btSair.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val voltarTelaLogin = Intent(this,FormLogin::class.java)
            startActivity(voltarTelaLogin)
            finish()
        }

        binder.supplierImage.setOnClickListener{
            val intent = Intent(this, formFornecedores::class.java)
            startActivity(intent)
        }

        binder.medicineImage.setOnClickListener{
            val intent = Intent(this, CadastroRemedio::class.java)
            startActivity(intent)
        }

        binder.listSupplierImage.setOnClickListener{
            val intent = Intent(this, ListagemFornecedor::class.java)
            startActivity(intent)
        }

        binder.listMedicineImage.setOnClickListener{
            val intent = Intent(this, ListagemRemedio::class.java)
            startActivity(intent)
        }



    }
}