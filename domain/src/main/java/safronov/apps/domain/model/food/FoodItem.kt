package safronov.apps.domain.model.food

data class FoodItem(
    val idMeal: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    var foodCategory: String? = null
)