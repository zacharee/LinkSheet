package fe.linksheet.module.viewmodel

import android.app.Application
import fe.linksheet.module.preference.AppPreferenceRepository

import fe.android.preference.helper.compose.getBooleanState
import fe.android.preference.helper.compose.getIntState
import fe.linksheet.module.preference.AppPreferences
import fe.linksheet.module.viewmodel.base.BaseViewModel

class FollowRedirectsSettingsViewModel(
    val context: Application,
    preferenceRepository: AppPreferenceRepository
) : BaseViewModel(preferenceRepository) {
    var followRedirects = preferenceRepository.getBooleanState(AppPreferences.followRedirects)
    var followRedirectsLocalCache = preferenceRepository.getBooleanState(
        AppPreferences.followRedirectsLocalCache
    )
    val followRedirectsBuiltInCache = preferenceRepository.getBooleanState(
        AppPreferences.followRedirectsBuiltInCache
    )
    var followRedirectsExternalService = preferenceRepository.getBooleanState(
        AppPreferences.followRedirectsExternalService
    )
    var followOnlyKnownTrackers = preferenceRepository.getBooleanState(
        AppPreferences.followOnlyKnownTrackers
    )
    val followRedirectsTimeout = preferenceRepository.getIntState(AppPreferences.requestTimeout)
}