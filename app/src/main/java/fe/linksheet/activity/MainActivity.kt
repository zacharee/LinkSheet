package fe.linksheet.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import fe.linksheet.BuildConfig
import fe.linksheet.aboutSettingsRoute
import fe.linksheet.activity.onboarding.OnboardingActivity
import fe.linksheet.advancedSettingsRoute
import fe.linksheet.amp2HtmlSettingsRoute
import fe.linksheet.appsSettingsRoute
import fe.linksheet.appsWhichCanOpenLinksSettingsRoute
import fe.linksheet.bottomSheetSettingsRoute
import fe.linksheet.browserSettingsRoute
import fe.linksheet.composable.main.MainRoute
import fe.linksheet.composable.settings.SettingsRoute
import fe.linksheet.composable.settings.about.AboutSettingsRoute
import fe.linksheet.composable.settings.about.CreditsSettingsRoute
import fe.linksheet.composable.settings.advanced.AdvancedSettingsRoute
import fe.linksheet.composable.settings.advanced.FeatureFlagSettingsRoute
import fe.linksheet.composable.settings.advanced.ShizukuSettingsRoute
import fe.linksheet.composable.settings.apps.AppsSettingsRoute
import fe.linksheet.composable.settings.apps.PretendToBeAppSettingsRoute
import fe.linksheet.composable.settings.apps.link.AppsWhichCanOpenLinksSettingsRoute
import fe.linksheet.composable.settings.apps.preferred.PreferredAppSettingsRoute
import fe.linksheet.composable.settings.bottomsheet.BottomSheetSettingsRoute
import fe.linksheet.composable.settings.browser.BrowserSettingsRoute
import fe.linksheet.composable.settings.browser.inapp.InAppBrowserSettingsDisableInSelectedRoute
import fe.linksheet.composable.settings.browser.inapp.InAppBrowserSettingsRoute
import fe.linksheet.composable.settings.browser.mode.PreferredBrowserSettingsRoute
import fe.linksheet.composable.settings.browser.mode.WhitelistedBrowsersSettingsRoute
import fe.linksheet.composable.settings.debug.DebugSettingsRoute
import fe.linksheet.composable.settings.debug.loadpreferences.LoadDumpedPreferences
import fe.linksheet.composable.settings.debug.log.LogSettingsRoute
import fe.linksheet.composable.settings.debug.log.LogTextSettingsRoute
import fe.linksheet.composable.settings.general.GeneralSettingsRoute
import fe.linksheet.composable.settings.link.LinksSettingsRoute
import fe.linksheet.composable.settings.link.amp2html.Amp2HtmlSettingsRoute
import fe.linksheet.composable.settings.link.downloader.DownloaderSettingsRoute
import fe.linksheet.composable.settings.link.libredirect.LibRedirectServiceSettingsRoute
import fe.linksheet.composable.settings.link.libredirect.LibRedirectSettingsRoute
import fe.linksheet.composable.settings.link.redirect.FollowRedirectsSettingsRoute
import fe.linksheet.composable.settings.privacy.PrivacySettingsRoute
import fe.linksheet.composable.settings.theme.ThemeSettingsRoute
import fe.linksheet.composable.util.animatedArgumentRouteComposable
import fe.linksheet.composable.util.animatedComposable
import fe.linksheet.creditsSettingsRoute
import fe.linksheet.debugSettingsRoute
import fe.linksheet.downloaderSettingsRoute
import fe.linksheet.featureFlagSettingsRoute
import fe.linksheet.followRedirectsSettingsRoute
import fe.linksheet.generalSettingsRoute
import fe.linksheet.inAppBrowserSettingsDisableInSelectedRoute
import fe.linksheet.inAppBrowserSettingsRoute
import fe.linksheet.libRedirectServiceSettingsRoute
import fe.linksheet.libRedirectSettingsRoute
import fe.linksheet.linksSettingsRoute
import fe.linksheet.loadDumpedPreferences
import fe.linksheet.logTextViewerSettingsRoute
import fe.linksheet.logViewerSettingsRoute
import fe.linksheet.mainRoute
import fe.linksheet.module.viewmodel.MainViewModel
import fe.linksheet.preferredAppsSettingsRoute
import fe.linksheet.preferredBrowserSettingsRoute
import fe.linksheet.pretendToBeApp
import fe.linksheet.privacySettingsRoute
import fe.linksheet.settingsRoute
import fe.linksheet.shizukuSettingsRoute
import fe.linksheet.themeSettingsRoute
import fe.linksheet.ui.AppHost
import fe.linksheet.util.AndroidVersion
import fe.linksheet.whitelistedBrowsersSettingsRoute
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (mainViewModel.firstRun.value && BuildConfig.BUILD_TYPE == "release") {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }

        setContent {
            val navController = rememberNavController()

            AppHost {
                Spacer(modifier = Modifier.height(5.dp))

                NavHost(
                    navController = navController,
                    startDestination = mainRoute,
                ) {
                    animatedComposable(route = mainRoute) {
                        MainRoute(navController = navController)
                    }

                    val onBackPressed: () -> Unit = { navController.popBackStack() }
                    animatedComposable(route = settingsRoute) {
                        SettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = appsSettingsRoute) {
                        AppsSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = browserSettingsRoute) {
                        BrowserSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = generalSettingsRoute) {
                        GeneralSettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = privacySettingsRoute) {
                        PrivacySettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = bottomSheetSettingsRoute) {
                        BottomSheetSettingsRoute(onBackPressed = onBackPressed,)
                    }

                    animatedComposable(route = linksSettingsRoute) {
                        LinksSettingsRoute(
                            onBackPressed = onBackPressed,
                            navController = navController,
                        )
                    }

                    animatedComposable(route = followRedirectsSettingsRoute) {
                        FollowRedirectsSettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = libRedirectSettingsRoute) {
                        LibRedirectSettingsRoute(
                            onBackPressed = onBackPressed,
                            navController = navController,
                        )
                    }

                    animatedArgumentRouteComposable(route = libRedirectServiceSettingsRoute) { _, _ ->
                        LibRedirectServiceSettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = downloaderSettingsRoute) {
                        DownloaderSettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = amp2HtmlSettingsRoute) {
                        Amp2HtmlSettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = themeSettingsRoute) {
                        ThemeSettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = advancedSettingsRoute) {
                        AdvancedSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = shizukuSettingsRoute) {
                        ShizukuSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = featureFlagSettingsRoute) {
                        FeatureFlagSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = debugSettingsRoute) {
                        DebugSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = logViewerSettingsRoute) {
                        LogSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = loadDumpedPreferences) {
                        LoadDumpedPreferences(
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedArgumentRouteComposable(route = logTextViewerSettingsRoute) { _, _ ->
                        LogTextSettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = aboutSettingsRoute) {
                        AboutSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = creditsSettingsRoute) {
                        CreditsSettingsRoute(onBackPressed = onBackPressed)
                    }

                    animatedComposable(route = preferredBrowserSettingsRoute) {
                        PreferredBrowserSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = whitelistedBrowsersSettingsRoute) {
                        WhitelistedBrowsersSettingsRoute(navController = navController)
                    }

                    animatedComposable(route = inAppBrowserSettingsRoute) {
                        InAppBrowserSettingsRoute(
                            navController = navController,
                            onBackPressed = onBackPressed
                        )
                    }

                    animatedComposable(route = inAppBrowserSettingsDisableInSelectedRoute) {
                        InAppBrowserSettingsDisableInSelectedRoute(navController = navController)
                    }


                    animatedComposable(route = preferredAppsSettingsRoute) {
                        PreferredAppSettingsRoute(onBackPressed = onBackPressed)
                    }

                    if (AndroidVersion.AT_LEAST_API_31_S) {
                        animatedComposable(route = appsWhichCanOpenLinksSettingsRoute) {
                            AppsWhichCanOpenLinksSettingsRoute(onBackPressed = onBackPressed)
                        }

                        animatedComposable(route = pretendToBeApp) {
                            PretendToBeAppSettingsRoute(onBackPressed = onBackPressed)
                        }
                    }
                }
            }
        }
    }
}