import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class NoteServiceTest {
    private lateinit var noteService: NoteService

    @BeforeEach
    fun setUp() {
        noteService = NoteService()
    }

    @Test
    fun add() {
        noteService.add("ПРИВЕТ", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        assertEquals("ПРИВЕТ", noteService.getById(1, 2, true)?.title)
    }

    @Test
    fun createComment() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.createComment(1, 2, 3, "авм", "ку")
        assertEquals(1, noteService.comments.size)
    }

    @Test
    fun delete() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.add("ПРИВЕТ2", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.add("ПРИВЕТ3", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        val res = noteService.delete(1)
        assertEquals(2, noteService.notes.size)
    }

    @Test
    fun delete2() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.createComment(1, 2, 3, "авм", "ку")
        val res = noteService.delete(1)
        assertEquals(0, noteService.comments.size)
    }
    @Test
    fun delete3() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.createComment(1, 2, 3, "авм", "ку")
        assertThrowsExactly(
            NoteNotFoundException::class.java
        ){
            val res = noteService.delete(10)
        }
    }

    @Test
    fun edit() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.createComment(1, 2, 3, "авм", "ку")
        noteService.edit(1, "123", "234", 3, 5, "куа", "аьм")
        assertEquals("123", noteService.getById(1, 2, true)?.title)
    }

    @Test
    fun editComment() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.createComment(1, 2, 3, "авм", "ку")
        noteService.editComment(2, 3, "ам")
        assertEquals("ам", noteService.comments[0].message)
    }

    @Test
    fun get() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.add("ПРИВЕТ2", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.add("ПРИВЕТ3", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        val result = noteService.get("амуам", 3, 1, 3, 5).map { it.title }
        assertEquals(result, listOf("ПРИВЕТ1", "ПРИВЕТ2", "ПРИВЕТ3"))
    }

    @Test
    fun getById() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.add("ПРИВЕТ2", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.add("ПРИВЕТ3", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        val result = noteService.getById(1, 3, true)?.title
        assertEquals(result, "ПРИВЕТ1")
    }

    @Test
    fun testGetComments() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.createComment(1, 2, 3, "ка", "кцм")
        noteService.createComment(1, 3, 4, "df", "ref")
        noteService.createComment(1,6,5,"de","htn")
        val result =  noteService.getComments(1,3,4,5,6).map { it.message }
        assertEquals(result, listOf("ка","df","de"))
    }

    @Test
    fun restoreComment() {
        noteService.add("ПРИВЕТ1", "ОК", 1, 2, "ПИСТОЛЕТ", "ПУЛЯ", 3)
        noteService.createComment(1, 2, 3, "ка", "кцм")
        noteService.deleteComment(2,3)
        assertTrue(noteService.comments[0].removed)
    }
}