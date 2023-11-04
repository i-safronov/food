package safronov.apps.data.repository_impl.remote.model

import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food_category.FoodCategoryItem

data class FoodRemoteEntity(
    val meals: List<FoodItemRemoteEntity>
) {

    companion object {
        fun convertFoodRemoteEntityToFood(
            category: FoodCategoryItem,
            food: FoodRemoteEntity
        ): Food {
            return Food(
                foodItems = FoodItemRemoteEntity.convertListOfFoodItemRemoteEntityToListOfFoodItem(
                    category, food.meals
                )
            )
        }
    }

}
