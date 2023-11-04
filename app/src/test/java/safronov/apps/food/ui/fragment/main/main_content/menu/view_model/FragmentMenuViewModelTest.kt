package safronov.apps.food.ui.fragment.main.main_content.menu.view_model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal
import safronov.apps.domain.repository.local.FoodRepositoryLocal
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.category.SaveFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.local.food.SaveFoodsLocalUseCase
import safronov.apps.domain.use_case.remote.category.GetFoodCategoriesRemoteUseCase
import safronov.apps.domain.use_case.remote.food.GetFoodsByCategoryRemoteUseCase
import safronov.apps.food.ui.base.coroutines.DispatchersList
import safronov.apps.food.ui.system.network.ConnectivityObserver
import java.lang.IllegalStateException

/*
* actions:
* - get data from internet when network is available
* - get data when network is unavailable(should get data from database)
* - get data from internet when network is available but when calling request, network has been unavailable(should cancel job to get data from
* internet and get data from database)
* - get data from database(when network is unavailable) but when calling request, should get data from database 
* */

class FragmentMenuViewModelTest {

    private lateinit var fakeFoodCategoryRepositoryLocalGetting: FakeFoodCategoryRepositoryLocalGetting
    private lateinit var fakeFoodCategoryRepositoryLocalSaving: FakeFoodCategoryRepositoryLocalSaving
    private lateinit var fakeFoodRepositoryLocalGetting: FakeFoodRepositoryLocalGetting
    private lateinit var fakeFoodRepositoryLocalSaving: FakeFoodRepositoryLocalSaving
    private lateinit var fakeFoodCategoryRepositoryRemoteGetting: FakeFoodCategoryRepositoryRemoteGetting
    private lateinit var fakeFoodRepositoryRemoteGetting: FakeFoodRepositoryRemoteGetting

    private lateinit var getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase
    private lateinit var getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase
    private lateinit var getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase
    private lateinit var getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase

    private lateinit var testDispatchersList: TestDispatchersList

    private lateinit var fragmentMenuViewModel: FragmentMenuViewModel
    private lateinit var connectivityObserver: FakeConnectivityObserver

    @Before
    fun setUp() {
        testDispatchersList = TestDispatchersList()
        connectivityObserver = FakeConnectivityObserver()
        fakeFoodCategoryRepositoryLocalGetting = FakeFoodCategoryRepositoryLocalGetting()
        fakeFoodCategoryRepositoryLocalSaving = FakeFoodCategoryRepositoryLocalSaving()
        fakeFoodRepositoryLocalGetting = FakeFoodRepositoryLocalGetting()
        fakeFoodRepositoryLocalSaving = FakeFoodRepositoryLocalSaving()
        fakeFoodCategoryRepositoryRemoteGetting = FakeFoodCategoryRepositoryRemoteGetting()
        fakeFoodRepositoryRemoteGetting = FakeFoodRepositoryRemoteGetting()

        getFoodCategoriesRemoteUseCase = GetFoodCategoriesRemoteUseCase(
            fakeFoodCategoryRepositoryRemoteGetting,
            GetFoodCategoriesLocalUseCase(fakeFoodCategoryRepositoryLocalGetting),
            SaveFoodCategoriesLocalUseCase(fakeFoodCategoryRepositoryLocalSaving)
        )
        getFoodsByCategoryRemoteUseCase = GetFoodsByCategoryRemoteUseCase(
            fakeFoodRepositoryRemoteGetting, GetFoodsByCategoryLocalUseCase(fakeFoodRepositoryLocalGetting),
            SaveFoodsLocalUseCase(fakeFoodRepositoryLocalSaving)
        )
        getFoodCategoriesLocalUseCase = GetFoodCategoriesLocalUseCase(
            fakeFoodCategoryRepositoryLocalGetting
        )
        getFoodsByCategoryLocalUseCase = GetFoodsByCategoryLocalUseCase(
            fakeFoodRepositoryLocalGetting
        )

        fragmentMenuViewModel = FragmentMenuViewModel(
            dispatchersList = testDispatchersList,
            connectivityObserver = connectivityObserver,
            getFoodCategoriesRemoteUseCase = getFoodCategoriesRemoteUseCase,
            getFoodsByCategoryRemoteUseCase = getFoodsByCategoryRemoteUseCase,
            getFoodCategoriesLocalUseCase = getFoodCategoriesLocalUseCase,
            getFoodsByCategoryLocalUseCase = getFoodsByCategoryLocalUseCase
        )

    }

