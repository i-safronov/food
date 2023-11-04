package safronov.apps.food.ui.fragment.main.main_content.menu.view_model

import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.remote.category.GetFoodCategoriesRemoteUseCase
import safronov.apps.domain.use_case.remote.food.GetFoodsByCategoryRemoteUseCase
import safronov.apps.food.ui.base.coroutines.DispatchersList
import safronov.apps.food.ui.system.network.ConnectivityObserver

class FragmentMenuViewModel(
    private val dispatchersList: DispatchersList,
    private val connectivityObserver: ConnectivityObserver,
    private val getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase,
    private val getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase,
    private val getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase,
    private val getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase
) {

}
