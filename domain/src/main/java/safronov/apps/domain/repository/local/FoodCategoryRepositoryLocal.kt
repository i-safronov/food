package safronov.apps.domain.repository.local

import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem

interface FoodCategoryRepositoryLocal {

    interface GettingFoodCategoryRepositoryLocal {
        suspend fun getFoodCategories(): List<FoodCategoryItem>
    }

    interface SavingFoodCategoryRepositoryLocal {
        suspend fun saveFoodCategories(list: List<FoodCategory>): List<FoodCategory>
    }

    interface MutableFoodCategoryRepositoryLocal: GettingFoodCategoryRepositoryLocal, SavingFoodCategoryRepositoryLocal

}