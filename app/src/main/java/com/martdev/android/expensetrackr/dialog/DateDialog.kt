package com.martdev.android.expensetrackr.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.widget.DatePicker
import com.martdev.android.expensetrackr.R
import java.util.*

class DateDialog : DialogFragment() {

    companion object {
        private const val EXTRA_DATE = "com.martdev.android.expensetrackr.dialog.extra_date"
        private const val ARG_DATE = "date"

        fun newInstance(date: Date): DateDialog {
            val arg = Bundle()
            arg.putSerializable(ARG_DATE, date)

            val dialog = DateDialog()
            dialog.arguments = arg

            return dialog
        }

        fun getNewDate(data: Intent): Date {
            return data.getSerializableExtra(EXTRA_DATE) as Date
        }

    }

    private lateinit var mDatePicker: DatePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var date = arguments?.getSerializable(ARG_DATE) as Date

        val calendar = Calendar.getInstance()
        calendar.time = date

        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        val view = LayoutInflater.from(requireActivity()).inflate(R.layout.date_picker_dialog, null)

        mDatePicker = view.findViewById(R.id.date_picker)
        mDatePicker.init(year, month, day, null)

        return with(AlertDialog.Builder(this.activity!!)) {
            setView(view)
            setPositiveButton(android.R.string.ok) { _, _ ->
                year = mDatePicker.year
                month = mDatePicker.month
                day = mDatePicker.dayOfMonth
                date = GregorianCalendar(year, month, day).time
                sendResult(date)
            }
            create()
        }
    }

    private fun sendResult(date: Date) {
        if (targetFragment == null) return

        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)

        targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    }
}