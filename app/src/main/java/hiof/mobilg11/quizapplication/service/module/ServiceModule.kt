package hiof.mobilg11.quizapplication.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hiof.mobilg11.quizapplication.service.AccountService
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.impl.AccountServiceImpl
import hiof.mobilg11.quizapplication.service.impl.UserCacheServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideUserCacheService(impl: UserCacheServiceImpl): UserCacheService

    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}