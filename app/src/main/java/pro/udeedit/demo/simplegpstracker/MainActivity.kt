package pro.udeedit.demo.simplegpstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pro.udeedit.demo.simplegpstracker.ui.main.MainScreen
import pro.udeedit.demo.simplegpstracker.ui.theme.SimpleGpsTrackerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleGpsTrackerTheme {
                MainScreen()
            }
        }
    }
}


// PREVIEW

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleGpsTrackerTheme {
        MainScreen()
    }
}