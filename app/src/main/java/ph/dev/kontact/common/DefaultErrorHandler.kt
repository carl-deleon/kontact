package ph.dev.kontact.common

import androidx.lifecycle.MutableLiveData

class DefaultErrorHandler : ErrorHandler {

    override fun handleError(error: Throwable, errorLiveData: MutableLiveData<Throwable>) {
        errorLiveData.value = error
    }
}

interface ErrorHandler {

    fun handleError(error: Throwable, errorLiveData: MutableLiveData<Throwable>)
}