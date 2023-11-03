package safronov.apps.domain.use_case.local.food

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.repository.local.FoodRepositoryLocal
import java.lang.IllegalStateException

class GetAllFoodsLocalUseCaseTest {

    private lateinit var fakeFoodRepositoryLocal: FakeFoodRepositoryLocal
    private lateinit var getAllFoodsLocalUseCase: GetAllFoodsLocalUseCase

    @Before
    fun setUp() {
        fakeFoodRepositoryLocal = FakeFoodRepositoryLocal()
        getAllFoodsLocalUseCase = GetAllFoodsLocalUseCase(
            foodRepositoryLocal = fakeFoodRepositoryLocal
        )
    }

    @Test
    fun test_execute() = runBlocking {
        assertEquals(true, fakeFoodRepositoryLocal.dataToReturn == getAllFoodsLocalUseCase.execute())
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException(): Unit = runBlocking {
        fakeFoodRepositoryLocal.isNeedToThrowException = true
        getAllFoodsLocalUseCase.execute()
    }

}

private class FakeFoodRepositoryLocal: FoodRepositoryLocal.GettingFoodRepositoryLocal {

    var isNeedToThrowException = false
    val dataToReturn = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    )

    override suspend fun getAllFoods(): List<FoodItem> {
        if (isNeedToThrowException) throw DomainException("some exception")
        return dataToReturn
    }

    override suspend fun getFoodsByCategory(category: FoodCategory): List<FoodItem> {
        throw IllegalStateException("don't use this method -.- ")
    }

}