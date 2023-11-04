package safronov.apps.data.data_source.remote.core

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import safronov.apps.domain.model.food.Food

interface FoodService {

    @GET(LINK_TO_GET_FOODS_BY_CATEGORY)
    fun getFoodsByCategory(
        @Body category: String?
    ): Call<Food>

    companion object {
        const val LINK_TO_GET_FOODS_BY_CATEGORY = "https://www.themealdb.com/api/json/v1/1/filter.php?c"
    }

}