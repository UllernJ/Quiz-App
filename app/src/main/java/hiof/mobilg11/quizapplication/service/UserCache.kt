package hiof.mobilg11.quizapplication.service

import hiof.mobilg11.quizapplication.model.user.User

interface UserCache {
    fun saveUser(user: User)
    fun getUser(): User?
    fun updateUser(user: User)
    fun clearUser()
}