package idea.pti.insaf.purchi.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import idea.pti.insaf.purchi.data.Candidate
import idea.pti.insaf.purchi.databinding.ActivityForm45Binding

class Form45Activity : AppCompatActivity() {

    private lateinit var binding: ActivityForm45Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForm45Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val candidate = intent.getParcelableExtra<Candidate>("candidate")

        binding.halqa.text = candidate?.constituency


        binding.btnSubmitForm.setOnClickListener {
            // Read values from other EditText views

            val editTextPollingStationNumber = binding.editTextPollingStationNumber.text.toString()
            val ptiVotes = binding.editTextPTI.text.toString()
            val pmlnVotes = binding.editTextPMLN.text.toString()
            val pppVotes = binding.editTextPPP.text.toString()
            val tlpVotes = binding.editTextTlp.text.toString()
            val iipVotes = binding.editTextIPP.text.toString()
            val otherParty = binding.editTextOtherParty.text.toString()
            val otherVotes = binding.editTextOther.text.toString()

        }

        binding.btnAddImage.setOnClickListener {
            ImagePicker.with(this)
                .compress(2024)
                .start()
        }

        binding.closest.setOnClickListener {
            finish()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            binding.imageViewForm45.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}
