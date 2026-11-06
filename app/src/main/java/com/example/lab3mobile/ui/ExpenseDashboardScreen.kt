package com.example.lab3mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.lab3mobile.R
import com.example.lab3mobile.model.Expense
import com.example.lab3mobile.ui.components.CategorySpendingView
import com.example.lab3mobile.ui.components.EmptyExpensesCard
import com.example.lab3mobile.ui.components.ExpenseRow
import com.example.lab3mobile.ui.components.RecentExpensesHeader
import com.example.lab3mobile.ui.components.TotalSummaryCard

/**
 * Main Expense Dashboard Screen (Stateless).
 * Takes the list and event callbacks as parameters.
 * - This month's total worked out from the list with a plain val
 * - Spending by category worked out from the list with a plain val
 * - Empty message worked out with derivedStateOf
 * - FAB for adding expenses (D5)
 * - Undo handled via SnackbarHost
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDashboardScreen(
    expenses: List<Expense>,
    onAddExpense: () -> Unit,
    onDeleteExpense: (Expense) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    // Total worked out directly from list with a plain val (no second state)
    val totalAmount = expenses.sumOf { it.amount }

    // Category totals worked out directly from list with a plain val (no second state)
    val categoryTotals = expenses.groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }

    // Derived state for empty check because it changes much less often than the list
    val isEmpty by remember(expenses) {
        derivedStateOf { expenses.isEmpty() }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_bar_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            // D5: Add-expense entry (Floating Action Button)
            FloatingActionButton(
                onClick = onAddExpense,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_expense)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.spacing_normal)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_normal))
        ) {
            // Section 1: This Month's Total Card
            item {
                TotalSummaryCard(
                    totalAmount = totalAmount,
                    expenseCount = expenses.size
                )
            }

            // Section 2: Spending by Category (only when expenses exist)
            if (categoryTotals.isNotEmpty()) {
                item {
                    CategorySpendingView(
                        categoryTotals = categoryTotals
                    )
                }
            }

            // Section 3: Recent Expenses Header
            item {
                RecentExpensesHeader()
            }

            // Section 4: If empty, replace recent expenses list with empty message
            if (isEmpty) {
                item {
                    EmptyExpensesCard()
                }
            } else {
                items(
                    items = expenses,
                    key = { it.id }
                ) { expense ->
                    ExpenseRow(
                        expense = expense,
                        onDelete = { onDeleteExpense(expense) }
                    )
                }
            }
        }
    }
}
