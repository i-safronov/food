package safronov.apps.domain.use_case

import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote

class GetFoodCategoriesRemoteUseCase(
    private val foodCategoryRepositoryRemote: FoodCategoryRepositoryRemote
) {

    suspend fun execute(): List<FoodCategory> {
        return foodCategoryRepositoryRemote.getFoodCategories()
    }

}
