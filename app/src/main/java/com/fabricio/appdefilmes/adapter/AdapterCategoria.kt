package com.fabricio.appdefilmes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabricio.appdefilmes.databinding.CategoriaItemBinding
import com.fabricio.appdefilmes.model.Categoria
import com.fabricio.appdefilmes.model.Filme

class AdapterCategoria(private val context:Context, val listaCategoria:MutableList<Categoria>):
    RecyclerView.Adapter<AdapterCategoria.CategoriaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val itemlista = CategoriaItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoriaViewHolder(itemlista)
    }

    override fun getItemCount() = listaCategoria.size

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.titulo.text = listaCategoria[position].titulo

        val categoria = listaCategoria[position]

        holder.recyclerViewFilmes.adapter = AdapterFilme(context, categoria.listaFilmes)
        holder.recyclerViewFilmes.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    inner  class  CategoriaViewHolder(binding: CategoriaItemBinding):RecyclerView.ViewHolder(binding.root){
        val titulo = binding.txtTitulo
        val recyclerViewFilmes = binding.recyclerViewFilmes
    }

}