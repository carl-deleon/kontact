package ph.dev.kontact.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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

        if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            getKontactList()
        }
    }

    private fun getKontactList() {
        viewModel.getKontactList()
    }

    private fun itemClickListener(): (KontactDetail) -> Unit = {
        // TODO Navigate to Detail Screen
    }
}