    @Test
    fun test_loadFoodsCategoriesAndFoods_networkIsAvailableAndPrevDataIsEmpty() = runBlocking {
        connectivityObserver.isNetworkAvailable = true
        fakeFoodRepositoryLocalGetting.dataToReturn = emptyList()
        fakeFoodCategoryRepositoryLocalGetting.dataToReturn = emptyList()
        val remoteFoodCategories = fakeFoodCategoryRepositoryRemoteGetting.dataToReturn.categories
        val remoteFoods = fakeFoodRepositoryRemoteGetting.dataToReturn.foodItems
        fragmentMenuViewModel.loadFoodsCategoriesAndFoods()
        val currentFoodCategories = fragmentMenuViewModel.getFoodCategories().first()
        val currentFoods = fragmentMenuViewModel.getFoods().first()
        var networkConnection: ConnectivityObserver.Status = fragmentMenuViewModel.getConnectivityStatus().first()

        assertEquals(true, remoteFoodCategories == currentFoodCategories)
        assertEquals(true, remoteFoods == currentFoods)

        assertEquals(true, networkConnection == ConnectivityObserver.Status.Available)

        assertEquals(true, fakeFoodCategoryRepositoryLocalSaving.countOfRequest == 1)
        assertEquals(true, fakeFoodRepositoryLocalSaving.countOfRequest == 1)
    }

}

private class FakeFoodCategoryRepositoryLocalGetting: FoodCategoryRepositoryLocal.GettingFoodCategoryRepositoryLocal {

    var isNeedToThrowException = false
    var dataToReturn = listOf(
        FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
    )

    override suspend fun getFoodCategories(): List<FoodCategoryItem> {
        if (isNeedToThrowException) throw DomainException("domain exception")
        return dataToReturn
    }

}

private class FakeFoodCategoryRepositoryLocalSaving: FoodCategoryRepositoryLocal.SavingFoodCategoryRepositoryLocal {

    var isNeedToThrowException = false
    var countOfRequest = 0
    var dataToReturn = listOf(
        FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
    )

    override suspend fun saveFoodCategories(list: List<FoodCategoryItem>): List<FoodCategoryItem> {
        if (isNeedToThrowException) throw DomainException("some exception")
        dataToReturn = list
        countOfRequest++
        return dataToReturn
    }


}

private class FakeFoodRepositoryLocalGetting: FoodRepositoryLocal.GettingFoodRepositoryLocal {

    var isNeedToThrowException = false
    var categoryRequest: FoodCategoryItem? = null
    var dataToReturn = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    )

    override suspend fun getAllFoods(): List<FoodItem> {
        throw IllegalStateException("don't use this method -.- ")
    }

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): List<FoodItem> {
        if (isNeedToThrowException) throw DomainException("some exception")
        categoryRequest = category
        return dataToReturn
    }

}

private class FakeFoodRepositoryLocalSaving: FoodRepositoryLocal.SavingFoodRepositoryLocal {

    var isNeedToThrowException = false
    var countOfRequest = 0
    val dataToReturn = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    )

    override suspend fun saveFoods(list: List<FoodItem>): List<FoodItem> {
        if (isNeedToThrowException) throw DomainException("some exception")
        countOfRequest++
        return dataToReturn
    }


}

private class FakeFoodCategoryRepositoryRemoteGetting: FoodCategoryRepositoryRemote {

    val dataToReturn =  FoodCategory(listOf(
        FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
    ))
    var isNeedToThrowException = false

    override suspend fun getFoodCategories(): FoodCategory {
        if (isNeedToThrowException) throw DomainException("some exception")
        return dataToReturn
    }

}

private class FakeFoodRepositoryRemoteGetting: FoodRepositoryRemote {

    var isNeedToThrowException = false
    var categoryRequest: FoodCategoryItem? = null
    val dataToReturn = Food(foodItems = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    ))

    override suspend fun getAllFoods(): Food {
        if (isNeedToThrowException) throw DomainException("some exception")
        return dataToReturn
    }

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): Food {
        if (isNeedToThrowException) throw DomainException("some exception")
        categoryRequest = category
        return dataToReturn
    }

}

private class TestDispatchersList(
    private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
): DispatchersList {
    override fun io(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }

    override fun ui(): CoroutineDispatcher {
        return testCoroutineDispatcher
    }
}

private class FakeConnectivityObserver: ConnectivityObserver {
    var isNetworkAvailable = false
    override fun observe(): Flow<ConnectivityObserver.Status> {
        return flow {
            if (isNetworkAvailable) {
                emit(ConnectivityObserver.Status.Available)
            } else {
                emit(ConnectivityObserver.Status.Lost)
            }
        }
    }
}