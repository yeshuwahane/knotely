package presentation.notesgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.notedetails.NoteDetailScreen
import kotlin.random.Random

class NotesGridScreen(
    val folderId:Int,
    val folder:String
):Screen {


    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        PersonalNotesScreen(navigator)

    }


    @Composable
    fun PersonalNotesScreen( navigator: Navigator) {

        val viewModel = getScreenModel<NotesGridViewModel>()

        val noteList = viewModel.noteList

        Scaffold(
            topBar = { TopAppBar { navigator.pop() } },
            backgroundColor =Color.White /*Color(0xFFEBECF0)*/ ,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(horizontal = 16.dp)
                ) {
                    Header(folder)
                    Spacer(modifier = Modifier.height(16.dp))
                    FilterChips()
                    Spacer(modifier = Modifier.height(16.dp))
                    NotesGrid(
                        onNoteClick = { id->
                            navigator.push(NoteDetailScreen(
                                id = id,
                                folder = folder
                            ))
                        },
                        notes = noteList
                    )
                }
            }
        )
    }

    @Composable
    fun TopAppBar(onBackClick: () -> Unit) {
            Row(
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { onBackClick.invoke() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF333333))
                }
            }
    }

    @Composable
    fun Header(folder: String) {
        Text(
            text = folder,
            color = Color(0xFF333333),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun FilterChips() {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val filters = listOf("filters", "important", "to-do", "favorites")

            items(filters){
                FilterChip(filter = it)
            }

        }
    }

    @Composable
    fun FilterChip(filter: String) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White, shape = CircleShape)
                .padding(vertical = 8.dp, horizontal = 6.dp)
                .border(2.dp,Color.Gray, RoundedCornerShape(8.dp))
        ) {
            Text(text = filter, color = Color(0xFF333333), modifier = Modifier.padding(10.dp))
        }
    }

    @Composable
    fun NotesGrid(onNoteClick: (id:Int) -> Unit,notes: List<NotesGridState>) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(notes) { note ->
                NoteCard(note, onNoteClick = {id->
                    onNoteClick.invoke(id)
                })
            }
        }
    }

    @Composable
    fun NoteCard(notesGridState: NotesGridState,onNoteClick: (id:Int) -> Unit) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = if (notesGridState.isImportant) Color(0xFF333333) else Color.White,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable {
                    onNoteClick.invoke(
                        notesGridState.id
                    )
                }
                ,
            elevation = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notesGridState.title,
                    color = if (notesGridState.isImportant) Color.White else Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = notesGridState.content,
                    color = if (notesGridState.isImportant) Color.White else Color.Black,
                    fontSize = 14.sp
                )
            }
        }
    }


}