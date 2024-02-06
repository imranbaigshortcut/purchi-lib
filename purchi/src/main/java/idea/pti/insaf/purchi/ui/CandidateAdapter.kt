package idea.pti.insaf.purchi.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import idea.pti.insaf.purchi.data.Candidate
import idea.pti.insaf.purchi.R
import idea.pti.insaf.purchi.list.Repo
import idea.pti.insaf.purchi.ui.ImageLoader
import java.util.Locale

class CandidateAdapter(private var candidates: List<Candidate>) :
    RecyclerView.Adapter<CandidateAdapter.ViewHolder>(),
    Filterable {

    private var candidateListFull: List<Candidate> = candidates

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val relativeLayout: View = itemView.findViewById(R.id.relativeLayout)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textLocation: TextView = itemView.findViewById(R.id.textLocation)
        val textConstituency: TextView = itemView.findViewById(R.id.textConstituency)
        val textNameUrdu: TextView = itemView.findViewById(R.id.textNameUrdu)
        val form45: TextView = itemView.findViewById(R.id.form45)
        val whats: ImageView = itemView.findViewById(R.id.whats)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.candidate_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val candidate = candidates[position]

        // Bind data to views
        holder.textName.text = candidate.name
        holder.textLocation.text = candidate.location
        holder.textConstituency.text = candidate.constituency
        holder.textNameUrdu.text = candidate.urduName
        holder.itemView.tag = candidate

        val backgroundColor = if (candidate.constituency.startsWith("NA"))
            ContextCompat.getColor(holder.itemView.context, R.color.purchi)
            else 0xfff6f6f6.toInt()

        holder.relativeLayout.setBackgroundColor(backgroundColor)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Form45Activity::class.java)
            intent.putExtra("candidate", candidate)
            holder.itemView.context.startActivity(intent)
        }

        if (candidate.constituency.isNotEmpty()) {
            holder.whats.visibility = View.VISIBLE
        } else {
            holder.whats.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return candidates.size
    }



    override fun getFilter(): Filter {

        val pattern = Regex("^(na|pp|ps|pk|pb)[0-9]+", RegexOption.IGNORE_CASE)

        fun isHalqa(input: String): Boolean {
            val isMatch = pattern.matches(input)
            return isMatch
        }
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filteredList: MutableList<Candidate> = mutableListOf()


                if (constraint.isNullOrEmpty() || constraint?.length!! <2 ) {
                    filteredList.addAll(candidateListFull)
                } else {
                    val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim()

                    val list  = when(filterPattern) {
                        "na", "national assembly" -> Repo.na
                        "pp", "punjab" -> Repo.pp
                        "ps", "sindh" -> Repo.ps
                        "pk","kpk" -> Repo.pk
                        "pb","balochistan" -> Repo.pb
                        else -> null
                    }

                    if(list != null) {
                        filteredList.addAll(list.filter { it.constituency != "Constituency No" })
                    } else {
                        if(isHalqa(filterPattern)) {
                            Repo.filterMap[filterPattern]?.let { filteredList.add(it) }
                        } else {
                            for (item in candidateListFull) {
                                if (item.name.lowercase(Locale.getDefault()).contains(filterPattern) ||
                                    item.location.lowercase(Locale.getDefault()).contains(filterPattern) ||
                                    item.constituency.lowercase(Locale.getDefault()) == filterPattern ||
                                    item.constituencyMatch(filterPattern) ||
                                    item.urduName.lowercase(Locale.getDefault()).contains(filterPattern)
                                ) {
                                    filteredList.add(item)
                                }
                            }
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                if(constraint?.isEmpty() == false) {
                    candidates = results?.values as List<Candidate>
                    candidates = candidates.sortedBy { it.constituencyNumber}
                    candidates = candidates.groupBy { it.location }.flatMap { it.value }
                } else {
                    candidates  = candidateListFull
                }

                notifyDataSetChanged()
            }
        }
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
}
