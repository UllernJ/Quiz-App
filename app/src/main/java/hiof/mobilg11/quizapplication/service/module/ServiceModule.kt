package hiof.mobilg11.quizapplication.service.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hiof.mobilg11.quizapplication.service.UserCache
import hiof.mobilg11.quizapplication.service.impl.UserCacheImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("user", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserCache(sharedPreferences: SharedPreferences): UserCache {
        return UserCacheImpl(sharedPreferences)
    }
}