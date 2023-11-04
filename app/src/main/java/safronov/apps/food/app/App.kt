package safronov.apps.food.app

import android.app.Application
import safronov.apps.food.di.component.AppComponent
import safronov.apps.food.di.component.DaggerAppComponent
import safronov.apps.food.di.module.AppModule

class App: Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(context = this)).build()
    }

    fun getAppComponent(): AppComponent? {
        return appComponent
    }

}