package safronov.apps.data.repository_impl.remote

import safronov.apps.data.data_source.remote.core.FoodCategoryService
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote
import java.lang.Exception
import java.lang.IllegalStateException

class FoodCategoryRepositoryRemoteImpl(
    private val foodCategoryService: FoodCategoryService
): FoodCategoryRepositoryRemote {

    override suspend fun getFoodCategories(): FoodCategory {
        try {
            val call = foodCategoryService.getFoodCategories()
            val result = call.execute()
            if (result.isSuccessful && result.body() != null) {
                return result.body()!!
            } else {
                throw IllegalStateException("result code from server isn't successful or body is null")
            }
        } catch (e: Exception) {
            throw DomainException(e.message, e)
        }
    }

}