package kg.sunrise.dasslerpro

import android.app.Application
import android.content.Context
import com.sunrise.adeliya.utils.timber.CrashReportingTree
import kg.sunrise.dasslerpro.modules.networkModule
import kg.sunrise.dasslerpro.modules.repositoryModule
import kg.sunrise.dasslerpro.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        context = this

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    networkModule
                )
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}