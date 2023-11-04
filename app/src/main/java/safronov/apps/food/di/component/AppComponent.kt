package safronov.apps.food.di.component

import dagger.Component
import safronov.apps.food.di.module.AppModule
import safronov.apps.food.di.module.DataModule
import safronov.apps.food.di.module.DomainModule
import safronov.apps.food.ui.fragment.main.main_content.menu.FragmentMenu

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {

    fun inject(fragmentMenu: FragmentMenu)

}