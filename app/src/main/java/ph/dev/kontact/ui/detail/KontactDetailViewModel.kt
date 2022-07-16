package ph.dev.kontact.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ph.dev.kontact.common.BaseViewModel
import ph.dev.kontact.data.repository.KontactRepository

class KontactDetailViewModel(savedStateHandle: SavedStateHandle) : BaseViewModel() {

    private val args = KontactDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    val deleteSuccessEvent = MutableLiveData<Unit>()

    fun delete() {
        safeApiCall {
            KontactRepository.deleteKontact(args.kontactDetail.id)
            deleteSuccessEvent.value = Unit
        }
    }
}