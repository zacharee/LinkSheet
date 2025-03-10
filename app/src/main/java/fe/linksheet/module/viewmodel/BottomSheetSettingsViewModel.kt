package fe.linksheet.module.viewmodel


import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fe.linksheet.module.preference.app.AppPreferenceRepository
import fe.linksheet.module.preference.app.AppPreferences
import fe.linksheet.module.preference.experiment.ExperimentRepository
import fe.linksheet.module.preference.permission.UsageStatsPermission
import fe.linksheet.module.viewmodel.base.BaseViewModel
import fe.linksheet.module.profile.ProfileSwitcher

class BottomSheetSettingsViewModel(
    val context: Application,
    preferenceRepository: AppPreferenceRepository,
    experimentsRepository: ExperimentRepository,
    val profileSwitcher: ProfileSwitcher,
) : BaseViewModel(preferenceRepository) {

    val enableIgnoreLibRedirectButton =
        preferenceRepository.asState(AppPreferences.enableIgnoreLibRedirectButton)
    val hideAfterCopying = preferenceRepository.asState(AppPreferences.hideAfterCopying)
    val gridLayout = preferenceRepository.asState(AppPreferences.gridLayout)
    val dontShowFilteredItem = preferenceRepository.asState(AppPreferences.dontShowFilteredItem)
    val previewUrl = preferenceRepository.asState(AppPreferences.previewUrl)
    val enableRequestPrivateBrowsingButton =
        preferenceRepository.asState(AppPreferences.enableRequestPrivateBrowsingButton)

    val usageStatsSorting = preferenceRepository.asState(AppPreferences.usageStatsSorting)
    val hideBottomSheetChoiceButtons = preferenceRepository.asState(AppPreferences.hideBottomSheetChoiceButtons)

    val tapConfigSingle = preferenceRepository.asState(AppPreferences.tapConfigSingle)
    val tapConfigDouble = preferenceRepository.asState(AppPreferences.tapConfigDouble)
    val tapConfigLong = preferenceRepository.asState(AppPreferences.tapConfigLong)
    val expandOnAppSelect = preferenceRepository.asState(AppPreferences.expandOnAppSelect)
    val bottomSheetNativeLabel = preferenceRepository.asState(AppPreferences.bottomSheetNativeLabel)
    val bottomSheetProfileSwitcher = preferenceRepository.asState(AppPreferences.bottomSheetProfileSwitcher)

    val usageStatsPermission = UsageStatsPermission(context)

    var wasTogglingUsageStatsSorting by mutableStateOf(false)
}
