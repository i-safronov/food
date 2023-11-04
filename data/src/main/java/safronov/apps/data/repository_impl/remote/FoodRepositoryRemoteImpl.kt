package safronov.apps.data.repository_impl.remote

import android.util.Log
import safronov.apps.data.data_source.remote.core.FoodService
import safronov.apps.data.repository_impl.remote.model.FoodRemoteEntity
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import java.lang.Exception
import java.lang.IllegalStateException

class FoodRepositoryRemoteImpl(
    private val foodService: FoodService
): FoodRepositoryRemote {

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): Food {
        try {
            val call = foodService.getFoodsByCategory(category = category.strCategory)
            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                val result = response.body()!!
                val converted = FoodRemoteEntity.convertFoodRemoteEntityToFood(
                    category, result
                )
                return converted
            } else {
                throw IllegalStateException("result code from server isn't successful or body is null")
            }
        } catch (e: Exception) {
            throw DomainException(e.message, e)
        }
    }

}