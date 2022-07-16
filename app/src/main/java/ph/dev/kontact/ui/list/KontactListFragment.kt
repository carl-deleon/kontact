package ph.dev.kontact.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ph.dev.kontact.R
import ph.dev.kontact.common.BaseFragment
import ph.dev.kontact.common.viewBinding
import ph.dev.kontact.data.model.KontactDetail
import ph.dev.kontact.databinding.FragmentKontactListBinding

class KontactListFragment : BaseFragment(R.layout.fragment_kontact_list) {

    private val binding by viewBinding(FragmentKontactListBinding::bind)

    override val viewModel by viewModels<KontactListViewModel>()

    private val adapter: KontactAdapter = KontactAdapter(itemClickListener())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.kontactListRecyclerView.adapter = adapter

        binding.swipeRefreshLayout.setOnRefreshListener { getKontactList() }

        viewModel.kontacts.observe(viewLifecycleOwner) {
            binding.swipeRefreshLayout.isRefreshing = false
            adapter.submitList(it)
        }

        binding.addContactButton.setOnClickListener {
            findNavController().navigate(R.id.to_add_kontact)
        }

        getKontactList()

        setFragmentResultListener("add_contact") { _, bundle ->
            val detail = bundle.getParcelable<KontactDetail>("detail")
            if (detail != null) viewModel.addKontact(detail)
        }

        setFragmentResultListener("delete") { _, bundle ->
            val id = bundle.getString("id")
            if (id != null) viewModel.deleteKontact(id)
        }
    }

    private fun getKontactList() {
        viewModel.getKontactList()
    }

    private fun itemClickListener(): (KontactDetail) -> Unit = {
        val direction = KontactListFragmentDirections.toKontactDetail(it)
        findNavController().navigate(direction)
    }
}