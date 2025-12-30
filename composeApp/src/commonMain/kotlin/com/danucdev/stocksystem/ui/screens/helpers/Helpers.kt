package com.danucdev.stocksystem.ui.screens.helpers

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object NumberUtils {

    fun formatPriceNumberWithDollarSign(price:Long):String = "$${NumberFormat.getInstance(Locale("es", "AR")).format(price)}"
    fun isNegativeNumber(number:Long):Boolean = number < 0

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