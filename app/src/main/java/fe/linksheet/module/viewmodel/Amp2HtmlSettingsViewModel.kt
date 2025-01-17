package fe.linksheet.module.viewmodel

import android.app.Application
import fe.linksheet.module.preference.AppPreferenceRepository

import fe.android.preference.helper.compose.getBooleanState
import fe.android.preference.helper.compose.getIntState
import fe.linksheet.module.preference.AppPreferences
import fe.linksheet.module.viewmodel.base.BaseViewModel

class Amp2HtmlSettingsViewModel(
    val context: Application,
    preferenceRepository: AppPreferenceRepository
) : BaseViewModel(preferenceRepository) {
    var enableAmp2Html = preferenceRepository.getBooleanState(AppPreferences.enableAmp2Html)
    val enableAmp2HtmlLocalCache = preferenceRepository.getBooleanState(
        AppPreferences.amp2HtmlLocalCache
    )
    val amp2HtmlBuiltInCache = preferenceRepository.getBooleanState(
        AppPreferences.amp2HtmlBuiltInCache
    )
    val amp2HtmlExternalService = preferenceRepository.getBooleanState(
        AppPreferences.amp2HtmlExternalService
    )
    val requestTimeout = preferenceRepository.getIntState(AppPreferences.requestTimeout)
}