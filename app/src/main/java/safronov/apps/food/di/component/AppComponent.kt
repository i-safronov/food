package safronov.apps.food.di.component

import dagger.Component
import safronov.apps.food.ui.fragment.main.main_content.menu.FragmentMenu

@Component
interface AppComponent {

    fun inject(fragmentMenu: FragmentMenu)

}