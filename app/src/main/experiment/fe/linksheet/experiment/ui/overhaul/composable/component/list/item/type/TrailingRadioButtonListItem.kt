package fe.linksheet.experiment.ui.overhaul.composable.component.list.item.type

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import fe.linksheet.experiment.ui.overhaul.composable.component.list.item.ClickableShapeListItem
import fe.linksheet.experiment.ui.overhaul.composable.component.list.item.OptionalContent
import fe.linksheet.experiment.ui.overhaul.composable.component.list.item.ShapeListItemDefaults


@Composable
fun TrailingRadioButtonListItem(
    enabled: Boolean = true,
    selected: Boolean,
    onClick: () -> Unit,
    shape: Shape = ShapeListItemDefaults.SingleShape,
    padding: PaddingValues = PaddingValues(),
    headlineContentText: String,
    supportingContentText: String? = null,
) {
    RadioButtonListItem(
        enabled = enabled,
        selected = selected,
        onClick = onClick,
        location = RadioLocation.Trailing,
        shape = shape,
        padding = padding,
        headlineContentText = headlineContentText,
        supportingContentText = supportingContentText
    )
}

@Composable
fun LeadingRadioButtonListItem(
    modifier: Modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
    enabled: Boolean = true,
    selected: Boolean,
    onClick: () -> Unit,
    shape: Shape = ShapeListItemDefaults.SingleShape,
    padding: PaddingValues = PaddingValues(),
    headlineContentText: String,
    supportingContentText: String? = null,
) {
    RadioButtonListItem(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        onClick = onClick,
        location = RadioLocation.Leading,
        shape = shape,
        padding = padding,
        headlineContentText = headlineContentText,
        supportingContentText = supportingContentText
    )
}

enum class RadioLocation {
    Leading, Trailing
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioButtonListItem(
    modifier: Modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min),
    enabled: Boolean = true,
    selected: Boolean,
    onClick: () -> Unit,
    location: RadioLocation,
    shape: Shape = ShapeListItemDefaults.SingleShape,
    padding: PaddingValues = PaddingValues(),
    headlineContentText: String,
    supportingContentText: String? = null,
    otherContent: (@Composable () -> Unit)? = null
) {
    val radioButton: OptionalContent = remember(enabled, selected, onClick) {
        {
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 36.dp) {
                RadioButton(
                    modifier = Modifier.fillMaxHeight().width(36.dp),
                    enabled = enabled,
                    selected = selected,
                    onClick = onClick,
                )
            }
        }
    }

    ClickableShapeListItem(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        role = Role.RadioButton,
        shape = shape,
        padding = padding,
        headlineContent = {
            Text(text = headlineContentText)
        },
        supportingContent = supportingContentText?.let { { Text(text = it) } },
        leadingContent = if (location == RadioLocation.Leading) radioButton else otherContent,
        trailingContent = if (location == RadioLocation.Trailing) radioButton else otherContent
    )
}
