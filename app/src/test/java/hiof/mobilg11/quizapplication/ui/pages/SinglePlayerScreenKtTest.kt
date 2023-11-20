package hiof.mobilg11.quizapplication.ui.pages

import hiof.mobilg11.quizapplication.model.Category
import hiof.mobilg11.quizapplication.ui.screen.singleplayer.filterCategories
import junit.framework.TestCase.assertEquals
import org.junit.Test
class SinglePlayerScreenKtTest {

    private var categories: List<Category> = listOf(
        Category("General Knowledge"),
        Category("Books"),
        Category("Film"),
        Category("Music"),
        Category("Musicals & Theatres"),
        Category("Television"),
        Category("Video Games"),
        Category("Board Games"),
        Category("Science & Nature"),
        Category("Computers"),
    )
    @Test
    fun testSearchForGeneralKnowledgeAndBeSizeOne() {
        val searchQuery = "General Knowledge"
        val result = filterCategories(searchQuery, categories)
        assertEquals(result[0].name, "General Knowledge")
        assertEquals(result.size, 1)
    }

    @Test
    fun testUnknownCategoryShouldBeEmpty() {
        val searchQuery = "Unknown Category"
        val result = filterCategories(searchQuery, categories)
        assertEquals(result.size, 0)
    }

    @Test
    fun testSearchForGamesShouldBeSizeTwo() {
        val searchQuery = "Games"
        val result = filterCategories(searchQuery, categories)
        assertEquals(result.size, 2)
    }

}