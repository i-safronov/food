package safronov.apps.domain.use_case.remote.food

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodRepositoryLocal
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.local.food.SaveFoodsLocalUseCase
import java.lang.IllegalStateException

class GetFoodsByCategoryRemoteUseCaseTest {

    private lateinit var fakeFoodRepositoryRemote1: FakeFoodRepositoryRemote1
    private lateinit var getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase
    private lateinit var fakeFoodRepositoryLocalSaving: FakeFoodRepositoryLocalSaving
    private lateinit var fakeFoodRepositoryLocalGetting: FakeFoodRepositoryLocalGetting

    @Before
    fun setUp() {
        fakeFoodRepositoryRemote1 = FakeFoodRepositoryRemote1()
        fakeFoodRepositoryLocalSaving = FakeFoodRepositoryLocalSaving()
        fakeFoodRepositoryLocalGetting = FakeFoodRepositoryLocalGetting()
        getFoodsByCategoryRemoteUseCase = GetFoodsByCategoryRemoteUseCase(
            foodRepositoryRemote = fakeFoodRepositoryRemote1,
            getFoodsByCategoryLocalUseCase = GetFoodsByCategoryLocalUseCase(fakeFoodRepositoryLocalGetting),
            saveFoodsLocalUseCase = SaveFoodsLocalUseCase(fakeFoodRepositoryLocalSaving)
        )
    }

    @Test
    fun test_execute_whenOldFoodsIsEmpty() = runBlocking {
        val foodCategory = FoodCategoryItem(
            idCategory = "asdf", strCategory = "fasdf", strCategoryThumb = "fasdfa",
            strCategoryDescription = "asdlfjk"
        )
        fakeFoodRepositoryLocalGetting.dataToReturn = emptyList()
        val result = getFoodsByCategoryRemoteUseCase.execute(foodCategory)
        assertEquals(true, fakeFoodRepositoryRemote1.dataToReturn == result)
        assertEquals(true, fakeFoodRepositoryRemote1.categoryRequest == foodCategory)
        assertEquals(true, fakeFoodRepositoryLocalSaving.countOfRequest == 1)
        assertEquals(true, fakeFoodRepositoryLocalGetting.categoryRequest == foodCategory)
    }

    @Test
    fun test_execute_whenOldFoodsIsNotEmpty() = runBlocking {
        val foodCategory = FoodCategoryItem(
            idCategory = "asdf", strCategory = "fasdf", strCategoryThumb = "fasdfa",
            strCategoryDescription = "asdlfjk"
        )
        val result = getFoodsByCategoryRemoteUseCase.execute(foodCategory)
        assertEquals(true, fakeFoodRepositoryRemote1.dataToReturn == result)
        assertEquals(true, fakeFoodRepositoryRemote1.categoryRequest == foodCategory)
        assertEquals(true, fakeFoodRepositoryLocalSaving.countOfRequest == 0)
        assertEquals(true, fakeFoodRepositoryLocalGetting.categoryRequest == foodCategory)
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException(): Unit = runBlocking {
        val foodCategory = FoodCategoryItem(
            idCategory = "asdf", strCategory = "fasdf", strCategoryThumb = "fasdfa",
            strCategoryDescription = "asdlfjk"
        )
        fakeFoodRepositoryRemote1.isNeedToThrowException = true
        getFoodsByCategoryRemoteUseCase.execute(foodCategory)
    }

}

private class FakeFoodRepositoryRemote1: FoodRepositoryRemote {

    var isNeedToThrowException = false
    var categoryRequest: FoodCategoryItem? = null
    val dataToReturn = Food(foodItems = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    ))

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): Food {
        if (isNeedToThrowException) throw DomainException("some exception")
        categoryRequest = category
        return dataToReturn
    }

}

private class FakeFoodRepositoryLocalSaving: FoodRepositoryLocal.SavingFoodRepositoryLocal {

    var isNeedToThrowException = false
    var countOfRequest = 0
    var dataToReturn = listOf(
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