package safronov.apps.food.ui.fragment.main.main_content.menu.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.remote.category.GetFoodCategoriesRemoteUseCase
import safronov.apps.domain.use_case.remote.food.GetFoodsByCategoryRemoteUseCase
import safronov.apps.food.ui.base.coroutines.DispatchersList
import safronov.apps.food.ui.fragment.main.main_content.menu.banner.BannerService
import safronov.apps.food.ui.system.network.ConnectivityObserver

class FragmentMenuViewModelFactory(
    private val dispatchersList: DispatchersList,
    private val connectivityObserver: ConnectivityObserver,
    private val getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase,
    private val getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase,
    private val getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase,
    private val getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase,
    private val bannerService: BannerService
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FragmentMenuViewModel(
            dispatchersList,
            connectivityObserver,
            getFoodCategoriesRemoteUseCase,
            getFoodsByCategoryRemoteUseCase,
            getFoodCategoriesLocalUseCase,
            getFoodsByCategoryLocalUseCase,
            bannerService
        ) as T
    }

}