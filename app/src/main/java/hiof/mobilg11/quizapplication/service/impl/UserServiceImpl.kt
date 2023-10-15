package hiof.mobilg11.quizapplication.service.impl

import hiof.mobilg11.quizapplication.dao.UserDao
import hiof.mobilg11.quizapplication.model.user.User
import hiof.mobilg11.quizapplication.service.UserService
import javax.inject.Inject

class UserServiceImpl @Inject constructor(private val userDao: UserDao): UserService {
    override suspend fun setUsername(username: String): Boolean {
        return userDao.setUsername(username)
    }

    override suspend fun createUser() {
        userDao.createUser()
    }

    override suspend fun isUsernameSet(): Boolean {
        return userDao.isUsernameSet()
    }

    override suspend fun getUser(): User? {
        return userDao.getUser()
    }

}