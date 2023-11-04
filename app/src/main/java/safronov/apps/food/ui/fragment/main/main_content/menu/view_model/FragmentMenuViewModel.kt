package safronov.apps.food.ui.fragment.main.main_content.menu.view_model

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.remote.category.GetFoodCategoriesRemoteUseCase
import safronov.apps.domain.use_case.remote.food.GetFoodsByCategoryRemoteUseCase
import safronov.apps.food.ui.base.ViewModelBase
import safronov.apps.food.ui.base.coroutines.DispatchersList
import safronov.apps.food.ui.system.network.ConnectivityObserver

class FragmentMenuViewModel(
    dispatchersList: DispatchersList,
    private val connectivityObserver: ConnectivityObserver,
    private val getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase,
    private val getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase,
    private val getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase,
    private val getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase
): ViewModelBase.Base(dispatchersList = dispatchersList) {

    private val connectivityStatus = MutableStateFlow<ConnectivityObserver.Status?>(null)

    fun loadFoodsCategoriesAndFoods() = runBlocking {

    }

    fun getFoodCategories(): StateFlow<List<FoodCategoryItem>> {

    }

    fun getFoods(): StateFlow<List<FoodItem>> {

    }

    fun getConnectivityStatus(): StateFlow<ConnectivityObserver.Status?> {
        return connectivityStatus
    }

    private fun networkConnectivityObserver() = viewModelScope.launch {
        connectivityObserver.observe().collect {
            connectivityStatus.value = it
        }
    }

}
