package safronov.apps.domain.use_case.local

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal

class GetFoodCategoriesLocalUseCaseTest {

    private lateinit var fakeFoodCategoryRepositoryLocal: FakeFoodCategoryRepositoryLocal
    private lateinit var getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase

    @Before
    fun setUp() {
        fakeFoodCategoryRepositoryLocal = FakeFoodCategoryRepositoryLocal()
        getFoodCategoriesLocalUseCase = GetFoodCategoriesLocalUseCase(
            foodCategoryRepositoryLocal = fakeFoodCategoryRepositoryLocal
        )
    }

    @Test
    fun test_execute() = runBlocking {
        assertEquals(true, fakeFoodCategoryRepositoryLocal.dataToReturn == getFoodCategoriesLocalUseCase.execute())
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException(): Unit = runBlocking {
        fakeFoodCategoryRepositoryLocal.isNeedToThrowException = true
        getFoodCategoriesLocalUseCase.execute()
    }

}

private class FakeFoodCategoryRepositoryLocal: FoodCategoryRepositoryLocal.GettingFoodCategoryRepositoryLocal {

    var isNeedToThrowException = false
    val dataToReturn = listOf(
        FoodCategory(listOf(
            FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
        ))
    )

    override suspend fun getFoodCategories(): List<FoodCategory> {
        if (isNeedToThrowException) throw DomainException("domain exception")
        return dataToReturn
    }

}