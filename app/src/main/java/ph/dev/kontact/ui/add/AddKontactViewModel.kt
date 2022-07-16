package ph.dev.kontact.ui.add

import android.telephony.PhoneNumberUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ph.dev.kontact.common.BaseViewModel
import ph.dev.kontact.common.isValidEmailAddress
import ph.dev.kontact.data.dto.AddKontactRequest
import ph.dev.kontact.data.model.KontactDetail
import ph.dev.kontact.data.repository.KontactRepository

class AddKontactViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData(AddKontactState())
    val uiState: LiveData<AddKontactState> get() = _uiState

    val successEvent = MutableLiveData<KontactDetail>()

    fun validate(param: AddKontactParam) {
        _uiState.value = uiState.value?.copy(
            param = param,
            sendButtonEnabled = param.name.isNotEmpty() && param.emailAddress.isValidEmailAddress() && param.phoneNumber.isNotEmpty()
        )
    }

    fun submit() {
        uiState.value?.param?.let { param ->
            safeApiCall {
                val formattedNumber = PhoneNumberUtils.formatNumber("+63${param.phoneNumber}", "PH")

                val request = AddKontactRequest(
                    name = param.name,
                    emailAddress = param.emailAddress,
                    phoneNumber = formattedNumber,
                    companyName = param.companyName
                )

                // Our mock API can only return predefined values.
                // If request is successful, return entered values instead of response from the mock API.

                KontactRepository.addKontact(request) // Ignore response. we only need if the request is successful or not.

                val fakeResponse = KontactDetail(
                    id = giveRandomId().toString(),
                    name = param.name,
                    emailAddress = param.emailAddress,
                    contactNumber = formattedNumber,
                    companyName = param.companyName,
                    profileImage = giveRandomImage()
                )

                successEvent.value = fakeResponse
            }
        }
    }

    private fun giveRandomId() = (1001..9009).random()

    private fun giveRandomImage() = listOf(
        "https://cdn2.thecatapi.com/images/cdd.jpg",
        "https://cdn2.thecatapi.com/images/da5.jpg",
        "https://cdn2.thecatapi.com/images/ecr.jpg",
        "https://cdn2.thecatapi.com/images/MTgzMDI0NA.jpg",
        "https://cdn2.thecatapi.com/images/2b2pFY0-t.jpg",
    ).random()
}