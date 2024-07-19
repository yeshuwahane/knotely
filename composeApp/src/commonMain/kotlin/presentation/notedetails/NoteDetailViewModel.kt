package presentation.notedetails

import cafe.adriel.voyager.core.model.ScreenModel

class NoteDetailViewModel() : ScreenModel {

    val noteDetailState = NoteDetailState(
        title = "how I found a new dream",
        content = """
            what was my dream
            
            It was a cold and clear night and the stars were twinkling brightly above. My mum was reading the Ben 10 Omniverse bed time story book and I started falling asleep. I think it should be around midnight I was dreaming about something.
            
            The dream was so cool. I saw the houses in my street were made out of apples and chocolate and it was such a strange scene. My friends and I were walking around and instead of walking, the funny thing is, my dad was riding a green coloured jet ski to go to work. I cannot see anyone using...
        """.trimIndent(),
        noteFolder = "personal",
        date = "19/07/2024"

    )

}