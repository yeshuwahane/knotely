package presentation.notesgrid

import cafe.adriel.voyager.core.model.ScreenModel

class NotesGridViewModel():ScreenModel {

    private val note1 = NotesGridState("how I found a new dream" , "Today my wish has come true – to devote the whole day")
    private val note2 = NotesGridState("my plan for the future me" , "Imagine the perfect life, the perfect family, dream house...",isImportant = true)
    private val note3 = NotesGridState("my personal strengths" , "One of my greatest strengths at work...")
    private val note4 = NotesGridState("my faith in love" , "I've always believed in fate and destiny. Everyone...")

    val noteList = listOf(note1,note2,note3,note4)




}