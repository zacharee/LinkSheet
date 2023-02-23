package fe.linksheet.composable.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fe.linksheet.R
import fe.linksheet.appsWhichCanOpenLinks
import fe.linksheet.extension.observeAsState
import fe.linksheet.preferredSettingsRoute
import fe.linksheet.ui.theme.HkGroteskFontFamily

@Composable
fun SettingsRoute(navController: NavController, viewModel: SettingsViewModel = viewModel()) {
    val context = LocalContext.current
    var enabled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        enabled = !viewModel.checkDefaultBrowser(context)
    }

    val lifecycleState = LocalLifecycleOwner.current.lifecycle.observeAsState()
    val state = lifecycleState.value

    if (state == Lifecycle.Event.ON_RESUME) {
        enabled = !viewModel.checkDefaultBrowser(context)
    }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp)) {
        ClickableRow(onClick = { viewModel.openDefaultBrowserSettings(context) }, enabled = enabled) {
            Column {
                Text(
                    text = stringResource(id = R.string.set_as_browser),
                    fontFamily = HkGroteskFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.set_as_browser_explainer),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        ClickableRow(onClick = { navController.navigate(preferredSettingsRoute) }) {
            Column {
                Text(
                    text = stringResource(id = R.string.preferred_apps),
                    fontFamily = HkGroteskFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.preferred_apps_settings),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        ClickableRow(onClick = { navController.navigate(appsWhichCanOpenLinks) }) {
            Column {
                Text(
                    text = stringResource(id = R.string.apps_which_can_open_links),
                    fontFamily = HkGroteskFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.apps_which_can_open_links_explainer_2),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun ClickableRow(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(10.dp).let {
                if (!enabled) it.alpha(0.3f) else it
            }
    ) {
        content()
    }
}

