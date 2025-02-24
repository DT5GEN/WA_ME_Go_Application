package com.dt5gen.wamegoapplication.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.dt5gen.wamegoapplication.presentation.ClipboardViewModel
import com.dt5gen.wamegoapplication.presentation.viewmodel.HistoryViewModel
import java.util.Date

@Composable
fun MainScreen(viewModel: ClipboardViewModel, historyViewModel: HistoryViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val phoneNumber = viewModel.phoneNumber.collectAsState().value
    val history = historyViewModel.history.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { newValue ->
                    if (newValue.length <= 16 && newValue.all { it.isDigit() || it == '+' }) {
                        viewModel.updatePhoneNumber(newValue)
                    }
                },
                label = { Text("Введите номер") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(history) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/${item.phoneNumber}"))
                                context.startActivity(intent)
                                historyViewModel.addNumber(item.phoneNumber) // ✅ Поднимаем номер в историю
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = item.phoneNumber, style = MaterialTheme.typography.bodyLarge)
                            Text(text = "Последний переход: ${Date(item.lastUsed)}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.getWhatsAppUrl()))
                        context.startActivity(intent)
                        historyViewModel.addNumber(phoneNumber)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Перейти в WhatsApp")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.checkClipboardForPhoneNumber() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Обновить номер")
                }
            }
        }
    }
}






/////LaunchedEffect(lifecycleOwner) {
//    lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
//        viewModel.checkClipboardForPhoneNumber()
//    }
//}

//