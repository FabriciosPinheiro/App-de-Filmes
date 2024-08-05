package com.fabricio.appdefilmes.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fabricio.appdefilmes.R
import com.fabricio.appdefilmes.databinding.ActivityFormeCadastroBinding
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.ktx.Firebase

class FormeCadastro : AppCompatActivity() {
    private lateinit var binding: ActivityFormeCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormeCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#FFFFFF")
        binding.editEmail.requestFocus()

        Firebase

        binding.btVamosLa.setOnClickListener {
            val email = binding.editEmail.text.toString()

            if (!email.isEmpty()){
                binding.containerSenha.visibility = View.VISIBLE
                binding.btVamosLa.visibility = View.GONE
                binding.btContinuar.visibility = View.VISIBLE
                binding.textTitulo.setText("Um mundo de séries e filmes \n ilimitados espera por você.")
                binding.textDescricao.setText("Crie uma conta para saber mais sobre \n o nosso App de Filmes")
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF018786")
                binding.containerEmail.helperText = ""
                binding.containerHeader.visibility = View.VISIBLE
            }else{
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                binding.containerEmail.helperText = "O email é obrigatório."
            }
        }

        binding.btContinuar.setOnClickListener {
            val senha = binding.editSenha.text.toString()
            val email = binding.editEmail.text.toString()

            if (!email.isEmpty() && !senha.isEmpty()){
                cadastro(email, senha)

            }else if (senha.isEmpty()){
                binding.containerSenha.boxStrokeColor = Color.parseColor("#FF0000")
                binding.containerSenha.helperText = "A senha é obrigatória."
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF018786")
            }else if (email.isEmpty()){
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                binding.containerEmail.helperText = "O email é obrigatório."
            }
        }

        binding.txtEntrar.setOnClickListener {
            val intent = Intent(this, FormeLogin::class.java)
            startActivity(intent)
        }

    }

    private fun cadastro(email: String, senha: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
            if (cadastro.isSuccessful) {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

                binding.containerEmail.helperText = ""
                binding.containerEmail.boxStrokeColor = Color.parseColor("#FF018786")
                binding.containerSenha.helperText = ""
                binding.containerSenha.boxStrokeColor = Color.parseColor("#FF018786")

            }

        }.addOnFailureListener {
            val erro = it

            when{
                erro is com.google.firebase.auth.FirebaseAuthWeakPasswordException -> {
                    binding.containerSenha.boxStrokeColor = Color.parseColor("#FF0000")
                    binding.containerSenha.helperText = "A senha deve conter no mínimo 6 caracteres."
                }
                erro is FirebaseAuthUserCollisionException -> {
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                    binding.containerEmail.helperText = "Este email já está cadastrado."
                }
                erro is FirebaseNetworkException -> {
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                    binding.containerEmail.helperText = "Sem conexão com a internet."
                }
                else -> {
                    Toast.makeText(this, "Erro ao cadastrar usuário.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}