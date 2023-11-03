package safronov.apps.domain.repository.remote

import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food_category.FoodCategory

interface FoodRepositoryRemote {

    suspend fun getAllFoods(): List<Food>
    suspend fun getFoodsByCategory(category: FoodCategory): List<Food>

}