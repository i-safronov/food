package safronov.apps.data.data_source.local.core.dao.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import safronov.apps.data.data_source.local.model.FoodItemEntity

@Dao
interface FoodDao {

    @Query("SELECT * FROM FoodItemEntityTableName")
    fun getAllFoods(): List<FoodItemEntity>

    @Query("SELECT * FROM FoodItemEntityTableName WHERE foodCategory =:category")
    fun getFoodsByCategory(category: String): List<FoodItemEntity>

    @Insert
    fun saveFoods(list: List<FoodItemEntity>)

}