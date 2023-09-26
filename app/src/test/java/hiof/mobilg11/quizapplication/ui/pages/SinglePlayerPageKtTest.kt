package hiof.mobilg11.quizapplication.ui.pages

import hiof.mobilg11.quizapplication.model.Category
import junit.framework.TestCase.assertEquals
import org.junit.Test
class SinglePlayerPageKtTest {

    private var categories: List<Category> = listOf(
        Category("General Knowledge", "General Knowledge"),
        Category("Books", "Entertainment: Books"),
        Category("Film", "Entertainment: Film"),
        Category("Music", "Entertainment: Music"),
        Category("Musicals & Theatres", "Entertainment: Musicals & Theatres"),
        Category("Television", "Entertainment: Television"),
        Category("Video Games", "Entertainment: Video Games"),
        Category("Board Games", "Entertainment: Board Games"),
        Category("Science & Nature", "Science & Nature"),
        Category("Computers", "Science: Computers"),
        Category("Mathematics", "Science: Mathematics"),
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