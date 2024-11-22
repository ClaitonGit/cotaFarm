package com.example.cotafarm

import java.text.SimpleDateFormat

class DatCustom {

    fun dataAtual(): String? {
        val date = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return simpleDateFormat.format(date)
    }

    fun mesAnoDataEscolhido(data: String): String? {
        val retornoData =
            data.split("/".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
        val dia = retornoData[0]
        val mes = retornoData[1]
        val ano = retornoData[2]
        return mes + ano
    }
}