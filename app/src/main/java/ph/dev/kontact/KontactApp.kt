package ph.dev.kontact

import android.app.Application
import timber.log.Timber

class KontactApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}