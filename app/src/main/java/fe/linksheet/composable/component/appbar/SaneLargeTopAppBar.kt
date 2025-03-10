package fe.linksheet.composable.component.appbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import fe.android.compose.content.rememberOptionalContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaneLargeTopAppBar(
    headline: String,
    enableBackButton: Boolean,
    onBackPressed: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val navigationIcon = rememberOptionalContent(enableBackButton) {
        SaneAppBarBackButton(onBackPressed = onBackPressed)
    }

    LargeTopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Transparent),
        title = { SaneAppBarTitle(headline = headline) },
        navigationIcon = navigationIcon ?: {},
        scrollBehavior = scrollBehavior
    )
}
