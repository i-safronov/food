package safronov.apps.data.data_source.remote.core

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import safronov.apps.data.repository_impl.remote.model.FoodRemoteEntity

interface FoodService {

    @GET(LINK_TO_GET_FOODS_BY_CATEGORY)
    fun getFoodsByCategory(
        @Query("c") category: String?
    ): Call<FoodRemoteEntity>

    companion object {
        private const val LINK_TO_GET_FOODS_BY_CATEGORY = "https://www.themealdb.com/api/json/v1/1/filter.php"
    }

}