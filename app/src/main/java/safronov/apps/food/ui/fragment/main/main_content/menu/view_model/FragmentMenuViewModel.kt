package safronov.apps.food.ui.fragment.main.main_content.menu.view_model

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
import safronov.apps.food.ui.exception.UiException
import safronov.apps.food.ui.system.network.ConnectivityObserver

class FragmentMenuViewModel(
    private val dispatchersList: DispatchersList,
    private val connectivityObserver: ConnectivityObserver,
    private val getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase,
    private val getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase,
    private val getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase,
    private val getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase
): ViewModelBase.Base(dispatchersList = dispatchersList) {

    //TODO напиши логику которая будет складывать все jobs от задач и при исчезновении/появлении интернет загружать либо с бд либо с сервера

    private val connectivityStatus = MutableStateFlow<ConnectivityObserver.Status?>(null)
    private val foodCategories = MutableStateFlow<List<FoodCategoryItem>?>(null)
    private val foods = MutableStateFlow<List<FoodItem>?>(null)
    private val isException = MutableStateFlow<UiException?>(null)
    private val currentFoodCategory = MutableStateFlow<FoodCategoryItem?>(null)

    private val networkJobs = mutableListOf<Job>()
    private val localJobs = mutableListOf<Job>()

    fun getFoodCategories(): StateFlow<List<FoodCategoryItem>?> {
        return foodCategories
    }

    fun getFoods(): StateFlow<List<FoodItem>?> {
        return foods
    }

    fun getConnectivityStatus(): StateFlow<ConnectivityObserver.Status?> {
        return connectivityStatus
    }

    fun getCurrentFoodCategory(): StateFlow<FoodCategoryItem?> = currentFoodCategory
    fun getIsException(): StateFlow<UiException?> = isException

    fun loadFoodsCategoriesAndFoods() {
        asyncWork(
            prepareUI = {},
            doWork = {
                if (connectivityObserver.isAccessToNetwork()) {
                    println("Do in network")
                    networkJobs.add(loadFoodsCategoriesAndFoodsFromNetwork())
                } else {
                    localJobs.add(loadFoodsCategoriesAndFoodsFromLocalDatabase())
                }
            }, showUI = {

            }, handleException = {
                setExceptionToUi(it as UiException)
            }
        )
    }

    private fun loadFoodsCategoriesAndFoodsFromNetwork(): Job = viewModelScope.launch(dispatchersList.io()) {
        foodCategories.value = getFoodCategoriesRemoteUseCase.execute().categories
        if (currentFoodCategory.value == null) {
            currentFoodCategory.value = foodCategories.value?.first()
        }
        if (currentFoodCategory.value == null) {
            setExceptionToUi(UiException("current food category is null"))
        } else {
            foods.value = getFoodsByCategoryRemoteUseCase.execute(currentFoodCategory.value!!).foodItems
        }
    }

    private fun loadFoodsCategoriesAndFoodsFromLocalDatabase(): Job = viewModelScope.launch(dispatchersList.io()) {
        foodCategories.value = getFoodCategoriesLocalUseCase.execute()
        if (currentFoodCategory.value == null) {
            currentFoodCategory.value = foodCategories.value?.first()
        }
        if (currentFoodCategory.value == null) {
            setExceptionToUi(UiException("current food category is null"))
        } else {
            foods.value = getFoodsByCategoryLocalUseCase.execute(currentFoodCategory.value!!)
        }
    }

    private fun setExceptionToUi(uiException: UiException?) {
        isException.value = uiException
        isException.value = null
    }

    fun observeNetworkConnection() {
        asyncWork(prepareUI = {}, doWork = {
            connectivityObserver.observe().collect {
                //TODO проверяй какое соединение и грузи даныне
                connectivityStatus.value = it
            }
        }, showUI = {

        }, handleException = {
            setExceptionToUi(it as UiException)
        })
    }

}
