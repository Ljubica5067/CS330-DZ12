package rs.ac.metropolitan.cs330_dz12

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import rs.ac.metropolitan.cs330_dz12.data.TransactionItem
import rs.ac.metropolitan.cs330_dz12.ui.theme.CS330DZ12Theme
import rs.ac.metropolitan.cs330_dz12.ui.view.TransactionListScreen
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CS330DZ12Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val sampleTransaction = TransactionItem(
                        id = "1",
                        currency = "€",
                        date = Date(),
                        category = "Groceries",
                        amount = 50.0,
                        comment = "Weekly groceries shopping"
                    )
                    TransactionListScreen(transactions = sampleTransaction)
                }
            }
        }
    }
}