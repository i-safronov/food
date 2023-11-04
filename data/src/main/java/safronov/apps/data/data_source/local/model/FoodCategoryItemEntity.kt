package safronov.apps.data.data_source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import safronov.apps.domain.model.food_category.FoodCategoryItem

@Entity(tableName = FoodCategoryItemEntity.FOOD_CATEGORY_ITEM_ENTITY_TABLE_NAME)
data class FoodCategoryItemEntity(
    @PrimaryKey(autoGenerate = false) val idCategory: Long? = null,
    @ColumnInfo val strCategory: String?,
    @ColumnInfo val strCategoryDescription: String?,
    @ColumnInfo val strCategoryThumb: String?
) {

    companion object {
        const val FOOD_CATEGORY_ITEM_ENTITY_TABLE_NAME = "FoodCategoryItemEntityTableName"

        fun convertFoodCategoryItemEntityToFoodCategoryItem(
            foodCategory: FoodCategoryItemEntity
        ): FoodCategoryItem {
            return FoodCategoryItem(
                idCategory = foodCategory.idCategory.toString(),
                strCategory = foodCategory.strCategory,
                strCategoryDescription = foodCategory.strCategoryDescription,
                strCategoryThumb = foodCategory.strCategoryThumb
            )
        }

        fun convertFoodCategoryItemToFoodCategoryItemEntity(
            foodCategory: FoodCategoryItem
        ): FoodCategoryItemEntity {
            return FoodCategoryItemEntity(
                idCategory = foodCategory.idCategory?.toLong(),
                strCategory = foodCategory.strCategory,
                strCategoryDescription = foodCategory.strCategoryDescription,
                strCategoryThumb = foodCategory.strCategoryThumb
            )
        }

        fun convertListOfFoodCategoryItemEntityToListOfFoodCategoryIte(
            list: List<FoodCategoryItemEntity>
        ): List<FoodCategoryItem> {
            val mList = mutableListOf<FoodCategoryItem>()
            list.forEach {
                mList.add(convertFoodCategoryItemEntityToFoodCategoryItem(it))
            }
            return mList
        }

        fun convertListOfFoodCategoryItemToListOfFoodCategoryItemEntity(
            list: List<FoodCategoryItem>
        ): List<FoodCategoryItemEntity> {
            val mList = mutableListOf<FoodCategoryItemEntity>()
            list.forEach {
                mList.add(convertFoodCategoryItemToFoodCategoryItemEntity(it))
            }
            return mList
        }

    }

}
