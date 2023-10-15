package hiof.mobilg11.quizapplication.service.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hiof.mobilg11.quizapplication.service.AccountService
import hiof.mobilg11.quizapplication.service.CategoryService
import hiof.mobilg11.quizapplication.service.GameService
import hiof.mobilg11.quizapplication.service.QuestionService
import hiof.mobilg11.quizapplication.service.UserCacheService
import hiof.mobilg11.quizapplication.service.UserService
import hiof.mobilg11.quizapplication.service.impl.AccountServiceImpl
import hiof.mobilg11.quizapplication.service.impl.CategoryServiceImpl
import hiof.mobilg11.quizapplication.service.impl.GameServiceImpl
import hiof.mobilg11.quizapplication.service.impl.QuestionServiceImpl
import hiof.mobilg11.quizapplication.service.impl.UserCacheServiceImpl
import hiof.mobilg11.quizapplication.service.impl.UserServiceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideUserCacheService(impl: UserCacheServiceImpl): UserCacheService

    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    @Binds
    abstract fun provideCategoryService(impl: CategoryServiceImpl): CategoryService

    @Binds
    abstract fun provideQuestionService(impl: QuestionServiceImpl): QuestionService

    @Binds
    abstract fun provideUserService(impl: UserServiceImpl): UserService

    @Binds
    abstract fun provideGameService(impl: GameServiceImpl): GameService
}