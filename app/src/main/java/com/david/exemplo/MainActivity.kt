package com.david.exemplo

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.david.exemplo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class MainActivity : AppCompatActivity() {


     var binding: ActivityMainBinding? = null
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding!!.root)

        auth = Firebase.auth
        binding!!.botaoLogar.setOnClickListener(View.OnClickListener {

            logar(
                binding!!.editTextEmail.text.toString(),
            binding!!.editTextSenha.text.toString())
        })

        binding!!.botaoSair.setOnClickListener(View.OnClickListener {

            sair()

        })


    }

    private fun logar(email : String, senha : String){

        auth?.let {
            auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Autenticação efetuada com sucesso.",
                            Toast.LENGTH_SHORT).show()

                       updateUI()
                    } else {

                        Toast.makeText(baseContext, "Falha na autenticação.",
                            Toast.LENGTH_SHORT).show()
                        updateUI()
                    }
                }
        }

    }
    private  fun sair (){
        Toast.makeText(baseContext, "Logout efetuada com sucesso.",
            Toast.LENGTH_SHORT).show()

        Firebase.auth.signOut()

        updateUI()
    }

    private fun updateUI(){
        val usuario: FirebaseUser? = auth.currentUser
        try {
            binding?.textViewUsuarioLogado?.text = usuario?.email
        }catch (e: Exception){
            binding?.textViewUsuarioLogado?.text =""
        }

    }

    public override fun onStart() {
        super.onStart()
        updateUI()
    }

}