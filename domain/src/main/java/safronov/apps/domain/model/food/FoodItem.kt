package safronov.apps.domain.model.food

data class FoodItem(
    val idMeal: String?,
    val strMeal: String?,
    val strMealThumb: String?,
    val foodCategory: String? = null
)