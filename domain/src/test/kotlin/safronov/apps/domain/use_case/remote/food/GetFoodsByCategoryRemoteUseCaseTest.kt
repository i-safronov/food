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
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import java.lang.IllegalStateException

class GetFoodsByCategoryRemoteUseCaseTest {

    private lateinit var fakeFoodRepositoryRemote1: FakeFoodRepositoryRemote1
    private lateinit var getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase

    @Before
    fun setUp() {
        fakeFoodRepositoryRemote1 = FakeFoodRepositoryRemote1()
        getFoodsByCategoryRemoteUseCase = GetFoodsByCategoryRemoteUseCase(
            foodRepositoryRemote = fakeFoodRepositoryRemote1
        )
    }

    @Test
    fun test_execute() = runBlocking {
        val foodCategory = FoodCategoryItem(
            idCategory = "asdf", strCategory = "fasdf", strCategoryThumb = "fasdfa",
            strCategoryDescription = "asdlfjk"
        )
        assertEquals(true, fakeFoodRepositoryRemote1.dataToReturn == getFoodsByCategoryRemoteUseCase.execute(foodCategory))
        assertEquals(true, fakeFoodRepositoryRemote1.categoryRequest == foodCategory)
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
    val dataToReturn = listOf(
        Food(foodItems = listOf(
            FoodItem(
                idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
            )
        ))
    )

    override suspend fun getAllFoods(): List<Food> {
        throw IllegalStateException("don't use this method -_-")
    }

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): List<Food> {
        if (isNeedToThrowException) throw DomainException("some exception")
        categoryRequest = category
        return dataToReturn
    }

}