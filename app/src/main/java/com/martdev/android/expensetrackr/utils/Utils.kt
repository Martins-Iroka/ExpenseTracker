package com.martdev.android.expensetrackr.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.martdev.android.expensetrackr.R
import java.lang.Long.parseLong
import java.text.*
import java.util.*
import java.util.concurrent.TimeUnit

object Utils {

    private fun elapsedDaysSinceEpoch(date: Long): Long {
        return TimeUnit.MILLISECONDS.toDays(date)
    }

    fun getReadableDate(datePicker: Long, context: Context): String {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val date = GregorianCalendar(year, month, day).time.time

        val currentDate = elapsedDaysSinceEpoch(date)
        val daysAfterToday = elapsedDaysSinceEpoch(datePicker)

        return when((daysAfterToday - currentDate).toInt()) {
            -1 -> context.getString(R.string.yesterday)
            0 -> context.getString(R.string.today)
            1 -> context.getString(R.string.tomorrow)
            else -> DateFormat.getDateInstance(DateFormat.MEDIUM).format(datePicker)
        }
    }

    fun getCurrencyFormat(figure: String): String {
        val parsedFigure = parseLong(figure)
        val numberFormat = NumberFormat.getCurrencyInstance()
        val symbols = DecimalFormatSymbols.getInstance().apply {
            currencySymbol = "\u20A6"
            groupingSeparator = '.'}
        (numberFormat as DecimalFormat).decimalFormatSymbols = symbols

        return numberFormat.format(parsedFigure)
    }

    fun showSnackBar(view: View, message: String, duration: Int) {
        Snackbar.make(view, message, duration).show()
    }

    fun getDateFormat(date: Date): String {
        return SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(date)
    }
}