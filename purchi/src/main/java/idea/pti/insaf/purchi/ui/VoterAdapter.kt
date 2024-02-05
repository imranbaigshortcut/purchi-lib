package idea.pti.insaf.purchi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import idea.pti.insaf.purchi.R
import idea.pti.insaf.purchi.api.Voter


class VoterAdapter(private var voters: List<Voter>) :
    RecyclerView.Adapter<VoterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textUrdu: TextView = itemView.findViewById(R.id.textUrdu1)
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
    }

    override fun getItemCount(): Int {
        return voters.size
    }

    fun updateData(voters: List<Voter>?) {
        this.voters = voters?: arrayListOf()
        notifyDataSetChanged()
    }
}
