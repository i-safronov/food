package safronov.apps.domain.use_case.local.food

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodRepositoryLocal
import java.lang.IllegalStateException

class GetFoodsByCategoryLocalUseCaseTest {

    private lateinit var fakeFoodRepositoryLocal1: FakeFoodRepositoryLocal1
    private lateinit var getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase

    @Before
    fun setUp() {
        fakeFoodRepositoryLocal1 = FakeFoodRepositoryLocal1()
        getFoodsByCategoryLocalUseCase = GetFoodsByCategoryLocalUseCase(
            foodRepositoryLocal = fakeFoodRepositoryLocal1
        )
    }

    @Test
    fun test_execute() = runBlocking {
        val taskCategory = fakeFoodRepositoryLocal1.categoryRequest
        val result = getFoodsByCategoryLocalUseCase.execute(taskCategory)
        assertEquals(true, fakeFoodRepositoryLocal1.dataToReturn == result)
        assertEquals(true, taskCategory == fakeFoodRepositoryLocal1.categoryRequest)
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException(): Unit = runBlocking {
        fakeFoodRepositoryLocal1.isNeedToThrowException = true
        val taskCategory = fakeFoodRepositoryLocal1.categoryRequest
        getFoodsByCategoryLocalUseCase.execute(taskCategory)
    }

}

private class FakeFoodRepositoryLocal1: FoodRepositoryLocal.GettingFoodRepositoryLocal {

    var isNeedToThrowException = false
    var categoryRequest: FoodCategoryItem? = null
    val dataToReturn = listOf(
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