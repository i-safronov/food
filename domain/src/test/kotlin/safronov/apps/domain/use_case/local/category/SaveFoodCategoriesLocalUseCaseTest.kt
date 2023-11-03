package safronov.apps.domain.use_case.local.category

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal

class SaveFoodCategoriesLocalUseCaseTest {

    private lateinit var fakeFoodCategoryRepositoryLocal1: FakeFoodCategoryRepositoryLocal1
    private lateinit var saveFoodCategoriesLocalUseCase: SaveFoodCategoriesLocalUseCase

    @Before
    fun setUp() {
        fakeFoodCategoryRepositoryLocal1 = FakeFoodCategoryRepositoryLocal1()
        saveFoodCategoriesLocalUseCase = SaveFoodCategoriesLocalUseCase(
            foodCategoryRepositoryLocal = fakeFoodCategoryRepositoryLocal1
        )
    }

    @Test
    fun test_execute() = runBlocking {
        val result = saveFoodCategoriesLocalUseCase.execute(fakeFoodCategoryRepositoryLocal1.dataToReturn)
        assertEquals(true, fakeFoodCategoryRepositoryLocal1.dataToReturn == result)
        assertEquals(true, fakeFoodCategoryRepositoryLocal1.countOfRequest == 1)
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException() = runBlocking {
        fakeFoodCategoryRepositoryLocal1.isNeedToThrowException = true
        val result = saveFoodCategoriesLocalUseCase.execute(fakeFoodCategoryRepositoryLocal1.dataToReturn)
    }

}

private class FakeFoodCategoryRepositoryLocal1: FoodCategoryRepositoryLocal.SavingFoodCategoryRepositoryLocal {

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