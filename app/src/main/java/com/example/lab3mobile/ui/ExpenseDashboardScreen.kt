package com.example.lab3mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab3mobile.model.Expense
import com.example.lab3mobile.model.sampleExpenses
import com.example.lab3mobile.ui.components.CategorySpendingView
import com.example.lab3mobile.ui.components.DashboardReviewCard
import com.example.lab3mobile.ui.components.ExpenseRow
import com.example.lab3mobile.ui.components.RecentExpensesHeader
import com.example.lab3mobile.ui.components.TotalSummaryCard
import com.example.lab3mobile.ui.theme.Lab3MobileTheme

/**
 * Main Expense Dashboard Screen.
 * Contains Scaffold with TopAppBar, Total Summary (D1), Dashboard Review card,
 * Spending by Category (D2), and Recent Expenses LazyColumn (D3).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDashboardScreen(
    expenses: List<Expense> = sampleExpenses
) {
    // Dynamically calculate total amount from expense list (D1 requirement)
    val totalAmount = expenses.sumOf { it.amount }

    // Dynamically calculate category totals for spending by category (D2 requirement)
    val categoryTotals = expenses.groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "lab_3_Nhim_Dara",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: This Month's Total Card (D1)
            item {
                TotalSummaryCard(
                    totalAmount = totalAmount,
                    expenseCount = expenses.size
                )
            }

            // Section 2: Dashboard Review Card
            item {
                DashboardReviewCard(
                    expenses = expenses
                )
            }

            // Section 3: Spending by Category (D2)
            item {
                CategorySpendingView(
                    categoryTotals = categoryTotals
                )
            }

            // Section 4: Recent Expenses Header (D3)
            item {
                RecentExpensesHeader()
            }

            // Section 4: Recent Expenses Scrollable LazyColumn Items (D3)
            items(
                items = expenses,
                key = { it.id }
            ) { expense ->
                ExpenseRow(expense = expense)
            }
        }
    }
}

/**
 * Required @Preview for the whole dashboard (Part 3)
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExpenseDashboardPreview() {
    Lab3MobileTheme {
        ExpenseDashboardScreen()
    }
}
