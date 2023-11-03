package safronov.apps.domain.use_case.local.category

import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal

class SaveFoodCategoriesLocalUseCase(
    private val foodCategoryRepositoryLocal: FoodCategoryRepositoryLocal.SavingFoodCategoryRepositoryLocal
) {

    suspend fun execute(list: List<FoodCategoryItem>): List<FoodCategoryItem> {
        return foodCategoryRepositoryLocal.saveFoodCategories(list)
    }

}
