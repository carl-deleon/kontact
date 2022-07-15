package ph.dev.kontact.ui.add

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ph.dev.kontact.R
import ph.dev.kontact.common.BaseFragment
import ph.dev.kontact.common.textChanges
import ph.dev.kontact.common.viewBinding
import ph.dev.kontact.databinding.FragmentAddKontactBinding

class AddKontactFragment : BaseFragment(R.layout.fragment_add_kontact) {

    private val binding by viewBinding(FragmentAddKontactBinding::bind)

    override val viewModel by viewModels<AddKontactViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.submitButton.isEnabled = it.sendButtonEnabled
        }

        viewModel.successEvent.observe(viewLifecycleOwner) {
            setFragmentResult("add_contact", bundleOf("detail" to it))
            findNavController().navigateUp()
        }

        binding.submitButton.setOnClickListener {
            viewModel.submit()
        }
    }

    private fun setupViews() {
        combine(
            binding.inputName.textChanges(),
            binding.inputPhoneNumber.textChanges(),
            binding.inputEmailAddress.textChanges(),
            binding.inputCompanyName.textChanges()
        ) { name, phoneNumber, emailAddress, companyName ->
            AddKontactParam(name, emailAddress, phoneNumber, companyName)
        }
            .onEach { viewModel.validate(it) }
            .launchIn(lifecycleScope)
    }
}