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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.lab3mobile.model.Expense
import com.example.lab3mobile.model.getCategoryStringRes
import com.example.lab3mobile.model.getRemarkStringRes
import java.util.Locale

/**
 * Required component: ExpenseRow.
 * Displays amount, category, date, remark, and a Delete button.
 * All styling strictly adheres to theme color roles and typography scale.
 */
@Composable
fun ExpenseRow(
    expense: Expense,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Theme-derived badge colors based on category
    val badgeContainerColor = when (expense.category.lowercase(Locale.ROOT)) {
        "shopping" -> MaterialTheme.colorScheme.primaryContainer
        "food & drink", "food" -> MaterialTheme.colorScheme.secondaryContainer
        "transport" -> MaterialTheme.colorScheme.tertiaryContainer
        "bills & utilities", "bills" -> MaterialTheme.colorScheme.surfaceVariant
        else -> MaterialTheme.colorScheme.errorContainer
    }

    val badgeContentColor = when (expense.category.lowercase(Locale.ROOT)) {
        "shopping" -> MaterialTheme.colorScheme.onPrimaryContainer
        "food & drink", "food" -> MaterialTheme.colorScheme.onSecondaryContainer
        "transport" -> MaterialTheme.colorScheme.onTertiaryContainer
        "bills & utilities", "bills" -> MaterialTheme.colorScheme.onSurfaceVariant
        else -> MaterialTheme.colorScheme.onErrorContainer
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.card_corner_small)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.spacing_normal),
                    vertical = dimensionResource(id = R.dimen.spacing_medium)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Category Icon & Details
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Category initial avatar badge
                Box(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.avatar_size))
                        .clip(CircleShape)
                        .background(badgeContainerColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = expense.category.take(1).uppercase(Locale.ROOT),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = badgeContentColor
                    )
                }

                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_medium)))

                Column {
                    Text(
                        text = stringResource(id = getCategoryStringRes(expense.category)),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    expense.remark?.let { remarkText ->
                        val localizedRemarkRes = getRemarkStringRes(remarkText)
                        val displayedRemark = if (localizedRemarkRes != null) {
                            stringResource(id = localizedRemarkRes)
                        } else {
                            remarkText
                        }
                        Text(
                            text = displayedRemark,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xxs)))

                    Text(
                        text = expense.date.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_small)))

            // Amount and Delete button
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "-${stringResource(id = R.string.currency_symbol_usd)}${String.format(Locale.US, "%.2f", expense.amount)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_xs)))

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(
                            id = R.string.delete_content_description,
                            stringResource(id = getCategoryStringRes(expense.category))
                        ),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
