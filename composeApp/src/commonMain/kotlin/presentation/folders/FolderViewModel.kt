package presentation.folders

import cafe.adriel.voyager.core.model.ScreenModel

class FolderViewModel() : ScreenModel {


    private val folder1 = FolderState(title = "personal notes", count = 23)
    private val folder2 = FolderState(title = "feelings", count = 13)
    private val folder3 = FolderState(title = "personal notes", count = 43)
    private val folder4 = FolderState(title = "morning pages", count = 63)
    private val folder5 = FolderState(title = "great ideas", count = 223)

    val folderList = listOf(folder1, folder2, folder3, folder4, folder5)
}