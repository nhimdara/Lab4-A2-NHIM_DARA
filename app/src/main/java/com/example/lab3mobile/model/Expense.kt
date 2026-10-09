package com.example.lab3mobile.model

/**
 * Data class representing an individual expense entry.
 * Required fields from Lab 3 specification:
 * - id: Unique identifier
 * - amount: Monetary value
 * - currency: Currency symbol/code (e.g., "$", "USD")
 * - category: Category name (e.g., "Food", "Transport", "Shopping")
 * - date: Formatted date string
 * - remark: Optional/nullable note or comment
 */
data class Expense(
    val id: String,
    val amount: Double,
    val currency: String = "$",
    val category: String,
    val date: String,
    val remark: String? = null
)

/**
 * Sample dataset containing expenses across various categories.
 */
val sampleExpenses = listOf(
    Expense(
        id = "1",
        amount = 120.50,
        currency = "$",
        category = "Shopping",
        date = "2025-02-18",
        remark = "New sneakers & socks"
    ),
    Expense(
        id = "2",
        amount = 45.00,
        currency = "$",
        category = "Food & Drink",
        date = "2025-02-18",
        remark = "Dinner with friends"
    ),
    Expense(
        id = "3",
        amount = 15.20,
        currency = "$",
        category = "Transport",
        date = "2025-02-17",
        remark = "Grab ride to campus"
    ),
    Expense(
        id = "4",
        amount = 85.00,
        currency = "$",
        category = "Bills & Utilities",
        date = "2025-02-16",
        remark = "Monthly Wi-Fi bill"
    ),
    Expense(
        id = "5",
        amount = 32.80,
        currency = "$",
        category = "Food & Drink",
        date = "2025-02-15",
        remark = "Groceries for the week"
    ),
    Expense(
        id = "6",
        amount = 220.00,
        currency = "$",
        category = "Shopping",
        date = "2025-02-14",
        remark = "Monitor desk arm"
    ),
    Expense(
        id = "7",
        amount = 18.00,
        currency = "$",
        category = "Entertainment",
        date = "2025-02-12",
        remark = "Cinema movie ticket"
    ),
    Expense(
        id = "8",
        amount = 9.50,
        currency = "$",
        category = "Transport",
        date = "2025-02-10"
    )
)
