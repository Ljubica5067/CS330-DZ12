package rs.ac.metropolitan.cs330_dz12.ui.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import java.util.Date
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ComponentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun TransactionInputScreen() {
    var selectedCurrency by remember { mutableStateOf("€") }
    val currencies = listOf("€", "$")
    var selectedDate by remember { mutableStateOf(Date()) }
    var selectedCategory by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    val categories = listOf("Groceries", "Utilities", "Entertainment", "Transport", "Dining")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Currency Selection
        Text(text = "Select Currency", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
        currencies.forEach { currency ->
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedCurrency == currency,
                    onClick = { selectedCurrency = currency }
                )
                Text(text = currency, style = TextStyle(fontSize = 16.sp))
            }
        }

        // Date and Time Picker
        Text(text = "Select Date and Time", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { showDatePicker(LocalContext.current) { date -> selectedDate = date } }) {
                Text(text = "Pick Date")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { showTimePicker(LocalContext.current) { date -> selectedDate = date } }) {
                Text(text = "Pick Time")
            }
        }

        Text(
            text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(selectedDate),
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(8.dp)
        )

        // Category Selection
        Text(text = "Select Category", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
        categories.forEach { category ->
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category }
                )
                Text(text = category, style = TextStyle(fontSize = 16.sp))
            }
        }

        // Comment
        Text(text = "Comment", style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold))
        BasicTextField(
            value = comment,
            onValueChange = { comment = it },
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Submit Button
        Button(
            onClick = { /* Handle Submit */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Submit")
        }
    }
}

@Composable
fun showDatePicker(context: Context, onDateSelected: (Date) -> Unit) {
    val activity = LocalContext.current as ComponentActivity

    val datePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedDate = result.data?.getLongExtra("selected_date", 0) ?: return@rememberLauncherForActivityResult
            val calendar = Calendar.getInstance().apply { timeInMillis = selectedDate }
            onDateSelected(calendar.time)
        }
    }

    val builder = MaterialDatePicker.Builder.datePicker()
        .setTitleText("Select Date")

    val picker = builder.build()
    picker.addOnPositiveButtonClickListener {
        val selectedDate = picker.selection
        val intent = Intent().apply {
            putExtra("selected_date", selectedDate)
        }
        datePickerLauncher.launch(intent)
    }

    DisposableEffect(context) {
        onDispose {
            picker.dismiss()
        }
    }
}

@Composable
fun showTimePicker(context: Context, onTimeSelected: (Date) -> Unit) {
    val activity = LocalContext.current as ComponentActivity

    val timePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedTime = result.data?.getLongExtra("selected_time", 0) ?: return@rememberLauncherForActivityResult
            val calendar = Calendar.getInstance().apply { timeInMillis = selectedTime }
            onTimeSelected(calendar.time)
        }
    }

    val builder = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setHour(12)
        .setMinute(0)
        .setTitleText("Select Time")

    val picker = builder.build()
    picker.addOnPositiveButtonClickListener {
        val selectedTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, picker.hour)
            set(Calendar.MINUTE, picker.minute)
        }.timeInMillis
        val intent = Intent().apply {
            putExtra("selected_time", selectedTime)
        }
        timePickerLauncher.launch(intent)
    }

    DisposableEffect(context) {
        onDispose {
            picker.dismiss()
        }
    }
}