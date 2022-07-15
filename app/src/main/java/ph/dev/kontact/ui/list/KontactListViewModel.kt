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

        _kontacts.value = updatedList.distinct().sortedBy { it.name }
    }

    fun addKontact(kontactDetail: KontactDetail) {
        val copyList = kontacts.value?.toMutableList()
        copyList?.add(kontactDetail)

        _kontacts.value = copyList
    }
}