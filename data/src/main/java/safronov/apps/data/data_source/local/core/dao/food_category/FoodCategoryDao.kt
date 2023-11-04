package safronov.apps.data.data_source.local.core.dao.food_category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import safronov.apps.domain.model.food_category.FoodCategoryItem

@Dao
interface FoodCategoryDao {

    @Insert
    fun saveFoodCategories(list: List<FoodCategoryItem>)

    @Query("SELECT * FROM FoodItemEntityTableName.FoodCategoryItemEntityTableName")
    fun getFoodCategories(): List<FoodCategoryItem>

}