package br.com.qmovie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.qmovie.R
import br.com.qmovie.domain.Dica
import br.com.qmovie.domain.TipoDica
import br.com.qmovie.viewmodel.JogoViewModel
import kotlinx.android.synthetic.main.item_dica.view.*

class DicaAdapter(
    private val viewModel: JogoViewModel
) : RecyclerView.Adapter<DicaAdapter.DicaViewHolder>() {

    var dicas = arrayListOf<Dica>()
    set(value) {
        if (value == null) return
        field = value
        notifyDataSetChanged()
    }

    class DicaViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        val tvDicaNumero : TextView = view.tvDicaNumero

        val tvDicaConteudo : TextView = view.tvDicaConteudo
        val ivDicaImagem : ImageView = view.ivDicaImagem
        val tvInterrogacao : TextView = view.tvInterrogacao
        val btnAbrirDica : Button = view.btnAbrirDica

        val btnDicaSetaDireita : Button = view.btnDicaSetaDireita
        val btnDicaSetaEsquerda : Button = view.btnDicaSetaEsquerda
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(view)
    }

    override fun getItemCount() = dicas.size

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = dicas[position]

        holder.tvDicaNumero.text = "Dica #${position + 1}"
        holder.tvDicaConteudo.text = dica.conteudo
        // TODO: definir dica de imagem

        if (position == 0) holder.btnDicaSetaEsquerda.visibility = View.INVISIBLE
        if (position == itemCount - 1) holder.btnDicaSetaDireita.visibility = View.INVISIBLE
        when (dica.estaAberta) {
            false -> fecharDica(holder)
            true -> abrirDica(holder, dica.tipo)
        }

        holder.btnAbrirDica.setOnClickListener {
            viewModel.abrirDica(dica)
            notifyItemChanged(position)
        }
    }

    private fun fecharDica(holder: DicaViewHolder) {
        holder.btnAbrirDica.visibility = View.VISIBLE
        holder.tvInterrogacao.visibility = View.VISIBLE
        holder.tvDicaConteudo.visibility = View.INVISIBLE
        holder.ivDicaImagem.visibility = View.INVISIBLE
    }

    private fun abrirDica(holder: DicaViewHolder, tipoDica: TipoDica) {
        holder.btnAbrirDica.visibility = View.INVISIBLE
        holder.tvInterrogacao.visibility = View.INVISIBLE
        when (tipoDica) {
            TipoDica.IMAGEM -> {
                holder.tvDicaConteudo.visibility = View.INVISIBLE
                holder.ivDicaImagem.visibility = View.VISIBLE
            }
            TipoDica.TEXTO -> {
                holder.tvDicaConteudo.visibility = View.VISIBLE
                holder.ivDicaImagem.visibility = View.INVISIBLE
            }
        }
    }
}