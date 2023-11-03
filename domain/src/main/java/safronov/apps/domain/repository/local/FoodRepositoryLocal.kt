package safronov.apps.domain.repository.local

import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food_category.FoodCategory

interface FoodRepositoryLocal {

    interface GettingFoodRepositoryLocal {
        suspend fun getAllFoods(): List<Food>
        suspend fun getFoodsByCategory(category: FoodCategory): List<Food>
    }

    interface SavingFoodRepositoryLocal {
        suspend fun saveFoods(list: List<Food>): List<Food>
    }

}