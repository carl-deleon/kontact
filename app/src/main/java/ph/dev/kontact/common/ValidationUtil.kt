package ph.dev.kontact.common

fun String.isValidEmailAddress(): Boolean {
    return isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}