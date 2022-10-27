package top.xjunz.tasker

import android.app.Application
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources.Theme
import android.os.Build
import androidx.appcompat.view.ContextThemeWrapper
import org.lsposed.hiddenapibypass.HiddenApiBypass

/**
 * @author xjunz 2021/6/25
 */
val isInHostProcess: Boolean get() = App.instance != null

val isInRemoteProcess: Boolean get() = !isInHostProcess

val app: App get() = requireNotNull(App.instance)

class App : Application() {

    lateinit var appTheme: Theme
        private set

    companion object {

        var instance: App? = null
            private set

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        generateAppTheme()
    }

    private fun generateAppTheme() {
        appTheme = ContextThemeWrapper(this, R.style.AppTheme).theme
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            HiddenApiBypass.addHiddenApiExemptions("")
        }
        generateAppTheme()
    }

    fun sharedPrefsOf(name: String): SharedPreferences {
        return getSharedPreferences(name, MODE_PRIVATE)
    }
}

