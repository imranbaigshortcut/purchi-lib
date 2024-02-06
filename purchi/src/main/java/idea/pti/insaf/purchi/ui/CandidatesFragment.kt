package idea.pti.insaf.purchi.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import candidates.insaf.pk.list.ui.candidate.CandidatesViewModel
import idea.pti.insaf.purchi.databinding.FragmentCandidatesBinding
import idea.pti.insaf.purchi.list.Repo

open class CandidatesFragment(val id: String = "all") : Fragment() {

    private var _binding: FragmentCandidatesBinding? = null
    var viewModel: CandidatesViewModel? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel =
            ViewModelProvider(this).get(CandidatesViewModel::class.java)

        _binding = FragmentCandidatesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        var candidateAdapter = CandidateAdapter(Repo.allCandidates)

        binding.candidatesList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.requireActivity())
        binding.candidatesList.adapter = candidateAdapter

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                candidateAdapter.filter.filter(s)
            }
        })

        _binding?.na?.setOnClickListener {
            _binding?.searchEditText?.setText("National Assembly")
        }
        _binding?.pk?.setOnClickListener {
            _binding?.searchEditText?.setText("KPK")
        }
        _binding?.pp?.setOnClickListener(View.OnClickListener {
            _binding?.searchEditText?.setText("Punjab")

        })
        _binding?.ps?.setOnClickListener {
            _binding?.searchEditText?.setText("Sindh")
        }

        _binding?.pb?.setOnClickListener {
            _binding?.searchEditText?.setText("Balochistan")
        }


        viewModel?.updateResult?.observe(this.requireActivity()) { result ->
            when (result) {
                is CandidatesViewModel.UpdateResult.Completed -> {
                    candidateAdapter = CandidateAdapter(Repo.allCandidates)
                    binding.candidatesList.adapter = candidateAdapter
                    candidateAdapter.notifyDataSetChanged()
                }
                else -> {}
            }
        }

        viewModel?.load(this.requireContext())
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class NationalCandidatesFragment : CandidatesFragment("all")
class PunjabCandidatesFragment : CandidatesFragment("pp")
class SindhCandidatesFragment : CandidatesFragment("ps")
class KPCandidatesFragment : CandidatesFragment("pk")
class BalochistanCandidatesFragment : CandidatesFragment("pb")
