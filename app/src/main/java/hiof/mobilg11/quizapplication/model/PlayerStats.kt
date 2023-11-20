package hiof.mobilg11.quizapplication.model

data class PlayerStats(
    var username: String = "",
    var gamesPlayed: Int = 0,
    var gamesWon: Int = 0,
    var gamesLost: Int = 0,
    var gamesDraw: Int = 0,
)
