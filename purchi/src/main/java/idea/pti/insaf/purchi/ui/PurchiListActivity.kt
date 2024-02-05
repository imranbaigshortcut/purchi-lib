package idea.pti.insaf.purchi.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider

import idea.pti.insaf.purchi.R
import idea.pti.insaf.purchi.databinding.ActivityPurchiListBinding
import idea.pti.insaf.purchi.api.Voter

class PurchiListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPurchiListBinding
    private lateinit var viewModel: PurchiListViewModel
    private var adapter: VoterAdapter = VoterAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPurchiListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this).get(PurchiListViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back_24)
        supportActionBar?.title = "Voter List"

        val id = intent.getStringExtra("cnic")?:""

        binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.voters.observe(this) {
            adapter.updateData(it)
        }

        viewModel.getVotersById(id)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Navigate back when the back button is pressed
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openWhatsAppGroup(context: Context, groupLink: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(groupLink)
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun createFakeVoterList(): List<Voter> {
        val fakeList = mutableListOf<Voter>()

        for (i in 1..10) {
            val voter = Voter(
                "Index$i",
                "BlockCode$i",
                "CNIC$i",
                "Family$i",
                "National$i",
                "Provincial$i",
                "Name$i",
                "FatherName$i",
                "PollingStationAddress$i"
            )
            fakeList.add(voter)
        }

        return fakeList
    }

    companion object {
        fun newIntent(context: Context?, cnic: String): Intent? {
            val intent = Intent(context, VoterDetailsActivity::class.java)
            intent.putExtra("cnic", cnic)
            return intent
        }
    }
}