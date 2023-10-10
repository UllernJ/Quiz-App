package hiof.mobilg11.quizapplication.model.user

data class Friend (
    var user: User,
    var status: FriendStatus
) {
    constructor(user: User) : this(user, FriendStatus.PENDING) {
        //TODO: Add friend request to database
    }
    fun acceptFriendRequest() {
        status = FriendStatus.ACCEPTED
        // TODO: Update friend status in database
    }
    fun declineFriendRequest() {
        status = FriendStatus.DECLINED
        //TODO: Remove friend request from database
    }
    fun sendFriendRequest(username: String) {
        //get user from database and create Friend object
        status = FriendStatus.PENDING
    }

    fun removeFriend() {
        //TODO: Remove friend from database
    }

    fun challengeFriend() {
        //TODO: Add challenge to database
    }

    fun acceptChallenge() {
        //TODO: Update challenge status in database
    }

}

enum class FriendStatus {
    PENDING,
    ACCEPTED,
    DECLINED
}