package safronov.apps.domain.use_case.local.food

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodRepositoryLocal
import java.lang.IllegalStateException

class SaveFoodsLocalUseCaseTest {

    private lateinit var fakeFoodRepositoryLocal2: FakeFoodRepositoryLocal2
    private lateinit var saveFoodsLocalUseCase: SaveFoodsLocalUseCase

    @Before
    fun setUp() {
        fakeFoodRepositoryLocal2 = FakeFoodRepositoryLocal2()
        saveFoodsLocalUseCase = SaveFoodsLocalUseCase(
            foodRepositoryLocal = fakeFoodRepositoryLocal2
        )
    }

    @Test
    fun test_execute() = runBlocking {
        val foods = fakeFoodRepositoryLocal2.dataToReturn
        val result = saveFoodsLocalUseCase.execute(foods)
        assertEquals(true, foods == result)
        assertEquals(true, fakeFoodRepositoryLocal2.countOfRequest == 1)
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException(): Unit = runBlocking {
        val foods = fakeFoodRepositoryLocal2.dataToReturn
        fakeFoodRepositoryLocal2.isNeedToThrowException = true
        saveFoodsLocalUseCase.execute(foods)
    }

}

private class FakeFoodRepositoryLocal2: FoodRepositoryLocal.SavingFoodRepositoryLocal {

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