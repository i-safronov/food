package safronov.apps.domain.use_case.remote.food

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import java.lang.IllegalStateException

class GetAllFoodsRemoteUseCaseTest {

    private lateinit var fakeFoodRepositoryRemote: FakeFoodRepositoryRemote
    private lateinit var getAllFoodsRemoteUseCase: GetAllFoodsRemoteUseCase

    @Before
    fun setUp() {
        fakeFoodRepositoryRemote = FakeFoodRepositoryRemote()
        getAllFoodsRemoteUseCase = GetAllFoodsRemoteUseCase(
            foodRepositoryRemote = fakeFoodRepositoryRemote
        )
    }

    @Test
    fun test_execute() = runBlocking {
        assertEquals(true, fakeFoodRepositoryRemote.dataToReturn == getAllFoodsRemoteUseCase.execute())
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException(): Unit = runBlocking {
        fakeFoodRepositoryRemote.isNeedToThrowException = true
        getAllFoodsRemoteUseCase.execute()
    }

}

private class FakeFoodRepositoryRemote: FoodRepositoryRemote {

    var isNeedToThrowException = false
    val dataToReturn =  Food(foodItems = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    ))

    override suspend fun getAllFoods(): Food {
        if (isNeedToThrowException) throw DomainException("some exception :( ")
        return dataToReturn
    }

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): Food {
        throw IllegalStateException("don't use this method -_-")
    }

}