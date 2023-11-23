package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    suspend fun create(user: User)
    suspend fun setUsername(username: String): Boolean
    suspend fun isUsernameSet(): Boolean
    fun get(uuid: String): Flow<User?>
    suspend fun find(username: String): List<User?>
}