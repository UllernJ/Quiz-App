package hiof.mobilg11.quizapplication.service.impl

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.service.UserCacheService
import javax.inject.Inject

class UserCacheServiceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    UserCacheService {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val adapter = moshi.adapter(User::class.java)
    override fun saveUser(user: User) {
        sharedPreferences.edit()
            .putString("User", adapter.toJson(user))
            .apply()
    }

    override fun getUser(): User? {
        val json = sharedPreferences.getString("User", null) ?: return null
        return adapter.fromJson(json)
    }

    override fun clearUser() {
        sharedPreferences.edit()
            .remove("User")
            .apply()
    }

    override fun updateUser(user: User) {
        sharedPreferences.edit()
            .putString("User", adapter.toJson(user))
            .apply()
    }
}