package safronov.apps.data.data_source.remote.core

import retrofit2.Call
import retrofit2.http.GET
import safronov.apps.domain.model.food_category.FoodCategory

interface FoodCategoryService {

    @GET(LINK_TO_GET_ALL_FOOD_CATEGORIES)
    fun getFoodCategories(): Call<FoodCategory>

    companion object {
        const val LINK_TO_GET_ALL_FOOD_CATEGORIES = "https://www.themealdb.com/api/json/v1/1/categories.php"
    }

}