package idea.pti.insaf.purchi.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import idea.pti.insaf.purchi.R
import idea.pti.insaf.purchi.api.Voter
import idea.pti.insaf.purchi.databinding.ActivityVoterDetailsBinding

class VoterDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVoterDetailsBinding
    private var voter: Voter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back_24)
        supportActionBar?.title = "Voter Details"

        voter = intent.getParcelableExtra("VOTER_EXTRA")

        binding.textUrdu.text = voter?.toShare()
        binding.buttonShare.setOnClickListener {
            openWhatsAppGroup(voter?.toShare()?:"")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Navigate back when the back button is pressed
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openWhatsAppGroup(textToShare: String) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
            `package` = "com.whatsapp"  // Specify the package name of WhatsApp
        }

        try {
            startActivity(sendIntent)
        } catch (e: Exception) {
            // Handle exception if WhatsApp is not installed
            // You can show a Toast or provide a fallback mechanism
        }
    }

    companion object {
        fun newIntent(context: Context?, voter: Voter): Intent? {
            val intent = Intent(context, VoterDetailsActivity::class.java)
            intent.putExtra("VOTER_EXTRA", voter)
            return intent
        }
    }
}