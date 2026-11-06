package com.example.lab3mobile.model

import com.example.lab3mobile.R
import java.time.LocalDate
import java.util.Locale
import java.util.UUID

/**
 * Data class representing an individual expense entry.
 * Required fields from Lab 4 specification:
 * - id: String (UUID.randomUUID().toString())
 * - amount: Double
 * - currency: String ("KHR" or "USD")
 * - category: String
 * - date: LocalDate
 * - remark: String? = null
 */
data class Expense(
    val id: String = UUID.randomUUID().toString(),
    val amount: Double,
    val currency: String = "USD",
    val category: String,
    val date: LocalDate,
    val remark: String? = null
)

/**
 * Initial sample expenses preserved from Lab 3, updated to use LocalDate.
 */
val sampleExpenses = listOf(
    Expense(
        id = UUID.randomUUID().toString(),
        amount = 120.50,
        currency = "USD",
        category = "Shopping",
        date = LocalDate.now().minusDays(1),
        remark = "New sneakers & socks"
    ),
    Expense(
        id = UUID.randomUUID().toString(),
        amount = 45.00,
        currency = "USD",
        category = "Food & Drink",
        date = LocalDate.now().minusDays(1),
        remark = "Dinner with friends"
    ),
    Expense(
        id = UUID.randomUUID().toString(),
        amount = 15.20,
        currency = "USD",
        category = "Transport",
        date = LocalDate.now().minusDays(2),
        remark = "Grab ride to campus"
    ),
    Expense(
        id = UUID.randomUUID().toString(),
        amount = 85.00,
        currency = "USD",
        category = "Bills & Utilities",
        date = LocalDate.now().minusDays(3),
        remark = "Monthly Wi-Fi bill"
    ),
    Expense(
        id = UUID.randomUUID().toString(),
        amount = 32.80,
        currency = "USD",
        category = "Food & Drink",
        date = LocalDate.now().minusDays(4),
        remark = "Groceries for the week"
    ),
    Expense(
        id = UUID.randomUUID().toString(),
        amount = 220.00,
        currency = "USD",
        category = "Shopping",
        date = LocalDate.now().minusDays(5),
        remark = "Monitor desk arm"
    ),
    Expense(
        id = UUID.randomUUID().toString(),
        amount = 18.00,
        currency = "USD",
        category = "Entertainment",
        date = LocalDate.now().minusDays(7),
        remark = "Cinema movie ticket"
    ),
    Expense(
        id = UUID.randomUUID().toString(),
        amount = 9.50,
        currency = "USD",
        category = "Transport",
        date = LocalDate.now().minusDays(9),
        remark = "Bus fare"
    )
)

/**
 * Rotating candidates for the Add button (D5) - each press adds one sample expense dated today.
 */
val candidateNewExpenses = listOf(
    Triple("Food & Drink", 6.50, "Iced latte & bakery snack"),
    Triple("Transport", 4.25, "TukTuk ride to cafe"),
    Triple("Shopping", 28.00, "Books and stationery"),
    Triple("Entertainment", 12.00, "Online streaming subscription"),
    Triple("Food & Drink", 16.75, "Lunch buffet with team"),
    Triple("Bills & Utilities", 25.00, "Mobile phone top-up")
)

/**
 * Maps category name strings to localized string resource IDs.
 */
fun getCategoryStringRes(category: String): Int {
    return when (category.lowercase(Locale.ROOT)) {
        "shopping" -> R.string.category_shopping
        "food & drink", "food" -> R.string.category_food_drink
        "transport" -> R.string.category_transport
        "bills & utilities", "bills" -> R.string.category_bills
        "entertainment" -> R.string.category_entertainment
        else -> R.string.category_shopping
    }
}

/**
 * Maps sample remarks to localized string resource IDs if known, or returns null.
 */
fun getRemarkStringRes(remark: String?): Int? {
    if (remark == null) return null
    return when (remark) {
        "Dinner with friends" -> R.string.sample_remark_dinner
        "Iced latte & bakery snack" -> R.string.sample_remark_coffee
        "Grab ride to campus" -> R.string.sample_remark_grab
        "New sneakers & socks" -> R.string.sample_remark_shopping
        "Monthly Wi-Fi bill" -> R.string.sample_remark_wifi
        "Groceries for the week" -> R.string.sample_remark_groceries
        "Monitor desk arm" -> R.string.sample_remark_monitor
        "Cinema movie ticket" -> R.string.sample_remark_movie
        "Bus fare" -> R.string.sample_remark_bus
        "TukTuk ride to cafe" -> R.string.sample_remark_tuktuk
        "Books and stationery" -> R.string.sample_remark_books
        "Online streaming subscription" -> R.string.sample_remark_streaming
        "Lunch buffet with team" -> R.string.sample_remark_lunch
        "Mobile phone top-up" -> R.string.sample_remark_phone
        else -> null
    }
}
