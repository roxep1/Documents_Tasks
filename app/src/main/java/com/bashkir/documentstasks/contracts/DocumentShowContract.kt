package com.bashkir.documentstasks.contracts

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class DocumentShowContract : ActivityResultContract<Uri, Unit>() {
    override fun createIntent(context: Context, input: Uri): Intent =
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                input,
//                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
            "application/rtf"
            )
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Unit = Unit
}