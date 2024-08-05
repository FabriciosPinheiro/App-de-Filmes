package com.fabricio.appdefilmes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fabricio.appdefilmes.databinding.FilmeItemBinding
import com.fabricio.appdefilmes.model.Filme
import com.fabricio.appdefilmes.view.DetalhesFilme

class AdapterFilme(private  val context: Context, private val listaFilmes: MutableList<Filme>): RecyclerView.Adapter<AdapterFilme.FilmeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val itemlista = FilmeItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return FilmeViewHolder(itemlista)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        Glide.with(context).load(listaFilmes[position].capa).centerCrop().into(holder.capa)

        holder.capa.setOnClickListener {
            val intent = Intent(context, DetalhesFilme::class.java)
            //intent.putExtra("id", listaFilmes[position].id) // Passa o ID do filme para a tela de detalhes
            intent.putExtra("capa", listaFilmes[position].capa)
            intent.putExtra("nome", listaFilmes[position].nome)
            intent.putExtra("descricao", listaFilmes[position].descricao)
            intent.putExtra("elenco", listaFilmes[position].elenco)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = listaFilmes.size

    inner  class  FilmeViewHolder(binding: FilmeItemBinding):RecyclerView.ViewHolder(binding.root){
        val capa = binding.capaFilme
    }

}