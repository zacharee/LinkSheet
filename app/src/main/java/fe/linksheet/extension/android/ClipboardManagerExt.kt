package fe.linksheet.extension.android

import android.content.ClipData
import android.content.ClipboardManager

fun ClipboardManager.setText(
    label: String,
    text: String
) = setPrimaryClip(ClipData.newPlainText(label, text))