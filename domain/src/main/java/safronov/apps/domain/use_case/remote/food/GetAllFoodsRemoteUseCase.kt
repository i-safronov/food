package safronov.apps.domain.use_case.remote.food

import safronov.apps.domain.model.food.Food
import safronov.apps.domain.repository.remote.FoodRepositoryRemote

class GetAllFoodsRemoteUseCase(
    private val foodRepositoryRemote: FoodRepositoryRemote
) {

    suspend fun execute(): List<Food> {
        return foodRepositoryRemote.getAllFoods()
    }

}
