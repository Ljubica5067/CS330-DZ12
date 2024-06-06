package rs.ac.metropolitan.cs330_dz12.ui.view

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Transaction
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import rs.ac.metropolitan.cs330_dz12.data.TransactionItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun TransactionDetailScreen(
    transaction: TransactionItem,
    onDeleteTransaction: (TransactionItem) -> Unit,
    onEditTransaction: (TransactionItem) -> Unit
) {
    var editedTransaction by remember { mutableStateOf(transaction) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Display Transaction Details
        Text(text = "Transaction Details", style = MaterialTheme.typography.h6)

        Text(text = "Currency: ${transaction.currency}", style = MaterialTheme.typography.body1)
        Text(
            text = "Date: ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(transaction.date)}",
            style = MaterialTheme.typography.body1
        )
        Text(text = "Category: ${transaction.category}", style = MaterialTheme.typography.body1)
        Text(text = "Amount: ${transaction.amount}", style = MaterialTheme.typography.body1)
        Text(text = "Comment: ${transaction.comment}", style = MaterialTheme.typography.body1)

        // Edit Button
        Button(
            onClick = { onEditTransaction(transaction) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Edit Transaction")
        }

        // Delete Button
        Button(
            onClick = { onDeleteTransaction(transaction) },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Delete Transaction")
        }
    }
}

@Composable
fun EditTransactionScreen(
    transaction: TransactionItem,
    onSaveTransaction: (TransactionItem) -> Unit
) {
    var currency by remember { mutableStateOf(transaction.currency) }
    var date by remember { mutableStateOf(transaction.date) }
    var category by remember { mutableStateOf(transaction.category) }
    var amount by remember { mutableStateOf(transaction.amount.toString()) }
    var comment by remember { mutableStateOf(transaction.comment) }

    val categories = listOf("Groceries", "Utilities", "Entertainment", "Transport", "Dining")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Currency Selection
        Text(text = "Select Currency", style = MaterialTheme.typography.body1)
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("€", "$").forEach { curr ->
                RadioButton(
                    selected = currency == curr,
                    onClick = { currency = curr }
                )
                Text(text = curr, style = MaterialTheme.typography.body1)
            }
        }

        // Date and Time Picker
        Text(text = "Select Date and Time", style = MaterialTheme.typography.body1)
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { showDatePicker(context) { date -> selectedDate = date } }) {
                Text(text = "Pick Date")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { showTimePicker(context) { date -> selectedDate = date } }) {
                Text(text = "Pick Time")
            }
        }
        Text(
            text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(date),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
        )

        // Category Selection
        Text(text = "Select Category", style = MaterialTheme.typography.body1)
        categories.forEach { cat ->
            Row(modifier = Modifier.fillMaxWidth()) {
                RadioButton(
                    selected = category == cat,
                    onClick = { category = cat }
                )
                Text(text = cat, style = MaterialTheme.typography.body1)
            }
        }

        // Amount Input
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(text = "Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        // Comment
        TextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text(text = "Comment") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save Button
        Button(
            onClick = {
                onSaveTransaction(
                    transaction.copy(
                        currency = currency,
                        date = date,
                        category = category,
                        amount = amount.toDouble(),
                        comment = comment
                    )
                )
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Save")
        }
    }
}


fun showDatePicker(context: Context, onDateSelected: (Date) -> Unit) {
    val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select Date")
        .build()

    datePicker.addOnPositiveButtonClickListener { selection ->
        val calendar = Calendar.getInstance().apply { timeInMillis = selection }
        onDateSelected(calendar.time)
    }

    datePicker.show((context as ComponentActivity).supportFragmentManager, "DATE_PICKER")
}

fun showTimePicker(context: Context, onTimeSelected: (Date) -> Unit) {
    val timePicker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setHour(12)
        .setMinute(0)
        .setTitleText("Select Time")
        .build()

    timePicker.addOnPositiveButtonClickListener {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
        calendar.set(Calendar.MINUTE, timePicker.minute)
        onTimeSelected(calendar.time)
    }

    timePicker.show((context as ComponentActivity).supportFragmentManager, "TIME_PICKER")
}