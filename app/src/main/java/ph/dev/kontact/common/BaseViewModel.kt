package ph.dev.kontact.common

import androidx.lifecycle.*
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

    fun <T> safeApiCallLiveData(action: suspend () -> T) = liveData {
        try {
            emit(action())
        } catch (e: Exception) {
            errorHandler.handleError(e, _errorLiveData)
        }
    }
}