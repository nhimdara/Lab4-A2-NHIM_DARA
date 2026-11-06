package com.example.lab3mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab3mobile.model.Expense
import com.example.lab3mobile.model.candidateNewExpenses
import com.example.lab3mobile.model.getCategoryStringRes
import com.example.lab3mobile.model.sampleExpenses
import com.example.lab3mobile.ui.ExpenseDashboardScreen
import com.example.lab3mobile.ui.theme.Lab4Theme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Locale
import java.util.UUID

class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "Lab4_Nhim_Dara"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity started")
        enableEdgeToEdge()
        setContent {
            Lab4Theme {
                ExpenseApp()
            }
        }
    }
}

/**
 * Stateful container: holds the mutable list state, coroutine scope,
 * and delegates UI rendering to the stateless ExpenseDashboardScreen.
 */
@Composable
fun ExpenseApp() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // State kept in the caller using mutableStateListOf
    val expenses = remember {
        mutableStateListOf<Expense>().apply {
            addAll(sampleExpenses)
        }
    }

    var candidateIndex by remember { mutableIntStateOf(0) }

    // Add button handler (D5): adds one sample expense dated today
    val onAddExpense: () -> Unit = {
        val (category, amount, remark) = candidateNewExpenses[candidateIndex % candidateNewExpenses.size]
        candidateIndex++

        val newExpense = Expense(
            id = UUID.randomUUID().toString(),
            amount = amount,
            currency = "USD",
            category = category,
            date = LocalDate.now(), // Dated today as required
            remark = remark
        )

        expenses.add(0, newExpense)
        Log.d(
            MainActivity.TAG,
            "Expense added: ${newExpense.category} ($${newExpense.amount}) on ${newExpense.date}"
        )
    }

    // Delete expense handler: removes item and shows Snackbar with Undo option
    val onDeleteExpense: (Expense) -> Unit = { expense ->
        val index = expenses.indexOf(expense)
        if (index != -1) {
            expenses.removeAt(index)
            Log.d(
                MainActivity.TAG,
                "Expense deleted: ${expense.category} ($${expense.amount})"
            )

            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                val categoryName = context.getString(getCategoryStringRes(expense.category))
                val amountFormatted = String.format(Locale.US, "%.2f", expense.amount)
                val message = context.getString(
                    R.string.expense_deleted_message,
                    categoryName,
                    amountFormatted
                )
                val actionLabel = context.getString(R.string.undo)

                val result = snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Short
                )

                if (result == SnackbarResult.ActionPerformed) {
                    val restoreIndex = index.coerceIn(0, expenses.size)
                    expenses.add(restoreIndex, expense)
                    Log.d(
                        MainActivity.TAG,
                        "Expense restored (undo): ${expense.category} ($${expense.amount})"
                    )
                }
            }
        }
    }

    ExpenseDashboardScreen(
        expenses = expenses,
        onAddExpense = onAddExpense,
        onDeleteExpense = onDeleteExpense,
        snackbarHostState = snackbarHostState
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExpenseDashboardPreview() {
    Lab4Theme {
        ExpenseDashboardScreen(
            expenses = sampleExpenses,
            onAddExpense = {},
            onDeleteExpense = {},
            snackbarHostState = remember { SnackbarHostState() }
        )
    }
}
