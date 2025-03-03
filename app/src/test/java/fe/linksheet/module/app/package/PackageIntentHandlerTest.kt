package fe.linksheet.module.app.`package`

import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.linksheet.testing.fake.PackageInfoFakes
import app.linksheet.testing.fake.TurretPackageInfoFake
import app.linksheet.testing.fake.YatsePackageInfoFake
import app.linksheet.testing.util.asDescriptors
import app.linksheet.testing.util.flatResolveInfos
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isNotNull
import fe.linksheet.UnitTest
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
internal class PackageIntentHandlerTest : UnitTest {

    @Test
    fun `test trampoline activity correctly handled`() {
        val handler: PackageIntentHandler = DefaultPackageIntentHandler(
            queryIntentActivities = { _, _ -> YatsePackageInfoFake.resolveInfos },
            isLinkSheetCompat = { false },
            isSelf = { false },
            checkReferrerExperiment = { true },
        )

        val handlers = handler.findHandlers(Uri.parse("https://www.youtube.com/watch?v=evIpx9Onc2c"), null)

        assertThat(handlers.asDescriptors()).containsExactly(
            "org.leetzone.android.yatsewidgetfree/org.leetzone.android.yatsewidget.ui.activity.SendToActivity:",
            "org.leetzone.android.yatsewidgetfree/.QueueToActivity:org.leetzone.android.yatsewidget.ui.activity.SendToActivity"
        )
    }

    @Test
    fun `test non-exported activities are ignored`() {
        val handler: PackageIntentHandler = DefaultPackageIntentHandler(
            queryIntentActivities = { _, _ -> TurretPackageInfoFake.resolveInfos },
            isLinkSheetCompat = { false },
            isSelf = { false },
            checkReferrerExperiment = { true },
        )

        val handlers = handler.findHandlers(Uri.parse("https://t.me/magiskalpha"), null)
        assertThat(handlers.asDescriptors()).containsExactly(
            "org.telegram.group/org.telegram.ui.LaunchActivity:"
        )
    }

    @Test
    fun `test find all http browsable`() {
        val handler: PackageIntentHandler = DefaultPackageIntentHandler(
            queryIntentActivities = { _, _ -> PackageInfoFakes.allBrowsers.flatResolveInfos() },
            isLinkSheetCompat = { false },
            isSelf = { false },
            checkReferrerExperiment = { true },
        )

        assertThat(handler.findHttpBrowsable(null))
            .isNotNull()
            .transform { it.asDescriptors() }
            .containsExactly(
                "com.mi.globalbrowser/com.sec.android.app.sbrowser.SBrowserLauncherActivity:",
                "com.duckduckgo.mobile.android/com.duckduckgo.app.dispatchers.IntentDispatcherActivity:",
                "com.android.chrome/com.google.android.apps.chrome.Main:"
            )
    }
}
