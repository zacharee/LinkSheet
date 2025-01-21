package fe.linksheet.module.app

import android.content.pm.ApplicationInfo
import android.content.pm.ComponentInfo
import android.content.pm.PackageInfo
import android.content.pm.ResolveInfo
import fe.linksheet.extension.android.info

interface PackageInfoLabelService {
    fun loadComponentInfoLabel(info: ComponentInfo): String?
    fun findBestLabel(packageInfo: PackageInfo, launcher: ResolveInfo?): String
    fun findApplicationLabel(applicationInfo: ApplicationInfo): String?
}

class RealPackageInfoLabelService(
    private val loadComponentInfoLabelInternal: (ComponentInfo) -> CharSequence,
    private val getApplicationLabel: (ApplicationInfo) -> CharSequence,
) : PackageInfoLabelService {

    override fun loadComponentInfoLabel(info: ComponentInfo): String? {
        val label = loadComponentInfoLabelInternal(info)
        if (label.isNotEmpty()) return label.toString()

        return null
    }

    override fun findBestLabel(packageInfo: PackageInfo, launcher: ResolveInfo?): String {
        if (launcher != null) {
            val label = loadComponentInfoLabel(launcher.info)
            if (label != null) return label
        }

        return packageInfo.applicationInfo?.let(::findApplicationLabel) ?: packageInfo.packageName
    }

    override fun findApplicationLabel(applicationInfo: ApplicationInfo): String? {
        val appLabel = getApplicationLabel(applicationInfo)
        if (appLabel.isNotEmpty()) return appLabel.toString()

        return applicationInfo.packageName
    }
}
