package fe.linksheet.module.viewmodel

import android.app.Application
import fe.linksheet.module.preference.AppPreferenceRepository

import fe.android.preference.helper.compose.getBooleanState
import fe.android.preference.helper.compose.getIntState
import fe.linksheet.module.preference.AppPreferences
import fe.linksheet.module.viewmodel.base.BaseViewModel

class DownloaderSettingsViewModel(
    val context: Application,
    preferenceRepository: AppPreferenceRepository
) : BaseViewModel(preferenceRepository) {
    var enableDownloader = preferenceRepository.getBooleanState(AppPreferences.enableDownloader)
    var downloaderCheckUrlMimeType = preferenceRepository.getBooleanState(
        AppPreferences.downloaderCheckUrlMimeType
    )
    val requestTimeout = preferenceRepository.getIntState(AppPreferences.requestTimeout)

}