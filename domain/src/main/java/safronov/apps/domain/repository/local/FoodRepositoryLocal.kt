package safronov.apps.domain.repository.local

import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategoryItem

interface FoodRepositoryLocal {

    interface GettingFoodRepositoryLocal {
        suspend fun getAllFoods(): List<FoodItem>
        suspend fun getFoodsByCategory(category: FoodCategoryItem): List<FoodItem>
    }

    interface SavingFoodRepositoryLocal {
        suspend fun saveFoods(list: List<FoodItem>): List<FoodItem>
    }

    interface MutableFoodRepositoryLocal: GettingFoodRepositoryLocal, SavingFoodRepositoryLocal

}