package safronov.apps.food.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import safronov.apps.domain.use_case.local.category.GetFoodCategoriesLocalUseCase
import safronov.apps.domain.use_case.local.food.GetFoodsByCategoryLocalUseCase
import safronov.apps.domain.use_case.remote.category.GetFoodCategoriesRemoteUseCase
import safronov.apps.domain.use_case.remote.food.GetFoodsByCategoryRemoteUseCase
import safronov.apps.food.ui.base.coroutines.DispatchersList
import safronov.apps.food.ui.fragment.main.main_content.menu.banner.BannerService
import safronov.apps.food.ui.fragment.main.main_content.menu.view_model.FragmentMenuViewModelFactory
import safronov.apps.food.ui.system.network.ConnectivityObserver
import safronov.apps.food.ui.system.network.NetworkConnectivityObserver

@Module
class AppModule(
     private val context: Context
) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideBannerService(
        context: Context
    ): BannerService {
        return BannerService.Base(context)
    }

    @Provides
    fun provideDispatchersList(): DispatchersList {
        return DispatchersList.Base()
    }

    @Provides
    fun provideConnectivityObserver(): ConnectivityObserver {
        return NetworkConnectivityObserver(context = context)
    }

    @Provides
    fun provideFragmentMenuViewModelFactory(
         dispatchersList: DispatchersList,
         connectivityObserver: ConnectivityObserver,
         getFoodCategoriesRemoteUseCase: GetFoodCategoriesRemoteUseCase,
         getFoodsByCategoryRemoteUseCase: GetFoodsByCategoryRemoteUseCase,
         getFoodCategoriesLocalUseCase: GetFoodCategoriesLocalUseCase,
         getFoodsByCategoryLocalUseCase: GetFoodsByCategoryLocalUseCase,
         bannerService: BannerService
    ): FragmentMenuViewModelFactory {
        return FragmentMenuViewModelFactory(
            dispatchersList, connectivityObserver, getFoodCategoriesRemoteUseCase,
            getFoodsByCategoryRemoteUseCase, getFoodCategoriesLocalUseCase, getFoodsByCategoryLocalUseCase,
            bannerService
        )
    }

}