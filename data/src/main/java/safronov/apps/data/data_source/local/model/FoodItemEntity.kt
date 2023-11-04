package safronov.apps.data.data_source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import safronov.apps.domain.model.food.FoodItem

@Entity(tableName = FoodItemEntity.FOOD_ITEM_ENTITY_TABLE_NAME)
data class FoodItemEntity(
    @PrimaryKey(autoGenerate = false) val idMeal: Long? = null,
    @ColumnInfo val strMeal: String?,
    @ColumnInfo val strMealThumb: String?,
    @ColumnInfo val foodCategory: String?
) {

    companion object {
        const val FOOD_ITEM_ENTITY_TABLE_NAME = "FoodItemEntityTableName"

        fun convertFoodItemEntityToFoodItem(
            foodItem: FoodItemEntity
        ): FoodItem {
            return FoodItem(
                idMeal = foodItem.idMeal.toString(),
                strMeal = foodItem.strMeal,
                strMealThumb = foodItem.strMealThumb,
                foodCategory = foodItem.foodCategory
            )
        }

        fun convertFoodItemToFoodItemEntity(
            foodItem: FoodItem
        ): FoodItemEntity {
            return FoodItemEntity(
                idMeal = foodItem.idMeal?.toLong(),
                strMeal = foodItem.strMeal,
                strMealThumb = foodItem.strMealThumb,
                foodCategory = foodItem.foodCategory
            )
        }

        fun convertListOfFoodItemEntityToListOfFoodItem(
            list: List<FoodItemEntity>
        ): List<FoodItem> {
            val mList = mutableListOf<FoodItem>()
            list.forEach {
                mList.add(convertFoodItemEntityToFoodItem(it))
            }
            return mList
        }

        fun convertListOfFoodItemToListOfFoodItemEntity(
            list: List<FoodItem>
        ): List<FoodItemEntity> {
            val mList = mutableListOf<FoodItemEntity>()
            list.forEach {
                mList.add(convertFoodItemToFoodItemEntity(it))
            }
            return mList
        }

    }

}
