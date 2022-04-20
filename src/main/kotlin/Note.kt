data class Note(
    val id: Int,
    val ownerId: Int,
    var title: String,
    var text: String,
    val date: Int,
    val comments: Int,
    val readComments: Int,
    val viewUrl: String,
    var privacyView: String,
    var canComment: Int,
    var textWiki: String,
)
