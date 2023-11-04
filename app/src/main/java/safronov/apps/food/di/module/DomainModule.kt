package safronov.apps.food.di.module

import dagger.Module
import dagger.Provides
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal
import safronov.apps.domain.repository.local.FoodRepositoryLocal
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote
import safronov.apps.domain.repository.remote.FoodRepositoryRemote
import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.category.SaveFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.food.GetAllFoodsLocalUseCase
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.local.food.SaveFoodsLocalUseCase
import safronov.apps.domain.use_case.remote.category.GetFoodCategoriesRemoteUseCase
import safronov.apps.domain.use_case.remote.food.GetFoodsByCategoryRemoteUseCase

@Module
class DomainModule {

    @Provides
    fun provideGetFoodCategoriesLocalUseCase(
        foodCategoryRepositoryLocal: FoodCategoryRepositoryLocal.GettingFoodCategoryRepositoryLocal
    ): GetFoodCategoriesLocalUseCase {
        return GetFoodCategoriesLocalUseCase(foodCategoryRepositoryLocal)
    }

    @Provides
    fun provideSaveFoodCategoriesLocalUseCase(
        foodCategoryRepositoryLocal: FoodCategoryRepositoryLocal.SavingFoodCategoryRepositoryLocal
    ): SaveFoodCategoriesLocalUseCase {
        return SaveFoodCategoriesLocalUseCase(foodCategoryRepositoryLocal)
    }

    @Provides
    fun provideGetAllFoodsLocalUseCase(
        repository: FoodRepositoryLocal.GettingFoodRepositoryLocal
    ): GetAllFoodsLocalUseCase {
        return GetAllFoodsLocalUseCase(repository)
    }

    @Provides
    fun provideGetFoodsByCategoryLocalUseCase(
        repository: FoodRepositoryLocal.GettingFoodRepositoryLocal
    ): GetFoodsByCategoryLocalUseCase {
        return GetFoodsByCategoryLocalUseCase(repository)
    }

    @Provides
    fun provideSaveFoodsLocalUseCase(
        repository: FoodRepositoryLocal.SavingFoodRepositoryLocal
    ): SaveFoodsLocalUseCase {
        return SaveFoodsLocalUseCase(repository)
    }

    @Provides
    fun provideGetFoodCategoriesRemoteUseCase(
        foodCategoryRepositoryRemote: FoodCategoryRepositoryRemote,
        getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase,
        saveFoodCategoriesLocalUseCase: SaveFoodCategoriesLocalUseCase
    ): GetFoodCategoriesRemoteUseCase {
        return GetFoodCategoriesRemoteUseCase(
            foodCategoryRepositoryRemote,
            getFoodCategoriesLocalUseCase,
            saveFoodCategoriesLocalUseCase
        )
    }

    @Provides
    fun provideGetFoodsByCategoryRemoteUseCase(
        foodRepositoryRemote: FoodRepositoryRemote,
        getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase,
        saveFoodsLocalUseCase: SaveFoodsLocalUseCase
    ): GetFoodsByCategoryRemoteUseCase {
        return GetFoodsByCategoryRemoteUseCase(
            foodRepositoryRemote,
            getFoodsByCategoryLocalUseCase,
            saveFoodsLocalUseCase
        )
    }

}