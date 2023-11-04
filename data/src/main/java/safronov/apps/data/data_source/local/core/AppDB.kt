package safronov.apps.data.data_source.local.core

import androidx.room.Database
import safronov.apps.data.data_source.local.core.dao.food.FoodDao
import safronov.apps.data.data_source.local.core.dao.food_category.FoodCategoryDao
import safronov.apps.data.data_source.local.model.FoodCategoryItemEntity
import safronov.apps.data.data_source.local.model.FoodItemEntity

@Database(entities = [FoodCategoryItemEntity::class, FoodItemEntity::class], version = 0)
abstract class AppDB {

    abstract fun getFoodDao(): FoodDao
    abstract fun getFoodCategoryDao(): FoodCategoryDao

}