package ph.dev.kontact.ui.edit

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ph.dev.kontact.R
import ph.dev.kontact.common.textChanges
import ph.dev.kontact.common.viewBinding
import ph.dev.kontact.data.model.KontactDetail
import ph.dev.kontact.databinding.FragmentAddKontactBinding
import timber.log.Timber

class EditKontactFragment : Fragment(R.layout.fragment_add_kontact) {

    private val args by navArgs<EditKontactFragmentArgs>()
    private val kontactDetail: KontactDetail by lazy { args.kontactDetail }

    private val binding by viewBinding(FragmentAddKontactBinding::bind)
    private val viewModel by viewModels<EditKontactViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) {
            binding.submitButton.isEnabled = it.updateButtonEnabled
        }

        viewModel.successEvent.observe(viewLifecycleOwner) {
            setFragmentResult("edit_contact", bundleOf("detail" to it))
            findNavController().navigateUp()
        }

        binding.inputName.setText(kontactDetail.name)
        binding.inputEmailAddress.setText(kontactDetail.emailAddress)
        binding.inputPhoneNumber.setText(kontactDetail.contactNumber.trimCountryCode())
        binding.inputCompanyName.setText(kontactDetail.companyName)

        binding.submitButton.setOnClickListener {
            viewModel.submit()
        }

        combine(
            binding.inputName.textChanges(),
            binding.inputPhoneNumber.textChanges(),
            binding.inputEmailAddress.textChanges(),
            binding.inputCompanyName.textChanges()
        ) { name, phoneNumber, emailAddress, companyName ->
            EditKontactParam(name, emailAddress, phoneNumber, companyName)
        }
            .onEach { viewModel.validate(it) }
            .launchIn(lifecycleScope)
    }

    private fun String.trimCountryCode(): String {
        return replace("+63", "")
            .replace(" ", "")
            .trim()
    }
}