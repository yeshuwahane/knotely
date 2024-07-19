package presentation.notesgrid

data class NotesGridState(
    val title:String,
    val content:String,
    val id:Int=0,
    val isImportant:Boolean = false
)
