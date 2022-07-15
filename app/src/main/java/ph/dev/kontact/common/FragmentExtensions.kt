package ph.dev.kontact.common

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ph.dev.kontact.R

fun Fragment.showErrorDialog(
    title: String = getString(R.string.title_error),
    errorMessage: String? = null,
    positiveButtonText: String = getString(R.string.dialog_default_positive_button_text)
) {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(errorMessage)
        .setPositiveButton(positiveButtonText) { dialog, _ ->
            // Respond to positive button press
            dialog.dismiss()
        }
        .show()
}