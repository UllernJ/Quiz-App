package hiof.mobilg11.quizapplication.service.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun auth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun firestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}