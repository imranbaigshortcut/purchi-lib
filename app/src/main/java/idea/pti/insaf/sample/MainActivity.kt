package idea.pti.insaf.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import idea.pti.insaf.purchi.PurchiLib
import idea.pti.insaf.purchi.ui.CandidateSelectionActivity
import idea.pti.insaf.purchi.ui.PurchiListActivity
import idea.pti.insaf.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root


        setContentView(view)

        val scanner = GmsBarcodeScanning.getClient(this)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.sendButton.setOnClickListener {
            val maxLength = 13
            binding.idCardEditText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))

            val idCardNumber = binding.idCardEditText.text.toString()

            if (validateIdCardNumber(idCardNumber)) {
                openVoterList(idCardNumber)
            } else {
                Toast.makeText(this, "صحیح آئی ڈی کارڈ نمبر ٹائپ کریں", Toast.LENGTH_LONG).show()
            }

            binding.idCardEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Not needed in this case
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Not needed in this case
                }

                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        if (it.length > 13) {
                            // Remove excess characters to limit to 13 digits
                            it.delete(13, it.length)
                        }
                    }
                }
            })
        }

        binding.scanButton.setOnClickListener {
            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    val rawValue: String? = barcode.rawValue
                    rawValue?.let {
                        if (validateIdCardNumberRaw(it)) {
                            openVoterList(extractAndRemoveLastDigit(it))
                            finish()
                        } else {
                            Toast.makeText(this, " درست  کوڈ اسکین کری", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                .addOnCanceledListener {
                    // Task canceled
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                }
        }

        binding.idCardEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND ||
                (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)
            ) {
                val idCardNumber = binding.idCardEditText.text.toString()
                openVoterList(idCardNumber)

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.form45.setOnClickListener {
            formSubmit()
        }

    }

    private fun validateIdCardNumber(idCardNumber: String): Boolean {
        return idCardNumber.isDigitsOnly() && idCardNumber.length == 13
    }

    fun extractAndRemoveLastDigit(inputString: String): String {
        // Ensure the input is at least 14 characters long
        if (inputString.length >= 14) {
            // Extract the last 14 digits
            val last14Digits = inputString.takeLast(14)

            // Remove the last digit
            val result13Digits = last14Digits.dropLast(1)

            return result13Digits
        } else {
            return "Input string should be at least 14 characters long."
        }
    }

    private fun validateIdCardNumberRaw(idCardNumber: String): Boolean {
        return idCardNumber.isDigitsOnly() && idCardNumber.length > 14
    }

    private fun openVoterList(idCardNumber: String) {

        PurchiLib.baseUrl = "https://bb0cbe11c6e4c611.azadvoter.com/api/"
        PurchiLib.secret = "XoX0i5xqJQ526jn3mWIgIpvHCbCshYHMzdN1XZEJoj1PJb062WqSVZEtBVYfUVDmmpET8BDkhuwQFVXAazdb1eZ7AiNZ9fybYBwEYwZIsXK98apqln8vktiDuQwOIXi3"


        val intent = Intent(this, PurchiListActivity::class.java)
        intent.putExtra("cnic", idCardNumber)
        startActivity(intent)
    }

    private fun formSubmit() {

        val intent = Intent(this, CandidateSelectionActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the back button click
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}