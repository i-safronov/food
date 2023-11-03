package safronov.apps.domain.use_case.remote.food

import safronov.apps.domain.model.food.Food
import safronov.apps.domain.repository.remote.FoodRepositoryRemote

class GetAllFoodsRemoteUseCase(
    private val foodRepositoryRemote: FoodRepositoryRemote
) {

    //TODO change logic to save remote data to storage if they're unique
    suspend fun execute(): List<Food> {
        return foodRepositoryRemote.getAllFoods()
    }

}
