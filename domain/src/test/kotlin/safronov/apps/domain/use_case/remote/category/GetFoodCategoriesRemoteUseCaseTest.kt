package safronov.apps.domain.use_case.remote.category

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote

class GetFoodCategoriesRemoteUseCaseTest {

    private lateinit var fakeFoodCategoryRepositoryRemote: FakeFoodCategoryRepositoryRemote
    private lateinit var getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase

    @Before
    fun setUp() {
        fakeFoodCategoryRepositoryRemote = FakeFoodCategoryRepositoryRemote()
        getFoodCategoriesRemoteUseCase = GetFoodCategoriesRemoteUseCase(
            foodCategoryRepositoryRemote = fakeFoodCategoryRepositoryRemote
        )
    }

    @Test
    fun test_execute() = runBlocking {
        Assert.assertEquals(
            true,
            getFoodCategoriesRemoteUseCase.execute() == fakeFoodCategoryRepositoryRemote.dataToReturn
        )
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException(): Unit = runBlocking {
        fakeFoodCategoryRepositoryRemote.isNeedToThrowException = true
        getFoodCategoriesRemoteUseCase.execute()
    }

}

private class FakeFoodCategoryRepositoryRemote: FoodCategoryRepositoryRemote {

    val dataToReturn = listOf(
        FoodCategory(listOf(
            FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
        ))
    )
    var isNeedToThrowException = false

    override suspend fun getFoodCategories(): List<FoodCategory> {
        if (isNeedToThrowException) throw DomainException("some exception")
        return dataToReturn
    }

}