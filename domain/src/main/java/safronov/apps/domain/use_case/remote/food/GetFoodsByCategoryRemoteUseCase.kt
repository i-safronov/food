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

    /**
     *
     * Execute is a method that makes a request to [FoodRepositoryRemote] after receiving the result,
     * it calls the [GetFoodsByCategoryLocalUseCase] method to get the foods from the foodsbase,
     * if the foodsbase stores products with exactly the same [category], then it simply returns
     * the foods that it received and if there is no foods in the database then it saves the foods
     * using [SaveFoodsLocalUseCase] and returns the received foods
     *
     * Notes: if some foods are updated/added on the server and previous products with exactly
     * the same [category] are already stored in the database, then the data will NOT be updated
     * and the previous outdated data will remain in the database. This problem is test related
     * to the API that this application uses.
     *
     * */
    suspend fun execute(category: FoodCategoryItem): Food {
        val newFoods = foodRepositoryRemote.getFoodsByCategory(category)
        val currentFoods = getFoodsByCategoryLocalUseCase.execute(category)
        return if (currentFoods.isNotEmpty()) newFoods else {
            saveFoodsLocalUseCase.execute(newFoods.foodItems)
            newFoods
        }
    }

}
