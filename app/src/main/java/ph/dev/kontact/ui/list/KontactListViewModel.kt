package ph.dev.kontact.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ph.dev.kontact.common.BaseViewModel
import ph.dev.kontact.data.model.KontactDetail
import ph.dev.kontact.data.repository.KontactRepository

class KontactListViewModel : BaseViewModel() {

    private val _kontacts = MutableLiveData<List<KontactDetail>>(emptyList())
    val kontacts: LiveData<List<KontactDetail>> get() = _kontacts

    fun getKontactList() = safeApiCall {
        val updatedList = kontacts.value!!.toMutableList()

        val result = KontactRepository.getKontacts()

        updatedList.plusAssign(result)

        _kontacts.value = updatedList
            .distinctBy { it.id }
            .sortedBy { it.name }
    }

    fun addKontact(kontactDetail: KontactDetail) {
        val copyList = kontacts.value?.toMutableList()
        copyList?.add(kontactDetail)

        _kontacts.value = copyList
    }

    fun deleteKontact(id: String) {
        // Soft delete only, deleted data will return after pulling new data from API.
        val copyList = kontacts.value?.toMutableList()
        copyList?.removeIf { it.id == id }

        _kontacts.value = copyList
    }

    fun updateKontact(kontactDetail: KontactDetail) {
        val copyList = kontacts.value?.toMutableList()

        copyList
            ?.indexOfFirst { it.id == kontactDetail.id }
            ?.let { copyList.set(it, kontactDetail) }

        _kontacts.value = copyList
    }
}