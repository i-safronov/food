package safronov.apps.domain.use_case.remote.food

import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.remote.FoodRepositoryRemote

class GetFoodsByCategoryRemoteUseCase(
    private val foodRepositoryRemote: FoodRepositoryRemote
) {

    suspend fun execute(category: FoodCategoryItem): List<Food> {
        return foodRepositoryRemote.getFoodsByCategory(category)
    }

}
