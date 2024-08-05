package com.fabricio.appdefilmes.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabricio.appdefilmes.R
import com.fabricio.appdefilmes.adapter.AdapterCategoria
import com.fabricio.appdefilmes.databinding.ActivityTelaPrincipalBinding
import com.fabricio.appdefilmes.model.Categoria
import com.fabricio.appdefilmes.model.Categorias
import com.fabricio.appdefilmes.api.Api
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TelaPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityTelaPrincipalBinding
    private lateinit var adapterCategoria: AdapterCategoria
    private val listaCategoria: MutableList<Categoria> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()// Esconde a barra de ação
        window.statusBarColor = getColor(R.color.black) // Define a cor da barra de status

        val recyclerViewFilmes = binding.recyclerViewFilmes
        recyclerViewFilmes.layoutManager = LinearLayoutManager(this)
        recyclerViewFilmes.setHasFixedSize(true)
        adapterCategoria = AdapterCategoria(this, listaCategoria)
        recyclerViewFilmes.adapter = adapterCategoria

        binding.txtSair.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormeLogin::class.java)
            startActivity(intent)
            finish()
        }

        //Configurar retrofit
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://stackmobile.com.br/")
            .build()
            .create(Api::class.java)

        retrofit.listaCategorias().enqueue(object : Callback<Categorias> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Categorias>, response: Response<Categorias>) {
                if(response.code() == 200){
                    response.body()?.let {
                        adapterCategoria.listaCategoria.addAll(it.categorias)
                        adapterCategoria.notifyDataSetChanged()

                        binding.containerProgressBar.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.textCarregando.visibility = View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<Categorias>, t: Throwable) {
                Toast.makeText(this@TelaPrincipal, "Erro ao buscar todos os filmes!", Toast.LENGTH_SHORT).show()
            }

        })

    }

}