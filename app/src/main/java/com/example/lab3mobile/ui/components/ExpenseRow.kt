package com.example.lab3mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab3mobile.model.Expense
import com.example.lab3mobile.ui.theme.Lab3MobileTheme
import java.util.Locale

/**
 * Reusable Composable: ExpenseRow.
 * Required by Part 3: Takes an Expense as a parameter.
 * Displays amount, category, date (D3) and remark (if available).
 */
@Composable
fun ExpenseRow(
    expense: Expense,
    modifier: Modifier = Modifier
) {
    // Dynamic color indicator based on category
    val categoryColor = when (expense.category.lowercase(Locale.ROOT)) {
        "food & drink", "food" -> Color(0xFF4CAF50)
        "transport" -> Color(0xFF2196F3)
        "shopping" -> Color(0xFF9C27B0)
        "bills & utilities", "bills" -> Color(0xFFFF9800)
        else -> Color(0xFF607D8B)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Category initial avatar badge
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(categoryColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = expense.category.take(1).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = categoryColor,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = expense.category,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    expense.remark?.let { remark ->
                        Text(
                            text = remark,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = expense.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Amount display
            Text(
                text = "-${expense.currency}${String.format(Locale.US, "%.2f", expense.amount)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

/**
 * Required @Preview for ExpenseRow (Part 3)
 */
@Preview(showBackground = true)
@Composable
fun ExpenseRowPreview() {
    Lab3MobileTheme {
        ExpenseRow(
            expense = Expense(
                id = "1",
                amount = 45.50,
                currency = "$",
                category = "Food & Drink",
                date = "2025-02-18",
                remark = "Dinner with friends"
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
