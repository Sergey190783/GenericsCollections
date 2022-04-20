data class Comment(
    val id: Int,
    val uid: Int,
    val nid: Int,
    val oid: Int,
    val date: Int,
    var message: String,
    val reply_to: Int,
    var removed: Boolean
)
