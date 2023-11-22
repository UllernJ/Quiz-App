package hiof.mobilg11.quizapplication.model

import kotlin.math.roundToInt

data class PlayerStats(
    var username: String = "",
    var gamesPlayed: Int = 0,
    var gamesWon: Int = 0,
    var gamesLost: Int = 0,
    var gamesDraw: Int = 0,
) {
    fun getWinPercentage(): Int {
        if (gamesPlayed > 0) {
            return ((gamesWon.toDouble() / gamesPlayed.toDouble()) * 100).roundToInt()
        }
        return 0
    }
}

