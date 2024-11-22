package com.example.cotafarm

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

class PrecoCustom(editText: EditText) {

    init {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!isUpdating) {
                    isUpdating = true

                    // Remover caracteres que não são números para processar o valor
                    val cleanString = s.toString().replace("[R$,.]".toRegex(), "")
                    val parsed = cleanString.toDoubleOrNull() ?: 0.0

                    isUpdating = false
                }
            }
        })
    }
}
