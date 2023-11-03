package safronov.apps.domain.use_case.local.food

import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodRepositoryLocal

class GetFoodsByCategoryLocalUseCase(
    private val foodRepositoryLocal: FoodRepositoryLocal.GettingFoodRepositoryLocal
) {

    suspend fun execute(category: FoodCategoryItem): List<FoodItem> {
        return foodRepositoryLocal.getFoodsByCategory(category)
    }

}
