package safronov.apps.domain.repository.local

import safronov.apps.domain.model.food_category.FoodCategory

interface FoodCategoryRepositoryLocal {

    interface GettingFoodCategoryRepositoryLocal {
        suspend fun getFoodCategories(): List<FoodCategory>
    }

    interface SavingFoodCategoryRepositoryLocal {
        suspend fun saveFoodCategories(list: List<FoodCategory>): List<FoodCategory>
    }

}