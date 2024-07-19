package presentation.notedetails

data class NoteDetailState(
    val title:String,
    val content:String,
    val noteFolder:String,
    val date:String,
    val id:Int=0
)
