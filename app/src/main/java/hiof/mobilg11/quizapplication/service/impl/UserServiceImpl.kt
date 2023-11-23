package hiof.mobilg11.quizapplication.service.impl

import hiof.mobilg11.quizapplication.dao.UserDao
import hiof.mobilg11.quizapplication.model.User
import hiof.mobilg11.quizapplication.service.UserService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserServiceImpl @Inject constructor(private val userDao: UserDao): UserService {
    override suspend fun setUsername(username: String): Boolean {
        return userDao.setUsername(username)
    }

    override suspend fun create(user: User) {
        userDao.createUser(user)
    }

    override suspend fun isUsernameSet(username: String): Boolean {
        return userDao.isUsernameSet(username)
    }

    override fun get(uuid: String): Flow<User?> {
        return userDao.getUser(uuid)
    }

    override suspend fun find(username: String): List<User?> {
        return userDao.findUser(username)
    }
}