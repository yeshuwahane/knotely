package presentation.notedetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

class NoteDetailScreen(
    val id: Int,
    val folder: String
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = getScreenModel<NoteDetailViewModel>()

        val noteDetailState = viewModel.noteDetailState

        NoteDetailStatic(navigator, noteDetailState)
    }


    @Composable
    fun NoteDetailStatic(navigator: Navigator, noteDetailState: NoteDetailState) {
        val scrollState = rememberScrollState()
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        Scaffold(
            topBar = {
                DetailTopAppBar(onBackClick = {
                    navigator.pop()
                })
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(scrollState)
                            .nestedScroll(object : NestedScrollConnection {
                                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                    return Offset.Zero
                                }
                            })
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        NoteMetadata(folder = noteDetailState.noteFolder, date = noteDetailState.date)
                        Spacer(modifier = Modifier.height(8.dp))

                        // Title
                        var title by remember { mutableStateOf(TextFieldValue(noteDetailState.title)) }
                        NoteTitle(title) { newTitle -> title = newTitle }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Note content
                        var content by remember { mutableStateOf(TextFieldValue(noteDetailState.content)) }
                        NoteContent(content) { newContent -> content = newContent }
                    }
                }
            }
        )
    }

    @Composable
    fun DetailTopAppBar(onBackClick: () -> Unit) {
        TopAppBar(
            title = { Text("") },
            navigationIcon = {
                IconButton(onClick = { onBackClick.invoke() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* Handle share */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.Black)
                }
                IconButton(onClick = { /* Handle more options */ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.Black)
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }

    @Composable
    fun NoteMetadata(folder: String, date: String) {
        Text(
            text = "${folder.uppercase()} • $date",
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }

    @Composable
    fun NoteTitle(title: TextFieldValue, onTitleChange: (TextFieldValue) -> Unit) {
        TextField(
            value = title,
            onValueChange = onTitleChange,
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }

    @Composable
    fun NoteContent(content: TextFieldValue, onContentChange: (TextFieldValue) -> Unit) {
        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = content,
            onValueChange = onContentChange,
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 16.sp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions(onAny = { keyboardController?.hide() })
        )
    }

    @Composable
    fun TaskList() {
        Column {
            TaskItem("Reflection on my place in life", true)
            TaskItem("Read a non-fiction book about goals in life and how to set them correctly", false)
            TaskItem("Watch a life-affirming movie about...", false)
        }
    }

    @Composable
    fun TaskItem(task: String, isCompleted: Boolean) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Checkbox(
                checked = isCompleted,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    checkedColor = Color.Black,
                    uncheckedColor = Color.Gray
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = task,
                color = if (isCompleted) Color.Gray else Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.Start
            )
        }
    }


}

/*
val noteDetailState = NoteDetailState(
    title = "how I found a new dream",
    content = """
            what was my dream
            
            It was a cold and clear night and the stars were twinkling brightly above. My mum was reading the Ben 10 Omniverse bed time story book and I started falling asleep. I think it should be around midnight I was dreaming about something.
            
            The dream was so cool. I saw the houses in my street were made out of apples and chocolate and it was such a strange scene. My friends and I were walking around and instead of walking, the funny thing is, my dad was riding a green coloured jet ski to go to work. I cannot see anyone using...
        """.trimIndent(),
    noteFolder = "personal",
    date = "19/07/2024"

)*/
