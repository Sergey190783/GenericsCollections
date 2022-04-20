data class GetByIdResult(
    val privacy: Int,
    val commentPrivacy: Int,
    val canComment: Int,
    val notes:List<Note>,
    )
