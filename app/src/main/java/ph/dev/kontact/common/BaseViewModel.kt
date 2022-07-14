package ph.dev.kontact.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    private val errorHandler: ErrorHandler = DefaultErrorHandler()
) : ViewModel() {

    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> get() = _errorLiveData

    fun safeApiCall(
        defaultCoroutineContext: CoroutineContext = Dispatchers.IO,
        action: suspend () -> Unit
    ) {
        viewModelScope.launch(defaultCoroutineContext) {
            withContext(Dispatchers.Main) {
                try {
                    action()
                } catch (e: Exception) {
                    errorHandler.handleError(e, _errorLiveData)
                }
            }
        }
    }
}