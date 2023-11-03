package safronov.apps.domain.use_case.local

import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal

class GetFoodCategoriesLocalUseCase(
    private val foodCategoryRepositoryLocal: FoodCategoryRepositoryLocal.GettingFoodCategoryRepositoryLocal
) {

    suspend fun execute(): List<FoodCategoryItem> {
        return foodCategoryRepositoryLocal.getFoodCategories()
    }

}
