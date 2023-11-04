package safronov.apps.data.data_source.local.core.dao.food_category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import safronov.apps.data.data_source.local.model.FoodCategoryItemEntity

@Dao
interface FoodCategoryDao {

    @Insert
    fun saveFoodCategories(list: List<FoodCategoryItemEntity>)

    @Query("SELECT * FROM FoodCategoryItemEntityTableName")
    fun getFoodCategories(): List<FoodCategoryItemEntity>

}