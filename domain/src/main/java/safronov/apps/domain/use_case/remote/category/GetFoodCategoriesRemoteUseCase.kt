package safronov.apps.domain.use_case.remote.category

import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote
import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.category.SaveFoodCategoriesLocalUseCase

class GetFoodCategoriesRemoteUseCase(
    private val foodCategoryRepositoryRemote: FoodCategoryRepositoryRemote,
    private val getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase,
    private val saveFoodCategoriesLocalUseCase: SaveFoodCategoriesLocalUseCase
) {

    suspend fun execute(): FoodCategory {
        val result = foodCategoryRepositoryRemote.getFoodCategories()
        return if (getFoodCategoriesLocalUseCase.execute().isNotEmpty()) result else {
            saveFoodCategoriesLocalUseCase.execute(result.categories)
            result
        }
    }

}
