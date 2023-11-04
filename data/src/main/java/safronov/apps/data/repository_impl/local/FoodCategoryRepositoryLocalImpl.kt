package safronov.apps.data.repository_impl.local

import safronov.apps.data.data_source.local.core.dao.food_category.FoodCategoryDao
import safronov.apps.data.data_source.local.model.FoodCategoryItemEntity
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal
import java.lang.Exception

class FoodCategoryRepositoryLocalImpl(
    private val foodCategoryDao: FoodCategoryDao,
): FoodCategoryRepositoryLocal.MutableFoodCategoryRepositoryLocal {

    override suspend fun getFoodCategories(): List<FoodCategoryItem> {
        try {
            return FoodCategoryItemEntity.convertListOfFoodCategoryItemEntityToListOfFoodCategoryIte(
                foodCategoryDao.getFoodCategories()
            )
        } catch (e: Exception) {
            throw DomainException(e.message, e)
        }
    }

    override suspend fun saveFoodCategories(list: List<FoodCategoryItem>): List<FoodCategoryItem> {
        try {
            foodCategoryDao.saveFoodCategories(FoodCategoryItemEntity.convertListOfFoodCategoryItemToListOfFoodCategoryItemEntity(
                list
            ))
            return getFoodCategories()
        } catch (e: Exception) {
            throw DomainException(e.message, e)
        }
    }

}