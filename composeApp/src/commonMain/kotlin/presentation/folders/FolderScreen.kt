package presentation.folders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.notesgrid.NotesGridScreen


class FolderScreen() :Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow


        //folderscreen
        FolderStatic()

    }


    @Composable
    fun FolderStatic() {

        var showDialog by remember { mutableStateOf(false) }

        val navigator = LocalNavigator.currentOrThrow

        val viewModel = getScreenModel<FolderViewModel>()

        val folderList = viewModel.folderList

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDialog = true },
                    backgroundColor = Color(0xFFE53935),
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Folder")
                }
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Text(
                    text = "folders",
                    color = Color.White,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn {
                    items(folderList) {
                        FolderCard(it, onFolderClick = { folderName, folderId ->

                            navigator.push(NotesGridScreen(
                                folderId = folderId,
                                folder = folderName
                            ))
                        })
                    }
                }

            }
        }

        if (showDialog) {
            AddFolderDialog(onDismiss = { showDialog = false }, onAddFolder = { folderName ->
                // Handle adding the folder
                showDialog = false
            })
        }

    }


    @Composable
    fun FolderCard(folderState: FolderState, onFolderClick: (folderName:String,folderId:Int) -> Unit) {
        Card(
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color(0xFF333333), // Dark gray color
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(140.dp)
                .clickable {
                    onFolderClick.invoke(
                        folderState.title,
                        folderState.folderId
                    )
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top, // Aligns the Column to the top
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = folderState.title,
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start // Align text to the start

                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                        Text(
                            text = folderState.count.toString(),
                            color = Color.White,
                            fontSize = 48.sp,
                            textAlign = TextAlign.Start // Align text to the start
                        )
                    }
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Arrow",
                    tint = Color.Gray
                )
            }
        }
    }


    @Composable
    fun AddFolderDialog(onDismiss: () -> Unit, onAddFolder: (String) -> Unit) {
        var folderName by remember { mutableStateOf("") }


        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties()
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).hideKeyboardOnOutsideClick()
                ) {
                    Text(
                        text = "Add Folder",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = folderName,
                        onValueChange = { folderName = it },
                        label = { Text("Folder Name") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Gray,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = Color.Gray
                        ),

                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Gray
                            )

                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { onAddFolder(folderName) },
                            enabled = folderName.isNotBlank(),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }

}

fun Modifier.hideKeyboardOnOutsideClick(): Modifier = composed {
    val controller = LocalSoftwareKeyboardController.current
    this then Modifier.noRippleClickable {
        controller?.hide()
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this then Modifier.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}



