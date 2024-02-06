package idea.pti.insaf.purchi.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import idea.pti.insaf.purchi.R
import idea.pti.insaf.purchi.api.Voter


class VoterAdapter(val context: Context, private var voters: List<Voter>) :
    RecyclerView.Adapter<VoterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textUrdu: TextView = itemView.findViewById(R.id.textUrdu1)
        val share = itemView.findViewById<Button>(R.id.shareImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_voter_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voter = voters[position]

        holder.itemView.tag = voter
        holder.textUrdu.text = voter.toShare()
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = VoterDetailsActivity.newIntent(context, holder.itemView.tag as Voter)
            context.startActivity(intent)
        }

        holder.share.setOnClickListener {
            openWhatsAppGroup(voter.toShare())
        }
    }

    private fun openWhatsAppGroup(textToShare: String) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textToShare)
            type = "text/plain"
            `package` = "com.whatsapp"  // Specify the package name of WhatsApp
        }

        try {
            context.startActivity(sendIntent)
        } catch (e: Exception) {
            // Handle exception if WhatsApp is not installed
            // You can show a Toast or provide a fallback mechanism
        }
    }


    override fun getItemCount(): Int {
        return voters.size
    }

    fun updateData(voters: List<Voter>?) {
        this.voters = voters?: arrayListOf()
        notifyDataSetChanged()
    }
}
