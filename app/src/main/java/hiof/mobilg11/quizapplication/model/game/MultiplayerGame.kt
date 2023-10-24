package hiof.mobilg11.quizapplication.model.game

import com.google.firebase.firestore.DocumentId
import hiof.mobilg11.quizapplication.model.Question
import hiof.mobilg11.quizapplication.model.User

data class MultiplayerGame(

    @DocumentId val uuid: String = "",
    var host: String = "",
    var opponent: String = "",
    var gameState: GameState = GameState.REQUESTING_GAME,

    var winner: String? = null,

    var hostScore: Int = 0,
    var opponentScore: Int = 0,

    var numberOfRounds: Int = 5,
    var roundQuestionsReferences: MutableList<Question> = mutableListOf(),
    var roundIndex: Int = 0,

    var categoriesPlayedReferences: MutableList<String> = mutableListOf(),

    var lastUpdated: Long = System.currentTimeMillis()
    )
