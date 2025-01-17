package fe.linksheet.composable.settings.debug.log

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fe.android.compose.route.util.navigate
import fe.kotlin.extension.localizedString
import fe.linksheet.LogTextViewerRoute
import fe.linksheet.R
import fe.linksheet.composable.settings.SettingsScaffold
import fe.linksheet.composable.util.ClickableRow
import fe.linksheet.composable.util.ColoredIcon
import fe.linksheet.composable.util.DividedRow
import fe.linksheet.composable.util.HeadlineText
import fe.linksheet.composable.util.SettingEnabledCardColumnCommon
import fe.linksheet.composable.util.SubtitleText
import fe.linksheet.composable.util.Texts
import fe.linksheet.composable.util.mapState
import fe.linksheet.extension.ioState
import fe.linksheet.extension.compose.mapHelper
import fe.linksheet.logTextViewerSettingsRoute
import fe.linksheet.module.log.AppLogger
import fe.linksheet.module.viewmodel.LogSettingsViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LogSettingsRoute(
    onBackPressed: () -> Unit,
    navController: NavHostController,
    viewModel: LogSettingsViewModel = koinViewModel()
) {
    val files by viewModel.files.ioState()
    val mapState = remember(files) {
        mapState(files)
    }

    val startupTime = AppLogger.getInstance().startupTime.localizedString()

    SettingsScaffold(R.string.logs, onBackPressed = onBackPressed) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxHeight(),
            contentPadding = PaddingValues(horizontal = 5.dp)
        ) {
            stickyHeader(key = "current") {
                SettingEnabledCardColumnCommon(contentTitle = stringResource(id = R.string.log_files)) {
                    ClickableRow(
                        paddingHorizontal = 10.dp,
                        paddingVertical = 10.dp,
                        onClick = {
                            navController.navigate(
                                logTextViewerSettingsRoute,
                                LogTextViewerRoute(
                                    startupTime,
                                    null
                                )
                            )
                        }
                    ) {
                        Column(verticalArrangement = Arrangement.Center) {
                            HeadlineText(headline = startupTime)
                            SubtitleText(subtitleId = R.string.current_session)
                        }
                    }
                }
            }

            mapHelper(
                noItems = R.string.no_logs_found,
                mapState = mapState,
                map = files,
                listKey = { it },
            ) { fileName, timestamp ->
                DividedRow(
                    paddingHorizontal = 10.dp,
                    paddingVertical = 5.dp,
                    leftContent = {
                        ClickableRow(
                            paddingHorizontal = 0.dp,
                            paddingVertical = 0.dp,
                            onClick = {
                                navController.navigate(
                                    logTextViewerSettingsRoute,
                                    LogTextViewerRoute(
                                        timestamp,
                                        fileName
                                    )
                                )
                            }
                        ) {
                            Texts(
                                headline = timestamp,
                                subtitle = fileName + AppLogger.fileExt
                            )
                        }
                    },
                    rightContent = {
                        IconButton(onClick = {
                            viewModel.deleteFileAsync(fileName)
                        }) {
                            ColoredIcon(
                                icon = Icons.Default.Delete,
                                descriptionId = R.string.delete,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            }
        }
    }
}