package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    suspend fun create()
    suspend fun setUsername(username: String): Boolean
    suspend fun isUsernameSet(): Boolean
    fun get(uuid: String): Flow<User?>
    suspend fun find(username: String): List<User?>
//    suspend fun getLastChallengedUsers(username: String, limit: Int): List<String>
}