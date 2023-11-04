package safronov.apps.food.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import safronov.apps.data.data_source.local.core.AppDB
import safronov.apps.data.data_source.local.core.dao.food.FoodDao
import safronov.apps.data.data_source.local.core.dao.food_category.FoodCategoryDao
import safronov.apps.data.data_source.remote.core.FoodCategoryService
import safronov.apps.data.data_source.remote.core.FoodService
import safronov.apps.data.repository_impl.local.FoodCategoryRepositoryLocalImpl
import safronov.apps.data.repository_impl.local.FoodRepositoryLocalImpl
import safronov.apps.data.repository_impl.remote.FoodCategoryRepositoryRemoteImpl
import safronov.apps.data.repository_impl.remote.FoodRepositoryRemoteImpl
import safronov.apps.domain.repository.local.FoodCategoryRepositoryLocal
import safronov.apps.domain.repository.local.FoodRepositoryLocal
import safronov.apps.domain.repository.remote.FoodCategoryRepositoryRemote
import safronov.apps.domain.repository.remote.FoodRepositoryRemote

@Module
class DataModule {

    @Provides
    fun provideAppDB(context: Context): AppDB {
        return Room.databaseBuilder(
            context = context,
            AppDB::class.java, AppDB.APP_DB_NAME
        ).build()
    }

    @Provides
    fun provideFoodDao(
        appDB: AppDB
    ): FoodDao {
        return appDB.getFoodDao()
    }

    @Provides
    fun provideFoodCategoryDao(
        appDB: AppDB
    ): FoodCategoryDao {
        return appDB.getFoodCategoryDao()
    }

    @Provides
    fun provideInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor = interceptor).build()
    }

    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        convertorFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(convertorFactory)
            .build()
    }

    @Provides
    fun provideFoodCategoryService(
        retrofit: Retrofit
    ): FoodCategoryService {
        return retrofit.create(FoodCategoryService::class.java)
    }

    @Provides
    fun provideFoodService(
        retrofit: Retrofit
    ): FoodService {
        return retrofit.create(FoodService::class.java)
    }

    @Provides
    fun provideMutableFoodCategoryRepositoryLocal(
        foodCategoryDao: FoodCategoryDao
    ): FoodCategoryRepositoryLocal.MutableFoodCategoryRepositoryLocal {
        return FoodCategoryRepositoryLocalImpl(
            foodCategoryDao = foodCategoryDao
        )
    }

    @Provides
    fun provideGettingFoodCategoryRepositoryLocal(
        mutable: FoodCategoryRepositoryLocal.MutableFoodCategoryRepositoryLocal
    ): FoodCategoryRepositoryLocal.GettingFoodCategoryRepositoryLocal {
        return mutable
    }

    @Provides
    fun provideSavingFoodCategoryRepositoryLocal(
        mutable: FoodCategoryRepositoryLocal.MutableFoodCategoryRepositoryLocal
    ): FoodCategoryRepositoryLocal.SavingFoodCategoryRepositoryLocal {
        return mutable
    }

    @Provides
    fun provideMutableFoodRepositoryLocal(
        foodDao: FoodDao
    ): FoodRepositoryLocal.MutableFoodRepositoryLocal {
        return FoodRepositoryLocalImpl(
            foodDao = foodDao
        )
    }

    @Provides
    fun provideGettingFoodRepositoryLocal(
        mutable: FoodRepositoryLocal.MutableFoodRepositoryLocal
    ): FoodRepositoryLocal.GettingFoodRepositoryLocal {
        return mutable
    }

    @Provides
    fun provideSavingFoodRepositoryLocal(
        mutable: FoodRepositoryLocal.MutableFoodRepositoryLocal
    ): FoodRepositoryLocal.SavingFoodRepositoryLocal {
        return mutable
    }

    @Provides
    fun provideFoodCategoryRepositoryRemote(
        foodCategoryService: FoodCategoryService
    ): FoodCategoryRepositoryRemote {
        return FoodCategoryRepositoryRemoteImpl(
            foodCategoryService = foodCategoryService
        )
    }

    @Provides
    fun provideFoodRepositoryRemote(
        foodService: FoodService
    ): FoodRepositoryRemote {
        return FoodRepositoryRemoteImpl(
            foodService = foodService
        )
    }

}