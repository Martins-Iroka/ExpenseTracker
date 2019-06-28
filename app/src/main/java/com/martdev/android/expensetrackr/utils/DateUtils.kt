package com.martdev.android.expensetrackr.utils

import android.content.Context
import com.martdev.android.expensetrackr.R
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {

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
}