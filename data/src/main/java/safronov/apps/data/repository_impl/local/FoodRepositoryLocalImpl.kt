package safronov.apps.data.repository_impl.local

import safronov.apps.data.data_source.local.core.dao.food.FoodDao
import safronov.apps.data.data_source.local.model.FoodItemEntity
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodRepositoryLocal

class FoodRepositoryLocalImpl(
    private val foodDao: FoodDao
): FoodRepositoryLocal.MutableFoodRepositoryLocal {

    override suspend fun getAllFoods(): List<FoodItem> {
        return FoodItemEntity.convertListOfFoodItemEntityToListOfFoodItem(
            foodDao.getAllFoods()
        )
    }

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): List<FoodItem> {
        return FoodItemEntity.convertListOfFoodItemEntityToListOfFoodItem(
            foodDao.getFoodsByCategory(category.strCategory.toString())
        )
    }

    override suspend fun saveFoods(list: List<FoodItem>): List<FoodItem> {
        foodDao.saveFoods(FoodItemEntity.convertListOfFoodItemToListOfFoodItemEntity(list))
        return getAllFoods()
    }

}