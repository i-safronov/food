package safronov.apps.domain.repository.remote

import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem

interface FoodRepositoryRemote {

    suspend fun getFoodsByCategory(category: FoodCategoryItem): Food

}