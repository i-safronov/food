package safronov.apps.food.ui.fragment.main.main_content.menu.view_model

import org.junit.Assert.*
import safronov.apps.domain.exception.DomainException
import safronov.apps.domain.model.food.Food
import safronov.apps.domain.model.food.FoodItem
import safronov.apps.domain.model.food_category.FoodCategory
import safronov.apps.domain.model.food_category.FoodCategoryItem
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal
import safronov.apps.domain.repository.local.FoodRepositoryLocal
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import java.lang.IllegalStateException

/*
* actions:
* - get data from internet when network is available
* - get data when network is unavailable(should get data from database)
* - get data from internet when network is available but when calling request, network has been unavailable(should cancel job to get data from
* internet and get data from database)
* - get data from database(when network is unavailable) but when calling request, should get data from database 
* */

class FragmentMenuViewModelTest

private class FakeFoodCategoryRepositoryLocalGetting: FoodCategoryRepositoryLocal.GettingFoodCategoryRepositoryLocal {

    var isNeedToThrowException = false
    val dataToReturn = listOf(
        FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
    )

    override suspend fun getFoodCategories(): List<FoodCategoryItem> {
        if (isNeedToThrowException) throw DomainException("domain exception")
        return dataToReturn
    }

}

private class FakeFoodCategoryRepositoryLocalSaving: FoodCategoryRepositoryLocal.SavingFoodCategoryRepositoryLocal {

    var isNeedToThrowException = false
    var countOfRequest = 0
    var dataToReturn = listOf(
        FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
    )

    override suspend fun saveFoodCategories(list: List<FoodCategoryItem>): List<FoodCategoryItem> {
        if (isNeedToThrowException) throw DomainException("some exception")
        dataToReturn = list
        countOfRequest++
        return dataToReturn
    }


}

private class FakeFoodRepositoryLocalGetting: FoodRepositoryLocal.GettingFoodRepositoryLocal {

    var isNeedToThrowException = false
    var categoryRequest: FoodCategoryItem? = null
    val dataToReturn = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    )

    override suspend fun getAllFoods(): List<FoodItem> {
        throw IllegalStateException("don't use this method -.- ")
    }

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): List<FoodItem> {
        if (isNeedToThrowException) throw DomainException("some exception")
        categoryRequest = category
        return dataToReturn
    }

}

private class FakeFoodRepositoryLocalSaving: FoodRepositoryLocal.SavingFoodRepositoryLocal {

    var isNeedToThrowException = false
    var countOfRequest = 0
    val dataToReturn = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    )

    override suspend fun saveFoods(list: List<FoodItem>): List<FoodItem> {
        if (isNeedToThrowException) throw DomainException("some exception")
        countOfRequest++
        return dataToReturn
    }


}

private class FakeFoodCategoryRepositoryRemoteGetting: FoodCategoryRepositoryRemote {

    val dataToReturn =  FoodCategory(listOf(
        FoodCategoryItem(idCategory = "sdf", strCategory = "asdfa", strCategoryDescription = "s", strCategoryThumb = "asdfa")
    ))
    var isNeedToThrowException = false

    override suspend fun getFoodCategories(): FoodCategory {
        if (isNeedToThrowException) throw DomainException("some exception")
        return dataToReturn
    }

}

private class FakeFoodRepositoryRemote1: FoodRepositoryRemote {

    var isNeedToThrowException = false
    var categoryRequest: FoodCategoryItem? = null
    val dataToReturn = Food(foodItems = listOf(
        FoodItem(
            idMeal = "dfas", strMeal = "fasdf", strMealThumb = "fasdfa"
        )
    ))

    override suspend fun getAllFoods(): Food {
        if (isNeedToThrowException) throw DomainException("some exception")
        return dataToReturn
    }

    override suspend fun getFoodsByCategory(category: FoodCategoryItem): Food {
        if (isNeedToThrowException) throw DomainException("some exception")
        categoryRequest = category
        return dataToReturn
    }

}