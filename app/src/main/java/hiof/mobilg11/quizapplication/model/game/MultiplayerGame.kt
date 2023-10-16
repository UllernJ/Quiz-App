package hiof.mobilg11.quizapplication.model.game

import com.google.firebase.firestore.DocumentId
import hiof.mobilg11.quizapplication.model.User

data class MultiplayerGame(

    @DocumentId val uuid: String = "",
    var host: User = User(),
    var opponent: User = User(),
    var gameState: GameState = GameState.REQUESTING_GAME,

    var winner: User? = null,

    var hostScore: Int = 0,
    var opponentScore: Int = 0,

    var numberOfRounds: Int = 0,
    var roundQuestionsReferences: List<String> = listOf(),
    var roundIndex: Int = 0,

    var categoriesPlayedReferences: List<String> = listOf(),

    var lastUpdated: Long = System.currentTimeMillis()
    )
