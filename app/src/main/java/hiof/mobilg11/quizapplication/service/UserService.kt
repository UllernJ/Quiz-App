package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.user.User

interface UserService {
    suspend fun createUser()
    suspend fun setUsername(username: String): Boolean
    suspend fun isUsernameSet(): Boolean
    suspend fun getUser(): User?
}