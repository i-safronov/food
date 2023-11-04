package safronov.apps.food.ui.fragment.main.main_content.menu.view_model

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.remote.category.GetFoodCategoriesRemoteUseCase
import safronov.apps.domain.use_case.remote.food.GetFoodsByCategoryRemoteUseCase
import safronov.apps.food.ui.base.ViewModelBase
import safronov.apps.food.ui.base.coroutines.DispatchersList
import safronov.apps.food.ui.system.network.ConnectivityObserver
import java.lang.Exception
import java.lang.RuntimeException

class FragmentMenuViewModel(
    dispatchersList: DispatchersList,
    private val connectivityObserver: ConnectivityObserver,
    private val getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase,
    private val getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase,
    private val getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase,
    private val getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase
): ViewModelBase.Base(dispatchersList = dispatchersList) {

    private val foodCategories = MutableStateFlow<List<FoodCategoryItem>?>(null)
    private val foods = MutableStateFlow<List<FoodItem>?>(null)
    private val isException = MutableStateFlow<Exception?>(null)
    private val currentFoodCategory = MutableStateFlow<FoodCategoryItem?>(null)

    fun getFoodCategories(): StateFlow<List<FoodCategoryItem>?> = foodCategories
    fun getFoods(): StateFlow<List<FoodItem>?> = foods
    fun getCurrentFoodCategory(): StateFlow<FoodCategoryItem?> = currentFoodCategory
    fun getIsException(): StateFlow<Exception?> = isException

    fun saveCurrentFoodCategory(category: FoodCategoryItem) {
        currentFoodCategory.value = category
    }


    fun loadFoodsCategoriesAndFoods() {
        asyncWork(
            prepareUI = {},
            doWork = {
                if (connectivityObserver.isAccessToNetwork()) {
                    loadFoodsCategoriesAndFoodsFromNetwork()
                } else {
                    loadFoodsCategoriesAndFoodsFromLocalDatabase()
                }
            }, showUI = {  }, handleException = {
                isException.value = it
            }
        )
    }

    private suspend fun loadFoodsCategoriesAndFoodsFromNetwork() {
        foodCategories.value = getFoodCategoriesRemoteUseCase.execute().categories
        if (currentFoodCategory.value == null) {
            currentFoodCategory.value = foodCategories.value?.first()
        }
        currentFoodCategory.value?.let {
            foods.value = getFoodsByCategoryRemoteUseCase.execute(it).foodItems
        }
    }

    private suspend fun loadFoodsCategoriesAndFoodsFromLocalDatabase() {
        foodCategories.value = getFoodCategoriesLocalUseCase.execute()
        if (currentFoodCategory.value == null) {
            currentFoodCategory.value = foodCategories.value?.first()
        }
        currentFoodCategory.value?.let {
            foods.value = getFoodsByCategoryLocalUseCase.execute(it)
        }
    }

}
