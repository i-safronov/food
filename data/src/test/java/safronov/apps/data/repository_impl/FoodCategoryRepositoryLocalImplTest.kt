package safronov.apps.data.repository_impl

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import safronov.apps.data.data_source.local.core.dao.food_category.FoodCategoryDao
import safronov.apps.data.data_source.local.model.FoodCategoryItemEntity
import java.lang.IllegalStateException

class FoodCategoryRepositoryLocalImplTest {

    private lateinit var fakeFoodCategoryDao: FakeFoodCategoryDao
    private lateinit var foodCategoryRepositoryLocalImpl: FoodCategoryRepositoryLocalImpl

    @Before
    fun setUp() {
        fakeFoodCategoryDao = FakeFoodCategoryDao()
        foodCategoryRepositoryLocalImpl = FoodCategoryRepositoryLocalImpl(fakeFoodCategoryDao)
    }

    @Test
    fun test_getFoodCategories() = runBlocking {
        assertEquals(true,
            fakeFoodCategoryDao.dataToReturn == FoodCategoryItemEntity.convertListOfFoodCategoryItemToListOfFoodCategoryItemEntity(foodCategoryRepositoryLocalImpl.getFoodCategories())
        )
    }

    @Test
    fun test_saveFoodCategories() = runBlocking {
        foodCategoryRepositoryLocalImpl.saveFoodCategories(listOf())
        assertEquals(true, fakeFoodCategoryDao.dataToReturn.isEmpty())
    }

    @Test(expected = Exception::class)
    fun test_getFoodCategories_expectedException(): Unit = runBlocking {
        fakeFoodCategoryDao.isNeedToThrowException = true
        foodCategoryRepositoryLocalImpl.getFoodCategories()
    }

    @Test(expected = Exception::class)
    fun test_saveFoodCategories_expectedException(): Unit = runBlocking {
        fakeFoodCategoryDao.isNeedToThrowException = true
        foodCategoryRepositoryLocalImpl.saveFoodCategories(listOf())
    }

}

private class FakeFoodCategoryDao: FoodCategoryDao {

    var isNeedToThrowException = false
    var dataToReturn = listOf(
        FoodCategoryItemEntity(
            idCategory = 435,
            strCategory = "fasdf",
            strCategoryDescription = "fasdf",
            strCategoryThumb = "faksdf"
        )
    )

    override fun saveFoodCategories(list: List<FoodCategoryItemEntity>) {
        if (isNeedToThrowException) throw IllegalStateException("sadfal")
        dataToReturn = list
    }

    override fun getFoodCategories(): List<FoodCategoryItemEntity> {
        if (isNeedToThrowException) throw IllegalStateException("sadfal")
        return dataToReturn
    }

}