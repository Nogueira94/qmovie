package br.com.qmovie.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import br.com.qmovie.R
import kotlinx.android.synthetic.main.fragment_jogo.*

class JogoViewModel: ViewModel() {

    private val MAX_DICAS_EXTRAS = 1
    private lateinit var countdownTimer : CountDownTimer

    val dicasExtrasUtilizadas = MutableLiveData<Int>(0)
    val nomeFilme : String = "O Diabo veste Prada"
    val nomeFilmeEscondido = MutableLiveData<String>("")
    val _tempoRestante = MutableLiveData<Long>(180000L)
    val tempoAcabou = MutableLiveData<Boolean>(false)

    fun usarDicaExtra() {
        if (temDicaExtraDisponivel())
            dicasExtrasUtilizadas.value = dicasExtrasUtilizadas.value!! + 1
    }

    fun temDicaExtraDisponivel() = dicasExtrasUtilizadas.value!! < MAX_DICAS_EXTRAS

    fun esconderNome() {
        var nomeEscondido = ""

        nomeFilme.forEach {
            if (it == ' ') nomeEscondido += it
            else nomeEscondido += "*"
        }
        nomeFilmeEscondido.value = nomeEscondido
    }

    fun abrirLetras(quantidadeDesejada: Int) {
        var quantidade = 0
        var nomeArray = nomeFilmeEscondido.value!!.toMutableList()
        while (quantidade < quantidadeDesejada) {
            var position = (0 until nomeArray.size).random()
            if (nomeArray[position] == '*') {
                nomeArray[position] = nomeFilme.get(position)
                quantidade++
            }
        }

        nomeFilmeEscondido.value =  nomeArray.joinToString(separator="")

        adicionaTempo(-10000L)
    }

    fun validaResposta(resposta: String) = resposta.toLowerCase() == this.nomeFilme.toLowerCase()

    fun criaTimer(tempoMillis: Long = 180000L) {
        countdownTimer = object : CountDownTimer(tempoMillis, 1000L) {

            override fun onFinish() {
                tempoAcabou.value = true
            }

            override fun onTick(tempoRestante: Long) {
                _tempoRestante.value = tempoRestante
            }

        }
        countdownTimer.start()
    }

    fun adicionaTempo(tempoParaAdicionar: Long) {
        countdownTimer.cancel()

        _tempoRestante.value = _tempoRestante.value!! + tempoParaAdicionar
        if (_tempoRestante.value!! <= 0L) _tempoRestante.value = 0

        criaTimer(_tempoRestante.value!!)
    }

}