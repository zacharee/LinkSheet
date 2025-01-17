package fe.linksheet.composable.settings.browser.mode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fe.linksheet.R
import fe.linksheet.composable.settings.browser.BrowserCommonPackageSelectorData
import fe.linksheet.composable.settings.browser.BrowserCommonRadioButtonRowData
import fe.linksheet.composable.settings.browser.BrowserCommonScaffold
import fe.linksheet.composable.settings.browser.BrowserIconTextRow
import fe.linksheet.composable.util.FilterChipValue
import fe.linksheet.composable.util.FilterChips
import fe.linksheet.composable.util.RadioButtonRow
import fe.linksheet.composable.util.SettingEnabledCardColumn
import fe.linksheet.extension.compose.currentActivity
import fe.linksheet.extension.ioState
import fe.linksheet.extension.android.startPackageInfoActivity
import fe.linksheet.module.resolver.BrowserHandler
import fe.linksheet.module.viewmodel.PreferredBrowserViewModel
import fe.linksheet.whitelistedBrowsersSettingsRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreferredBrowserSettingsRoute(
    navController: NavHostController,
    onBackPressed: () -> Unit,
    viewModel: PreferredBrowserViewModel = koinViewModel(),
) {
    val activity = LocalContext.currentActivity()

    val browsers by viewModel.browsers.ioState()
    val type by viewModel.type.ioState()
    val browserMode by viewModel.browserModeState.ioState()
    val selectedBrowser by viewModel.selectedBrowserState.ioState()

    val rows = remember {
        listOf(
            BrowserCommonRadioButtonRowData(
                R.string.always_ask,
                R.string.always_ask_explainer
            ),
            BrowserCommonRadioButtonRowData(
                R.string.none,
                R.string.none_explainer
            )
        )
    }

    BrowserCommonScaffold(
        navController = navController,
        headline = R.string.preferred_browser,
        explainer = R.string.preferred_browser_explainer,
        onBackPressed = onBackPressed,
        viewModel = viewModel,
        values = listOf(
            BrowserHandler.BrowserMode.AlwaysAsk,
            BrowserHandler.BrowserMode.None,
        ),
        state = browserMode,
        rowKey = { it.value },
        rows = rows,
        header = {
            SettingEnabledCardColumn(
                checked = viewModel.unifiedPreferredBrowser.value,
                onChange = { viewModel.updateState(viewModel.unifiedPreferredBrowser, it) },
                headline = stringResource(id = R.string.use_unified_preferred_browser),
                subtitle = stringResource(id = R.string.use_unified_preferred_browser_explainer)
            )
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                if (!viewModel.unifiedPreferredBrowser.value) {
                    FilterChips(
                        currentState = type,
                        onClick = { viewModel.type.value = it },
                        values = listOf(
                            FilterChipValue(
                                PreferredBrowserViewModel.BrowserType.Normal,
                                R.string.normal
                            ),
                            FilterChipValue(
                                PreferredBrowserViewModel.BrowserType.InApp,
                                R.string.in_app
                            )
                        )
                    )
                }
            }
        },
        browsers = browsers,
        selectorData = BrowserCommonPackageSelectorData(
            R.string.whitelisted,
            R.string.whitelisted_explainer,
            BrowserHandler.BrowserMode.Whitelisted,
            whitelistedBrowsersSettingsRoute
        )
    ) { browserState ->
        if (browserState == null || browserMode == null || selectedBrowser == null) {
            item(key = "loader") {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            items(
                items = browserState,
                key = { it.flatComponentName },
                contentType = { it.flatComponentName }
            ) { app ->
                val selected = browserMode!!.matches(BrowserHandler.BrowserMode.SelectedBrowser)
                        && selectedBrowser!!.matches(app.packageName)

                RadioButtonRow(
                    selected = selected,
                    onClick = { viewModel.updateSelectedBrowser(app.packageName) },
                    onLongClick = { activity.startPackageInfoActivity(app) }
                ) {
                    BrowserIconTextRow(
                        app = app,
                        selected = selected,
                        showSelectedText = true,
                        alwaysShowPackageName = viewModel.alwaysShowPackageName.value
                    )
                }
            }
        }
    }
}