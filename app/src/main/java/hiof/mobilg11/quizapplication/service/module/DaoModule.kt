package hiof.mobilg11.quizapplication.service.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hiof.mobilg11.quizapplication.dao.CategoryDao
import hiof.mobilg11.quizapplication.dao.GameDao
import hiof.mobilg11.quizapplication.dao.QuestionDao
import hiof.mobilg11.quizapplication.dao.UserDao
import hiof.mobilg11.quizapplication.service.UserCacheService

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideCategoryDao(firestore: FirebaseFirestore): CategoryDao {
        return CategoryDao(firestore)
    }

    @Provides
    fun provideQuestionDao(firestore: FirebaseFirestore): QuestionDao {
        return QuestionDao(firestore)
    }

    @Provides
    fun provideUserDao(firestore: FirebaseFirestore, auth: FirebaseAuth, gameDao: GameDao): UserDao {
        return UserDao(firestore, auth, gameDao)
    }

    @Provides
    fun provideGameDao(firestore: FirebaseFirestore): GameDao {
        return GameDao(firestore)
    }

}