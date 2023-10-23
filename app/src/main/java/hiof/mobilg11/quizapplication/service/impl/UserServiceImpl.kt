package hiof.mobilg11.quizapplication.service.impl

import hiof.mobilg11.quizapplication.dao.UserDao
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.service.UserService
import javax.inject.Inject

class UserServiceImpl @Inject constructor(private val userDao: UserDao): UserService {
    override suspend fun setUsername(username: String): Boolean {
        return userDao.setUsername(username)
    }

    override suspend fun create() {
        userDao.createUser()
    }

    override suspend fun isUsernameSet(): Boolean {
        return userDao.isUsernameSet()
    }

    override suspend fun get(): User? {
        return userDao.getUser()
    }

    override suspend fun find(username: String): List<User?> {
        return userDao.findUser(username)
    }

    override suspend fun getLastChallengedUsers(username: String, limit: Int): List<String> {
        return userDao.getLastChallengedUsers(username, limit)
    }
}