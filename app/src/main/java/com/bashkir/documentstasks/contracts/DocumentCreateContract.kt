package com.bashkir.documentstasks.contracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.bashkir.documentstasks.data.models.Document
import com.bashkir.documentstasks.data.models.File

class DocumentCreateContract : ActivityResultContract<File, Uri?>() {
    override fun createIntent(context: Context, input: File): Intent =
        Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = input.getFileType()
            putExtra(Intent.EXTRA_TITLE, "${input.name}.${input.extension}")
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? =
        when (resultCode) {
            Activity.RESULT_OK -> intent?.data
            else -> null
        }
}