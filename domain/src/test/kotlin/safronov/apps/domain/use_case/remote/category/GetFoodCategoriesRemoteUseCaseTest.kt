package safronov.apps.domain.use_case.remote.category

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote
import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.category.SaveFoodCategoriesLocalUseCase

class GetFoodCategoriesRemoteUseCaseTest {

    private lateinit var fakeFoodCategoryRepositoryRemote: FakeFoodCategoryRepositoryRemote
    private lateinit var getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase
    private lateinit var fakeFoodCategoryRepositoryLocal: FakeFoodCategoryRepositoryLocal
    private lateinit var fakeFoodCategoryRepositoryLocalSaving: FakeFoodCategoryRepositoryLocalSaving

    @Before
    fun setUp() {
        fakeFoodCategoryRepositoryLocal = FakeFoodCategoryRepositoryLocal()
        fakeFoodCategoryRepositoryLocalSaving = FakeFoodCategoryRepositoryLocalSaving()
        fakeFoodCategoryRepositoryRemote = FakeFoodCategoryRepositoryRemote()
        getFoodCategoriesRemoteUseCase = GetFoodCategoriesRemoteUseCase(
            foodCategoryRepositoryRemote = fakeFoodCategoryRepositoryRemote,
            getFoodCategoriesLocalUseCase = GetFoodCategoriesLocalUseCase(fakeFoodCategoryRepositoryLocal),
            saveFoodCategoriesLocalUseCase = SaveFoodCategoriesLocalUseCase(fakeFoodCategoryRepositoryLocalSaving)
        )
    }

    @Test
    fun test_execute_whenPrevDataIsEmpty() = runBlocking {
        fakeFoodCategoryRepositoryLocal.dataToReturn = emptyList()
        val newData = fakeFoodCategoryRepositoryRemote.dataToReturn
        assertEquals(
            true,
            getFoodCategoriesRemoteUseCase.execute() == newData
        )
        assertEquals(true, fakeFoodCategoryRepositoryLocal.countOfRequest == 1)
        assertEquals(true, fakeFoodCategoryRepositoryLocalSaving.countOfRequest == 1)
        assertEquals(true, fakeFoodCategoryRepositoryLocalSaving.dataToReturn == newData.categories)
    }

    @Test
    fun test_execute_whenPrevDataIsNotEmpty() = runBlocking {
        val newData = fakeFoodCategoryRepositoryRemote.dataToReturn
        assertEquals(
            true,
            getFoodCategoriesRemoteUseCase.execute() == newData
        )
        assertEquals(true, fakeFoodCategoryRepositoryLocal.countOfRequest == 1)
        assertEquals(true, fakeFoodCategoryRepositoryLocalSaving.countOfRequest == 0)
    }

    @Test(expected = DomainException::class)
    fun test_execute_expectedDomainException(): Unit = runBlocking {
        fakeFoodCategoryRepositoryRemote.isNeedToThrowException = true
        getFoodCategoriesRemoteUseCase.execute()
    }

}

private class FakeFoodCategoryRepositoryRemote: FoodCategoryRepositoryRemote {

    val dataToReturn =  FoodCategory(listOf(
        FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
    ))
    var isNeedToThrowException = false

    override suspend fun getFoodCategories(): FoodCategory {
        if (isNeedToThrowException) throw DomainException("some exception")
        return dataToReturn
    }

}

private class FakeFoodCategoryRepositoryLocal: FoodCategoryRepositoryLocal.GettingFoodCategoryRepositoryLocal {

    var isNeedToThrowException = false
    var countOfRequest = 0
    var dataToReturn = listOf(
        FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
    )

    override suspend fun getFoodCategories(): List<FoodCategoryItem> {
        if (isNeedToThrowException) throw DomainException("domain exception")
        countOfRequest++
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