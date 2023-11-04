package safronov.apps.domain.use_case.remote.food

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import java.lang.IllegalStateException

private class FakeFoodRepositoryRemote: FoodRepositoryRemote {

    var isNeedToThrowException = false
    val dataToReturn =  Food(foodItems = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    ))

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): Food {
        throw IllegalStateException("don't use this method -_-")
    }

}