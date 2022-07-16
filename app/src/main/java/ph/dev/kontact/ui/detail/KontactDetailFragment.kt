package ph.dev.kontact.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ph.dev.kontact.R
import ph.dev.kontact.common.viewBinding
import ph.dev.kontact.databinding.FragmentKontactDetailBinding

class KontactDetailFragment : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentKontactDetailBinding::bind)

    private val navArgs: KontactDetailFragmentArgs by navArgs()
    private val kontactDetail by lazy { navArgs.kontactDetail }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_kontact_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(kontactDetail) {
            binding.profileImage.load(profileImage) {
                transformations(CircleCropTransformation())
            }
            binding.displayNameTextView.text = name
            binding.emailAddressTextView.text = emailAddress
            binding.contactNumberTextView.text = contactNumber
            binding.companyNameTextView.text = companyName
        }

        binding.deleteButton.setOnClickListener { }
    }
}