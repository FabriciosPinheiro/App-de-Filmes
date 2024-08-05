package com.fabricio.appdefilmes.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.fabricio.appdefilmes.R
import com.fabricio.appdefilmes.databinding.ActivityDetalhesFilmeBinding

class DetalhesFilme : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesFilmeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesFilmeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()// Esconde a barra de ação
        window.statusBarColor = getColor(R.color.black) // Define a cor da barra de status

        val capa = intent.extras?.getString("capa")
        val nome = intent.extras?.getString("nome")
        val descricao = intent.extras?.getString("descricao")
        val elenco = intent.extras?.getString("elenco")

        Glide.with(this).load(capa).centerCrop().into(binding.capaFilme)
        binding.txtNome.setText(nome)
        binding.txtDescricao.setText("Descrição: $descricao")
        binding.txtElenco.setText("Elenco: $elenco")

    }
}