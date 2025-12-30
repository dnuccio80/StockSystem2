package com.danucdev.stocksystem.ui.screens.helpers

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object NumberUtils {

    fun formatPriceNumber(price:Long):String = "$${NumberFormat.getInstance(Locale("es", "AR")).format(price)}"

}

object DateUtils {

    fun dateFormatter(date:LocalDate):String {
        val dateFormatter = DateTimeFormatter.ofPattern(
            "dd/MM/yyyy",
            Locale.getDefault()
        )

        return dateFormatter.format(date)
    }

}