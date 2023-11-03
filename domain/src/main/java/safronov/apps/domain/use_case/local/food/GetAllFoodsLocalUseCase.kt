package safronov.apps.domain.use_case.local.food

import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.repository.local.FoodRepositoryLocal

class GetAllFoodsLocalUseCase(
    private val foodRepositoryLocal: FoodRepositoryLocal.GettingFoodRepositoryLocal
) {

    suspend fun execute(): List<FoodItem> {
        return foodRepositoryLocal.getAllFoods()
    }

}
