package safronov.apps.data.repository_impl

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import safronov.apps.data.data_source.local.core.dao.food.FoodDao
import safronov.apps.data.data_source.local.model.FoodItemEntity
import safronov.apps.domain.model.food_category.FoodCategoryItem
import java.lang.IllegalStateException

class FoodRepositoryLocalImplTest {

    private lateinit var fakeFoodDao: FakeFoodDao
    private lateinit var foodRepositoryLocalImpl: FoodRepositoryLocalImpl

    @Before
    fun setUp() {
        fakeFoodDao = FakeFoodDao()
        foodRepositoryLocalImpl = FoodRepositoryLocalImpl(fakeFoodDao)
    }

    @Test
    fun test_getAllFoods() = runBlocking {
        assertEquals(
            true,
            fakeFoodDao.dataToReturn == FoodItemEntity.convertListOfFoodItemToListOfFoodItemEntity(
                foodRepositoryLocalImpl.getAllFoods()
            )
        )
    }

    @Test
    fun test_getFoodsByCategory() = runBlocking {
        assertEquals(
            true,
            fakeFoodDao.dataToReturn == FoodItemEntity.convertListOfFoodItemToListOfFoodItemEntity(
                foodRepositoryLocalImpl.getFoodsByCategory(FoodCategoryItem("fasdf", "asdf", "asdf", "asdf"))
            )
        )
    }

    @Test
    fun test_saveFoods() = runBlocking {
        val save = fakeFoodDao.dataToReturn
        foodRepositoryLocalImpl.saveFoods(FoodItemEntity.convertListOfFoodItemEntityToListOfFoodItem(save))
        assertEquals(true, save == fakeFoodDao.dataToReturn)
        assertEquals(true, fakeFoodDao.countOfRequestSave == 1)
    }

}


private class FakeFoodDao: FoodDao {

    var isNeedToThrowException = false
    var countOfRequestSave = 0
    var dataToReturn = listOf(
        FoodItemEntity(
            idMeal = 4534,
            strMeal = "asdf",
            strMealThumb = "asdf",
            foodCategory = "asljdfkas"
        )
    )

    override fun getAllFoods(): List<FoodItemEntity> {
        if (isNeedToThrowException) throw IllegalStateException("asjdlfk")
        return dataToReturn
    }

    override fun getFoodsByCategory(category: String): List<FoodItemEntity> {
        if (isNeedToThrowException) throw IllegalStateException("asjdlfk")
        return dataToReturn
    }

    override fun saveFoods(list: List<FoodItemEntity>) {
        if (isNeedToThrowException) throw IllegalStateException("asjdlfk")
        countOfRequestSave++
        dataToReturn = list
    }

}