package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.User

interface UserService {
    suspend fun create()
    suspend fun setUsername(username: String): Boolean
    suspend fun isUsernameSet(): Boolean
    suspend fun get(): User?
    suspend fun find(username: String): List<User?>
    suspend fun getLastChallengedUsers(username: String, limit: Int): List<String>
}