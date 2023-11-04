package safronov.apps.data.repository_impl.remote.model

import android.util.Log
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategoryItem

data class FoodItemRemoteEntity(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
) {
    companion object {

        fun convertFoodItemRemoteEntityToFoodItem(
            category: FoodCategoryItem,
            foodItemRemoteEntity: FoodItemRemoteEntity
        ): FoodItem {
            return FoodItem(
                idMeal = foodItemRemoteEntity.idMeal,
                strMeal = foodItemRemoteEntity.strMeal,
                strMealThumb = foodItemRemoteEntity.strMealThumb,
                foodCategory = category.strCategory
            )
        }

        fun convertListOfFoodItemRemoteEntityToListOfFoodItem(
            category: FoodCategoryItem,
            list: List<FoodItemRemoteEntity>
        ): List<FoodItem> {
            val mList = mutableListOf<FoodItem>()
            list.forEach {
                mList.add(convertFoodItemRemoteEntityToFoodItem(
                    category, it
                ))
            }
            return mList
        }

    }
}
