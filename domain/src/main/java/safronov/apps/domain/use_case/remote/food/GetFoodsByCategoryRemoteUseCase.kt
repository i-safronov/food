package safronov.apps.domain.use_case.remote.food

import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.local.food.SaveFoodsLocalUseCase

class GetFoodsByCategoryRemoteUseCase(
    private val foodRepositoryRemote: FoodRepositoryRemote,
    private val getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase,
    private val saveFoodsLocalUseCase: SaveFoodsLocalUseCase
) {

    suspend fun execute(category: FoodCategoryItem): Food {
        val newFoods = foodRepositoryRemote.getFoodsByCategory(category)
        val currentFoods = getFoodsByCategoryLocalUseCase.execute(category)
        return if (currentFoods.isNotEmpty()) newFoods else {
            saveFoodsLocalUseCase.execute(newFoods.foodItems)
            newFoods
        }
    }

}
