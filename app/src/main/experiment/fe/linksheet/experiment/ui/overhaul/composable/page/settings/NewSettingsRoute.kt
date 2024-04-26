package fe.linksheet.experiment.ui.overhaul.composable.page.settings

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fe.linksheet.*
import fe.linksheet.experiment.ui.overhaul.composable.ContentTypeDefaults
import fe.linksheet.experiment.ui.overhaul.composable.component.list.item.RouteNavItem
import fe.linksheet.experiment.ui.overhaul.composable.component.list.item.RouteNavigateListItem
import fe.linksheet.experiment.ui.overhaul.composable.component.page.SaneScaffoldSettingsPage
import fe.linksheet.experiment.ui.overhaul.composable.component.page.layout.group
import fe.linksheet.module.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

internal typealias Nav = RouteNavItem

internal object NewSettingsRouteData {
    val verifiedApps = Nav(
        appsWhichCanOpenLinksSettingsRoute,
        Icons.Outlined.DomainVerification,
        R.string.verified_link_handlers,
        R.string.verified_link_handlers_subtitle
    )

    val customization = arrayOf(
        Nav(browserSettingsRoute, Icons.Outlined.Apps, R.string.app_browsers, R.string.app_browsers_subtitle),
        Nav(
            bottomSheetSettingsRoute,
            Icons.Outlined.SwipeUp,
            R.string.bottom_sheet,
            R.string.bottom_sheet_explainer
        ),
        Nav(linksSettingsRoute, Icons.Outlined.Link, R.string.links, R.string.links_explainer)
    )

    val miscellaneous = arrayOf(
        Nav(generalSettingsRoute, Icons.Outlined.Settings, R.string.general, R.string.general_settings_explainer),
        Nav(
            notificationSettingsRoute,
            Icons.Outlined.Notifications,
            R.string.notifications,
            R.string.notifications_explainer
        ),
        Nav(themeSettingsRoute, Icons.Outlined.Palette, R.string.theme, R.string.theme_explainer),
        Nav(privacySettingsRoute, Icons.Outlined.PrivacyTip, R.string.privacy, R.string.privacy_settings_explainer)
    )

    val advanced = arrayOf(
        Nav(advancedSettingsRoute, Icons.Outlined.Terminal, R.string.advanced, R.string.advanced_explainer),
        Nav(debugSettingsRoute, Icons.Outlined.BugReport, R.string.debug, R.string.debug_explainer)
    )

    val dev = Nav(devModeRoute, Icons.Outlined.DeveloperMode, R.string.dev, R.string.dev_explainer)

    val about = arrayOf(
        Nav(Routes.Help, Icons.AutoMirrored.Outlined.HelpOutline, R.string.help, R.string.help_subtitle),
        Nav(
            Routes.Shortcuts,
            Icons.Outlined.SwitchAccessShortcut,
            R.string.settings__title_shortcuts,
            R.string.settings__subtitle_shortcuts
        ),
        Nav(
            Routes.Updates,
            Icons.Outlined.Update,
            R.string.settings__title_updates,
            R.string.settings__subtitle_updates
        ),
        Nav(aboutSettingsRoute, Icons.Outlined.Info, R.string.about, R.string.about_explainer)
    )
}

@Composable
fun NewSettingsRoute(
    onBackPressed: () -> Unit,
    navigate: (String) -> Unit,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val devMode = viewModel.devModeEnabled()

    SaneScaffoldSettingsPage(headline = stringResource(id = R.string.settings), onBackPressed = onBackPressed) {
        item(key = R.string.enable_libredirect, contentType = ContentTypeDefaults.SingleGroupItem) {
            RouteNavigateListItem(data = NewSettingsRouteData.verifiedApps, navigate = navigate)
        }

        divider(stringRes = R.string.customization)

        group(items = NewSettingsRouteData.customization) { data, padding, shape ->
            RouteNavigateListItem(data = data, padding = padding, shape = shape, navigate = navigate)
        }

        divider(stringRes = R.string.misc_settings)

        group(items = NewSettingsRouteData.miscellaneous) { data, padding, shape ->
            RouteNavigateListItem(data = data, padding = padding, shape = shape, navigate = navigate)
        }

        divider(stringRes = R.string.advanced)

        group(size = NewSettingsRouteData.advanced.size + if (devMode) 1 else 0) {
            items(values = NewSettingsRouteData.advanced) { data, padding, shape ->
                RouteNavigateListItem(data = data, padding = padding, shape = shape, navigate = navigate)
            }

            if (devMode) {
                item(key = NewSettingsRouteData.dev.route) { padding, shape ->
                    RouteNavigateListItem(
                        data = NewSettingsRouteData.dev,
                        padding = padding,
                        shape = shape,
                        navigate = navigate
                    )
                }
            }
        }

        divider(stringRes = R.string.about)

        group(items = NewSettingsRouteData.about) { data, padding, shape ->
            RouteNavigateListItem(data = data, padding = padding, shape = shape, navigate = navigate)
        }
    }
}
