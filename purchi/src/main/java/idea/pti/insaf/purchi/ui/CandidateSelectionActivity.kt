package idea.pti.insaf.purchi.ui

import android.os.Build.VERSION_CODES.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import idea.pti.insaf.purchi.R
import idea.pti.insaf.purchi.databinding.ActivityCandidateSelectionBinding

class CandidateSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCandidateSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCandidateSelectionBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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