package ph.dev.kontact.ui.edit

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ph.dev.kontact.common.BaseViewModel
import ph.dev.kontact.common.isValidEmailAddress
import ph.dev.kontact.data.dto.EditKontactRequest
import ph.dev.kontact.data.model.KontactDetail
import ph.dev.kontact.data.repository.KontactRepository

class EditKontactViewModel(savedStateHandle: SavedStateHandle) : BaseViewModel() {

    private val args = EditKontactFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _uiState = MutableLiveData(EditKontactState())

    // Create param using original data
    private val originalData = EditKontactParam.from(args.kontactDetail)

    val uiState: LiveData<EditKontactState> get() = _uiState

    val successEvent = MutableLiveData<KontactDetail>()

    fun validate(param: EditKontactParam) {
        _uiState.value =
            uiState.value?.copy(
                param = param,
                updateButtonEnabled = (param != originalData)
                        && param.name.isNotEmpty()
                        && param.emailAddress.isValidEmailAddress()
                        && param.phoneNumber.isNotEmpty()
            )
    }

    fun submit() {
        uiState.value?.param?.let { param ->
            safeApiCall {
                val formattedNumber = PhoneNumberUtils.formatNumber("+63${param.phoneNumber}", "PH")

                val request = EditKontactRequest(
                    name = param.name,
                    emailAddress = param.emailAddress,
                    phoneNumber = formattedNumber,
                    companyName = param.companyName
                )

                KontactRepository.editKontact(args.kontactDetail.id, request)

                val fakeResponse = KontactDetail(
                    id = args.kontactDetail.id,
                    name = param.name,
                    emailAddress = param.emailAddress,
                    contactNumber = formattedNumber,
                    companyName = param.companyName,
                    profileImage = args.kontactDetail.profileImage
                )

                successEvent.value = fakeResponse
            }
        }
    }
}