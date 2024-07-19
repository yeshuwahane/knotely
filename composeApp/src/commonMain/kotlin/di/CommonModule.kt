package di

import org.koin.dsl.module
import presentation.folders.FolderViewModel
import presentation.notedetails.NoteDetailViewModel
import presentation.notesgrid.NotesGridViewModel


fun commonModule() = module {

    single {
        FolderViewModel()
    }

    single {
        NotesGridViewModel()
    }

    single {
        NoteDetailViewModel()
    }



}