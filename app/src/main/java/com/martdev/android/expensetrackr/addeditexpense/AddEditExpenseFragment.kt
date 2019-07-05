package com.martdev.android.expensetrackr.addeditexpense

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import androidx.fragment.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.martdev.android.expensetrackr.R
import com.martdev.android.expensetrackr.dialog.DateDialog
import java.util.*

class AddEditExpenseFragment : Fragment(), AddEditExpenseContract.View, RecognitionListener {

    companion object {
        private const val EXPENSE_ID = "expenseId"
        const val REQUEST_DATE = 100
        const val REQUEST_PERMISSION = 101
        val PERMISSION = Array(1) {Manifest.permission.READ_CONTACTS}

        fun newInstance(expenseId: String?): AddEditExpenseFragment {

            val fragment = AddEditExpenseFragment()

            if (expenseId != null) {
                val arg = Bundle()
                arg.putString(EXPENSE_ID, expenseId)
                fragment.arguments = arg
            }

            return fragment
        }
    }

    override lateinit var presenter: AddEditExpenseContract.Presenter

    private lateinit var mAmount: TextInputEditText
    private lateinit var mSpinner: Spinner
    private lateinit var mDateButton: Button
    private lateinit var mDescription: TextView
    private lateinit var mRecordingStatus: TextView
    private lateinit var mRecordButton: FloatingActionButton
    private lateinit var mSaveButton: Button
    private lateinit var mCancelButton: Button

    private lateinit var mSpeech: SpeechRecognizer
    private lateinit var mRecognizerIntent: Intent

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.expense_detail, container, false)

        mAmount = view.findViewById(R.id.amount)
        mAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.getAmount(s.toString())
            }

        })

        setupSpinner(view)

        mDateButton = view.findViewById(R.id.date_button)
        mDateButton.setOnClickListener {
            presenter.openDateDialog()
        }

        mDescription = view.findViewById(R.id.description_result)

        mRecordingStatus = view.findViewById(R.id.record_status)
        presenter.hasPermission()
        mRecordButton = view.findViewById(R.id.mic_recorder)
        mRecordButton.setOnClickListener {
            if (presenter.hasPermission()) {
                mSpeech.startListening(mRecognizerIntent)
            } else {
                presenter.requestPermission()
            }
        }

        mSaveButton = view.findViewById(R.id.save_button)
        mSaveButton.setOnClickListener {
            presenter.saveExpense()
        }

        mCancelButton = view.findViewById(R.id.cancel_button)
        mCancelButton.setOnClickListener {
            presenter.cancelExpense()
        }

        mSpeech = SpeechRecognizer.createSpeechRecognizer(activity)
                .also { it.setRecognitionListener(this) }

        mRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                .apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en")
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
                    putExtra("android.speech.extra.DICTATION_MODE", true)
                }

        return view
    }

    override fun onPause() {
        super.onPause()
        mSpeech.destroy()
    }

    private fun setupSpinner(view: View) {
        mSpinner = view.findViewById(R.id.spinner)
        val arrayAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.categories,
                android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        mSpinner.onItemSelectedListener = (object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (val selection: String = parent?.getItemAtPosition(position) as String) {
                    getString(R.string.movies) -> presenter.getCategory(selection)
                    getString(R.string.food) -> presenter.getCategory(selection)
                    getString(R.string.bills) -> presenter.getCategory(selection)
                    getString(R.string.loan) -> presenter.getCategory(selection)
                    getString(R.string.rent) -> presenter.getCategory(selection)
                    getString(R.string.drinks) -> presenter.getCategory(selection)
                    getString(R.string.clothing) -> presenter.getCategory(selection)
                    getString(R.string.health_care) -> presenter.getCategory(selection)
                    getString(R.string.education) -> presenter.getCategory(selection)
                    getString(R.string.household) -> presenter.getCategory(selection)
                }

                presenter.getCategoryId(position)
            }
        })

        mSpinner.adapter = arrayAdapter
    }

    override fun showAmount(amount: String) {
        mAmount.setText(amount)
    }

    override fun setCategoryId(categoryId: Int) {
        mSpinner.setSelection(categoryId)
    }

    override fun showDate(date: String) {
        mDateButton.text = date
    }

    override fun showDescription(description: String) {
        mDescription.text = description
    }

    override fun showDateDialog(date: Date) {
        val manager = fragmentManager
        DateDialog.newInstance(date).apply {
            setTargetFragment(this@AddEditExpenseFragment, REQUEST_DATE)
            show(manager, "DateDialog")
        }
    }

    override fun showExpenseListUI() {
        activity?.finish()
    }

    override fun showPermissionDialog() {
        requestPermissions(PERMISSION, REQUEST_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.activityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        presenter.permissionResult(requestCode)
    }

    override fun onReadyForSpeech(params: Bundle?) {

    }

    override fun onRmsChanged(rmsdB: Float) {

    }

    override fun onBufferReceived(buffer: ByteArray?) {

    }

    override fun onPartialResults(partialResults: Bundle?) {

    }

    override fun onEvent(eventType: Int, params: Bundle?) {

    }

    override fun onBeginningOfSpeech() {
        mRecordingStatus.text = getString(R.string.recording_message)
    }

    override fun onEndOfSpeech() {
        mRecordingStatus.text = getString(R.string.converting_to_text)
    }

    override fun onError(error: Int) {

    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        var text = ""
        if (matches != null) {
            for (item in matches)
                text += item + "\n"
        }
        mRecordingStatus.visibility = View.INVISIBLE
        mDescription.text = text
    }
}