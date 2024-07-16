package presentation.folders

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen


class FolderScreen() :Screen{

    @Composable
    override fun Content() {
        FolderStatic()

    }
}

@Composable
fun FolderStatic() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle FAB click */ },
                backgroundColor = Color(0xFFD50000) // Red color
            ) {
                Text("+", color = Color.White, fontSize = 24.sp)
            }
        }
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

            FolderCard(title = "personal notes", count = 23)
            FolderCard(title = "feelings", count = 481)
            FolderCard(title = "morning pages", count = 15)
            FolderCard(title = "great ideas", count = 62)
        }
    }

}




@Composable
fun FolderCard(title: String, count: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color(0xFF333333), // Dark gray color
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(140.dp)
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
                    text = title,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start // Align text to the start

                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = count.toString(),
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
