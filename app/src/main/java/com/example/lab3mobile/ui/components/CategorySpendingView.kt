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
import androidx.compose.ui.unit.dp
import java.util.Locale

/**
 * Reusable Composable 2: Spending by Category (D2).
 * Displays each category with its total amount.
 * Bigger spending looks bigger via proportional bar widths and scaling typography,
 * built using Column, Row, Box, and Modifiers as per lab requirements.
 */
@Composable
fun CategorySpendingView(
    categoryTotals: Map<String, Double>,
    modifier: Modifier = Modifier,
    currency: String = "$"
) {
    val maxSpend = categoryTotals.values.maxOrNull() ?: 1.0
    val totalSpend = categoryTotals.values.sum().coerceAtLeast(1.0)

    // Color palette for different categories
    val categoryColors = listOf(
        Color(0xFF673AB7), // Deep Purple
        Color(0xFF2196F3), // Blue
        Color(0xFF009688), // Teal
        Color(0xFFFF9800), // Orange
        Color(0xFFE91E63)  // Pink
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Spending by Category",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Sort categories by highest spend first
            val sortedCategories = categoryTotals.entries.sortedByDescending { it.value }

            sortedCategories.forEachIndexed { index, entry ->
                val categoryName = entry.key
                val categoryAmount = entry.value
                val fraction = (categoryAmount / maxSpend).toFloat().coerceIn(0.05f, 1.0f)
                val percentage = (categoryAmount / totalSpend * 100).toInt()
                val barColor = categoryColors[index % categoryColors.size]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = categoryName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "$currency${String.format(Locale.US, "%.2f", categoryAmount)} ($percentage%)",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Visual bar representation where bigger spending occupies more width
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .height(10.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(barColor)
                        )
                    }
                }
            }
        }
    }
}
