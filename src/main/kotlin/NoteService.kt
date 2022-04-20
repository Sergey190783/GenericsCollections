class NoteService {
    val notes: MutableList<Note> = mutableListOf()
    val comments: MutableList<Comment> = mutableListOf()
    private fun getId(): Int {
        return if (notes.isNotEmpty()) {
            notes.last().id + 1
        } else {
            1
        }
    }

    fun add(
        title: String,
        text: String,
        privacy: Int,
        commentPrivacy: Int,
        privacyView: String,
        privacyComment: String,
        ownerId: Int
    ): Int {
        val newNote = Note(getId(), ownerId, title, text, 12102020, 33, 22, "пока", privacyView, 55, "день")
        notes.add(newNote)
        return newNote.id
    }

    fun createComment(
        noteId: Int,
        ownerId: Int,
        replyTo: Int,
        message: String,
        guid: String
    ): Int {
        val newComment = Comment(getId(), 1, noteId, ownerId, 10032022, message, replyTo, false)
        comments.add(newComment)
        return newComment.id
    }

    fun delete(
        noteId: Int
    ): Int {
        return if (notes.removeIf { it.id == noteId }) {
            comments.removeIf { c -> notes.find { it.id == c.nid } == null}
            1
        } else {
            throw NoteNotFoundException()
        }
    }

    fun deleteComment(
        commentId: Int,
        ownerId: Int
    ): Int {
        return if (comments.find { it.id == commentId } != null) {
            comments.find { it.id == commentId }?.removed = true
            1
        } else {
            throw NoteNotFoundException()
        }
    }

    fun edit(
        noteId: Int,
        title: String,
        text: String,
        privacy: Int,
        commentPrivacy: Int,
        privacyView: String,
        privacyComment: String,
    ): Int {
        return if (notes.find { it.id == noteId } != null) {
            notes.find { it.id == noteId }?.apply {
                this.title = title
                this.text = text
                this.privacyView = privacyView
            }
            1
        } else {
            0
        }
    }

    fun editComment(
        commentId: Int,
        ownerId: Int,
        message: String,
    ): Int {
        return if (comments.find { it.id == commentId } != null) {
            comments.find { it.id == commentId }?.apply {
                this.message = message
            }
            1
        } else {
            0
        }
    }

    fun get(
        noteIds: String,
        userId: Int,
        offset: Int,
        count: Int,
        sort: Int,
    ): List<Note> {
        return notes.filter { it.ownerId == userId }
    }

    fun getById(
        noteId: Int,
        ownerId: Int,
        needWiki: Boolean,
    ): Note? {
        return notes.find { it.id == noteId }
    }

    fun getComments(
        noteId: Int,
        ownerId: Int,
        sort: Int,
        offset: Int,
        count: Int,
    ): List<Comment> {
        return comments.filter { it.nid == noteId }
    }

    fun restoreComment(
        commentId: Int,
        ownerId: Int,
        ): Int {
        return if (comments.find { it.id == commentId } != null) {
            comments.find { it.id == commentId }?.removed = false
            1
        } else {
            0
        }
    }
}