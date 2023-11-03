package safronov.apps.domain.repository.remote

import safronov.apps.domain.model.food_category.FoodCategory

interface FoodCategoryRepositoryRemote {

    suspend fun getFoodCategories(): List<FoodCategory>

}