package ph.dev.kontact.common

import android.os.Looper
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

fun checkMainThread() {
    check(Looper.myLooper() == Looper.getMainLooper()) {
        "Expected to be called on the main thread but was " + Thread.currentThread().name
    }
}

/**
 * Converts a [Flow] to an [InitialValueFlow], taking an [initialValue] lambda for computing the initial value.
 */
fun <T : Any> Flow<T>.asInitialValueFlow(initialValue: () -> T): InitialValueFlow<T> =
    InitialValueFlow(
        onStart {
            emit(initialValue())
        }
    )

/**
 * A [Flow] implementation that emits the current value of a widget immediately upon collection.
 */
class InitialValueFlow<T : Any>(private val flow: Flow<T>) : Flow<T> by flow {

    /**
     * Returns a [Flow] that skips the initial emission of the current value.
     */
    fun skipInitialValue(): Flow<T> = flow.drop(1)
}

fun EditText.textChanges(): InitialValueFlow<String> = callbackFlow {
    checkMainThread()
    val listener = doOnTextChanged { text, _, _, _ ->
        if (text != null) {
            trySend(text.toString())
        }
    }
    awaitClose { removeTextChangedListener(listener) }
}
    .conflate()
    .asInitialValueFlow { text.toString() }
