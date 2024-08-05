package com.fabricio.appdefilmes.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fabricio.appdefilmes.R
import com.fabricio.appdefilmes.databinding.ActivityFormeLoginBinding
import com.google.firebase.auth.FirebaseAuth

class FormeLogin : AppCompatActivity() {
    private lateinit var binding: ActivityFormeLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormeLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide() // Esconde a barra de ação
        window.statusBarColor = Color.parseColor("#000000") // Define a cor da barra de status

        binding.edtEmail.requestFocus()

        binding.btnEntrar.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val senha = binding.edtSenha.text.toString()

            when {
                email.isEmpty() -> {
                    binding.containerEmail.helperText = "Preencha o email!"
                    binding.containerEmail.boxStrokeColor = Color.parseColor("#FF9800")
                }

                senha.isEmpty() -> {
                    binding.containerSenha.helperText = "Preencha a senha!"
                    binding.containerSenha.boxStrokeColor = Color.parseColor("#FF9800")

                }else -> {
                    autenticarUsuario(email, senha)
            }
            }

        }

        binding.txtCriarConta.setOnClickListener {
            val intent = Intent(this, FormeCadastro::class.java)
            startActivity(intent)
        }
    }

    private fun autenticarUsuario(email: String, senha: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener { autenticacao ->
            if (autenticacao.isSuccessful) {
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                navegarTelaPrincipal()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Erro ao fazer login!", Toast.LENGTH_SHORT).show()
        }

    }
    private fun navegarTelaPrincipal() {
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish() // Finaliza a tela atual para que o usuário não possa voltar
    }

    override fun onStart() {// Verifica se o usuário já está logado
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser // Verifica se o usuário está logado

        if (usuarioAtual != null) { // Se estiver logado, navega para a tela principal
            navegarTelaPrincipal()
        }
    }
}