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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.lab3mobile.R
import com.example.lab3mobile.model.getCategoryStringRes
import java.util.Locale

/**
 * Required component: Spending by Category.
 * Shows each category with its amount, dynamically worked out from the list.
 * Bigger spendings have wider bars.
 * Strictly uses Theme color roles, Typography scale, Dimens, and String resources.
 */
@Composable
fun CategorySpendingView(
    categoryTotals: Map<String, Double>,
    modifier: Modifier = Modifier
) {
    val maxSpend = categoryTotals.values.maxOrNull() ?: 1.0
    val totalSpend = categoryTotals.values.sum().coerceAtLeast(1.0)

    // Bar colors strictly derived from the Material 3 Theme color roles
    val barColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_medium)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.spacing_normal))
        ) {
            Text(
                text = stringResource(id = R.string.spending_by_category),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))

            // Sort categories by highest spend first
            val sortedCategories = categoryTotals.entries.sortedByDescending { it.value }

            sortedCategories.forEachIndexed { index, entry ->
                val categoryName = entry.key
                val categoryAmount = entry.value
                val fraction = (categoryAmount / maxSpend).toFloat().coerceIn(0.06f, 1.0f)
                val percentage = (categoryAmount / totalSpend * 100).toInt()
                val barColor = barColors[index % barColors.size]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dimensionResource(id = R.dimen.spacing_xs))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = getCategoryStringRes(categoryName)),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${stringResource(id = R.string.currency_symbol_usd)}${String.format(Locale.US, "%.2f", categoryAmount)} ($percentage%)",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xs)))

                    // Visual bar representation where bigger spending occupies more width
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(dimensionResource(id = R.dimen.bar_height))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.bar_corner)))
                            .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(fraction)
                                .height(dimensionResource(id = R.dimen.bar_height))
                                .clip(RoundedCornerShape(dimensionResource(id = R.dimen.bar_corner)))
                            .background(barColor)
                        )
                    }
                }
            }
        }
    }
}
