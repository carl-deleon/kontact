package ph.dev.kontact.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ph.dev.kontact.common.BaseViewModel
import ph.dev.kontact.data.model.KontactDetail
import ph.dev.kontact.data.repository.KontactRepository

class KontactListViewModel : BaseViewModel() {

    private val _kontacts = MutableLiveData<List<KontactDetail>>()
    val kontacts: LiveData<List<KontactDetail>> get() = _kontacts

    fun getKontactList() = safeApiCall {
        _kontacts.value = KontactRepository.getKontacts()
    }
}