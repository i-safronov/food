package safronov.apps.domain.use_case.local.food

import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.repository.local.FoodRepositoryLocal

class SaveFoodsLocalUseCase(
    private val foodRepositoryLocal: FoodRepositoryLocal.SavingFoodRepositoryLocal
) {

    suspend fun execute(list: List<FoodItem>): List<FoodItem> {
        return foodRepositoryLocal.saveFoods(list)
    }

}
